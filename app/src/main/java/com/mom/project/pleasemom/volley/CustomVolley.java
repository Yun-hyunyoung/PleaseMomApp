package com.mom.project.pleasemom.volley;

/**
 * Created by 08_718 on 2016-10-31.
 */

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

public class CustomVolley {
    private static CustomVolley mInstance;
    private static Context mCtx;
    private RequestQueue mRequestQueue;

    private CustomVolley(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static CustomVolley getInstance(Context context) {
        if (mInstance == null) {
            synchronized (CustomVolley.class) {
                if (mInstance == null) {
                    mInstance = new CustomVolley(context);
                }
            }
        }

        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            Cache cache = new DiskBasedCache(mCtx.getCacheDir(), 10 * 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            mRequestQueue = new RequestQueue(cache, network);
            // Don't forget to start the volley request queue
            mRequestQueue.start();
        }
        return mRequestQueue;
    }
}
