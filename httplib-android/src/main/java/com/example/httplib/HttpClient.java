package com.example.httplib;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by anouarkappitou on 7/30/17.
 */

public class HttpClient {

    public interface Callback{
        void onSuccess( HttpResponse response );
        void onError();
    }

    Callback    _result;

    public HttpClient()
    {
        _result = null;
        // NOTE: set default timeout to be 500 ms
    }


    public void set_result_callback( Callback result )
    {
        _result = result;
    }

    public void send( HttpRequest request )
    {
        new AsyncRequest( request ).execute();
    }

    private class AsyncRequest extends AsyncTask<Void,Void,HttpResponse>
    {

        private HttpRequest _req;
        public AsyncRequest( HttpRequest request )
        {
            _req = request;
        }

        @Override
        protected void onPostExecute( HttpResponse s ) {
            // TODO : reformate Result or just pass it as it is

            if( _result != null )
            {
                if( s != null )
                    _result.onSuccess( s );
                else
                    // TODO : error code
                    _result.onError();
            }
        }

        @Override
        protected HttpResponse doInBackground(Void... voids) {
            try {

                HttpUrl url = _req.get_url();

                byte[] raw_params = null;

                if( _req.get_method() == HttpRequest.GET )
                {
                    url.set_params( _req.get_params() );
                }else{
                   String params_data = HttpUtils.params_stringify( _req.get_params() );
                    raw_params = params_data.getBytes( StandardCharsets.UTF_8 );
                }

                HttpHeader header = _req.get_header();

                HttpURLConnection connection = (HttpURLConnection) new URL( url.toString() ).openConnection();

                if( !header.is_empty() )
                {
                    Vector< HttpEntity > entities = header.get_entities();
                    for ( HttpEntity entity : entities )
                    {
                        connection.setRequestProperty( entity.get_key() , entity.get_value() );
                    }
                }

                connection.setConnectTimeout( _req.get_timeout() );
                connection.setDefaultUseCaches( false );

                connection.setDoInput( true );

                if( _req.get_method() == HttpRequest.GET ) {
                    connection.setRequestMethod( "GET" );
                    connection.setDoOutput(false);
                }else {
                    connection.setRequestProperty( "Content-Length", Integer.toString( raw_params.length ));
                    connection.setRequestMethod( "POST" );
                    connection.setDoOutput(true);
                }

                connection.connect();

                // TODO : refactore to support json ...


                if( _req.get_method() == HttpRequest.POST )
                {
                   try ( DataOutputStream dos = new DataOutputStream( connection.getOutputStream() ) ){
                        dos.write( raw_params );
                    }
                }



                HttpHeader response_header = new HttpHeader();

                Map<String,List<String>> fields = connection.getHeaderFields();

                for (Map.Entry<String, List<String>> k : fields.entrySet()) {
                    for (String v : k.getValue()){
                        if( k.getKey() != null && v != null )
                            response_header.add( k.getKey() , v );
                    }
                }

                InputStream is = connection.getInputStream();

                HttpResponse response = new HttpResponse( get_response( is ) , connection.getResponseCode() );

                response.set_header( response_header );

                is.close();

                return response;

            } catch ( Exception e ) {
                Log.d("HttpLib" , e.getMessage() );
                return null;
            }
        }

        private String get_response( InputStream stream ) throws IOException {

            BufferedReader reader = new BufferedReader( new InputStreamReader( stream ) );
            String line = "";
            StringBuffer buffer = new StringBuffer();

            while ( ( line = reader.readLine() ) != null )
            {
                buffer.append( line );
            }

            return buffer.toString();
        }

    }

}
