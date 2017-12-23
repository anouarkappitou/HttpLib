package com.example.anouarkappitou.httplib_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.httplib.HttpClient;
import com.example.httplib.HttpEntity;
import com.example.httplib.HttpHeader;
import com.example.httplib.HttpRequest;
import com.example.httplib.HttpResponse;
import com.example.httplib.HttpUrl;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // NOTE : you should add internet permission to android manifest file

        final TextView view = (TextView) findViewById( R.id.html );


        HttpUrl url = new HttpUrl( "xodia.herokuapp.com" );
        url.use_ssl( true );

        HttpClient http_client = new HttpClient();

        HttpRequest request = new HttpRequest( url );
        request.set_method( HttpRequest.POST );
        request.add_param( new HttpEntity( "name" , "hamid" ) );
        request.add_param( new HttpEntity( "lastname" , "jamal" ) );
        request.add_param( new HttpEntity( "age" , "10" ) );


        http_client.set_result_callback(new HttpClient.Callback() {

            @Override
            public void onSuccess( HttpResponse obj ) {

                if( obj.is_http_ok() )
                {
                    view.setText( obj.get_body() + "\n" );
                    HttpHeader header = obj.get_header();

                    view.append( header.get( HttpHeader.CONNECTION ) );

                }else
                {
                    view.setText( obj.get_code() );
                }
            }

            @Override
            public void onError() {
                view.setText( "Error occured" );
            }
        });

        http_client.send( request );
    }
}
