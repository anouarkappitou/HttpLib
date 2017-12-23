package com.example.httplib;

/**
 * Created by anouarkappitou on 7/30/17.
 */

public class HttpEntity {

    String _key;
    String _value;

    public HttpEntity( String key , String value )
    {
        _key    = key;
        _value  = value;
    }

    public void set_key( String key )
    {
        _key = key;
    }

    public void set_value( String value )
    {
        _value = value;
    }


    public String get_key() { return _key; }
    public String get_value() { return _value; }

}
