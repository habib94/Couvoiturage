package com.el_habib.couvoiturage.connection;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by el-habib on 29/10/16.
 */

public class JsonObjectRequestWithParams  extends StringRequest {

    private Map<String, String> params;

    public JsonObjectRequestWithParams(int method, String url, final Response.Listener<JSONObject> listener,
                                       final Response.ErrorListener errorListener, Map<String, String> params) {

        super(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    listener.onResponse(new JSONObject(response));
                }catch (JSONException je) {
                   errorListener.onErrorResponse(new ParseError(je));
                }

            }
        }, errorListener);
        this.params = params;
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }



}