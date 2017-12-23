package com.example.httplib;

import java.util.Vector;

/**
 * Created by anouarkappitou on 7/31/17.
 */

public class HttpRequest {

    private HttpHeader _header;
    private HttpUrl _url;
    private Vector<HttpEntity> _params;
    private int _method;

    public final static int GET = 0;
    public final static int POST = 1;
    private int _timeout;

    public HttpRequest( HttpUrl url )
    {
        // set default timeout to be 500 ms
        _timeout = 500;
        // set default method to be GET
        _method = GET;

        _url = url;
        _header = new HttpHeader();
        _params = new Vector<>();
    }

    public void set_method( int method )
    {
        _method = method;
    }

    public void set_timeout( int timeout )
    {
        _timeout = timeout;
    }

    public int get_timeout() { return _timeout; }

    public int get_method() { return _method; }

    public void set_header( HttpHeader header )
    {
        _header = header;
    }

    public void add_param( HttpEntity entity )
    {
        _params.add( entity );
    }

    public Vector< HttpEntity >  get_params() { return _params; }
    public HttpUrl get_url() { return _url; }
    public HttpHeader get_header(){ return _header; }


}
