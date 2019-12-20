package com.example.ahemdhammouda.malvoyants.Helper;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySegleton {

    private static MySegleton mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    private MySegleton(Context context) {
        mCtx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized MySegleton getmInstance(Context context)
    {
        if (mInstance == null){
            mInstance = new MySegleton(context);
        }
        return mInstance;
    }


    public RequestQueue getRequestQueue() {

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }

        return requestQueue;
    }

    public <T> void addTorequestque(Request<T> request)
    {
         requestQueue.add(request);
    }
}
