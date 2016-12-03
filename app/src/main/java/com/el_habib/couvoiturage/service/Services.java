package com.el_habib.couvoiturage.service;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.el_habib.couvoiturage.Couvoiturage;
import com.el_habib.couvoiturage.VolleyCallBack;
import com.el_habib.couvoiturage.connection.JsonObjectRequestWithParams;
import com.el_habib.couvoiturage.model.Voiture;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by el-habib on 28/10/16.
 */

public class Services {

    public static final String IP = "http://10.42.0.1";
    public static final String URL_AUTHENTIFICATION = IP+"/authentification";
    public static final String URL_PROJET_SIMPLE = IP+"/projetsimple.json";
    public static final String URL_VOITURES = IP+"/api/v1/voiture";

    private Context context;

    public Services(Context context){
        this.context = context;
    }


    public LatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 1);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    public void authentification(String email, String motDePasse, final VolleyCallBack<Boolean> callBack){
        Map<String,String> map = new HashMap<>();
        map.put("email",email);
        map.put("password",motDePasse);

        JsonObjectRequestWithParams jsonObjectRequest = new JsonObjectRequestWithParams(Request.Method.POST, URL_AUTHENTIFICATION,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callBack.sameOpperation();
                try {
                    callBack.onSuccess(response.getBoolean("result"));
                } catch (JSONException e) {
                    callBack.errorNetwork();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.sameOpperation();
                callBack.errorNetwork();
            }
        },map);

        Couvoiturage.addRequest(jsonObjectRequest);
    }


    public void getVoitures(final VolleyCallBack<List<Voiture>> callBack){

        Map<String,String> map = new HashMap<>();

        JsonObjectRequestWithParams jsonObjectRequestWithParams = new JsonObjectRequestWithParams(Request.Method.GET, URL_VOITURES, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callBack.sameOpperation();
                try {
                    List<Voiture> voitures = new ArrayList<>();
                    JSONArray voituresJson = response.getJSONArray("voitures");
                    for(int i=0;i<voituresJson.length();i++){
                        JSONObject voitureJson = voituresJson.getJSONObject(i);
                        voitures.add(new Voiture(voitureJson.getInt("id"),voitureJson.getString("marque"),voitureJson.getString("color"),voitureJson.getString("imageUrl")));
                    }
                    callBack.onSuccess(voitures);
                }catch (Exception e){
                    callBack.errorNetwork();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                 callBack.sameOpperation();
                callBack.errorNetwork();
            }
        },map);

        Couvoiturage.addRequest(jsonObjectRequestWithParams);

    }


}
