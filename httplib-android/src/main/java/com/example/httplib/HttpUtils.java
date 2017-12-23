package com.example.httplib;

import java.util.Vector;

/**
 * Created by anouarkappitou on 7/31/17.
 */

public class HttpUtils {

    public static String params_stringify( Vector<HttpEntity> params )
    {
        String result = "";
        String separator = "";

            for ( HttpEntity entity : params )
            {
                result += separator + entity.get_key() + "=" + entity.get_value();
                separator = "&";
            }

        return result;
    }
}
