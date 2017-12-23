package com.example.httplib;

import android.util.Log;

import java.util.Vector;

/**
 * Created by anouarkappitou on 7/30/17.
 */

public class HttpUrl {

    private static final String[] PROTOCOLS = { "http" , "https" };

    String _url;
    String _proto;
    Vector<HttpEntity> _params;

    public HttpUrl( String url )
    {
        _url = url;
        _proto = PROTOCOLS[0];
        _params = new Vector<>();
    }

    public void add_param( HttpEntity entity )
    {
        _params.add( entity );
    }

    private boolean is_protocole_found()
    {
        for ( String proto : PROTOCOLS )
        {
            if ( _url.contains( proto ) ) return true;
        }

        return false;
    }

    public void use_ssl( boolean ssl )
    {
        if( ssl )
            _proto = PROTOCOLS[1];
        else
            _proto = PROTOCOLS[0];
    }

    public void set_params( Vector<HttpEntity> params )
    {
        _params = params;
    }

    public String toString(){

        if( !is_protocole_found() )
        {
            _url = _proto + "://" + _url;
        }

        if( !_params.isEmpty() )
        {
            _url += "?" + HttpUtils.params_stringify( _params );
        }

        Log.d("HttpLib", _url );

        return _url;
    }

}
