package com.el_habib.couvoiturage.connection;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * Created by el-habib on 29/10/16.
 */

public class StringRequestWithParams extends StringRequest{

    private Map<String, String> params;

    public StringRequestWithParams(int method, String url, Response.Listener<String> listener,
                         Response.ErrorListener errorListener,Map<String, String> params) {
        super(method, url,listener,errorListener);
        this.params = params;
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
