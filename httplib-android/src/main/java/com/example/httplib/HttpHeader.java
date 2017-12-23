package com.example.httplib;

import java.util.SortedSet;
import java.util.Vector;

/**
 * Created by anouarkappitou on 7/30/17.
 */

public class HttpHeader {

    public final static String ACCEPT      = "accept";
    public final static String CONNECTION  = "connection";
    public final static String USER_AGENT  = "user-agent";

    public Vector<HttpEntity> _entities;

    public HttpHeader()
    {
        _entities = new Vector<>();
    }

    // TODO: refactore this for optimization maybe implement Map
    public String get( String key )
    {
        String value = "";

        for( HttpEntity e : _entities )
        {
            if( e.get_key().equalsIgnoreCase( key ) )
                return e.get_value();
        }

        return null;
    }

    public void add( String key , String value )
    {
        _entities.add( new HttpEntity( key , value ) );
    }

    public void add_accept( String accept )
    {
        add( "accept" , accept );
    }

    public void add_referer( String referer )
    {
        add( "referer" , referer );
    }

    public void add_useragent( String agent )
    {
        add( "user-agent" , agent );
    }

    public void add_connection( String connection ){ add( "connection" , connection ); }

    public String toString()
    {
        String http_header = "";

        for ( HttpEntity entity : _entities )
        {
                 http_header += entity.toString() + "\r\n";
        }

        return http_header;
    }

    public Vector< HttpEntity > get_entities() { return  _entities; }
    public boolean is_empty() { return _entities.isEmpty(); }
}
