package com.el_habib.couvoiturage;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NoCache;
import com.el_habib.couvoiturage.model.Utilisateur;
import com.el_habib.couvoiturage.service.Services;

/**
 * Created by el-habib on 28/10/16.
 */

public class Couvoiturage extends Application{

    private Utilisateur utilisateur = null;
    private Services services = null;
    private Class<?> activityDesirer = null;
    private static RequestQueue mRequestQueue = null;
    public static ImageLoader mImageLoader = null;


    {

        Cache cache = new NoCache();
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        // Don't forget to start the volley request queue
        mRequestQueue.start();

        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {

            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);

            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }

        });

    }


    public static void addRequest(Request request){

        mRequestQueue.add(request);
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Services getServices(){
        if(services == null){
            services = new Services(getApplicationContext());
        }
        return services;
    }

    public Class<?> getActivityDesirer() {
        return activityDesirer;
    }

    public void setActivityDesirer(Class<?> activityDesirer) {
        this.activityDesirer = activityDesirer;
    }
}
