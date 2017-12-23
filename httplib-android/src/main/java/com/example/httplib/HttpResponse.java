package com.example.httplib;

import java.net.HttpURLConnection;

/**
 * Created by anouarkappitou on 7/31/17.
 */

public class HttpResponse {

    private String _response;
    private int _code;
    private HttpHeader _header;

    public HttpResponse( String response , int code )
    {
        _response = response;
        _code = code;
    }

    public void set_header( HttpHeader header ){ _header = header; }
    public String get_body()  { return _response; }
    public int get_code() { return _code; }
    public boolean is_http_ok() { return _code == HttpURLConnection.HTTP_OK; }
    public HttpHeader get_header() { return _header; }

}
