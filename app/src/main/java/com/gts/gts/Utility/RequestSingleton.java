package com.gts.gts.Utility;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by john.nana on 8/8/2017.
 */

public class RequestSingleton {

    private static RequestSingleton mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    private RequestSingleton(Context context) {
        mCtx = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }

        return requestQueue;
    }

    public static synchronized RequestSingleton getmInstance(Context context){
        if(mInstance == null){
            mInstance = new RequestSingleton(context);
        }
        return mInstance;

    }

    public<T> void addToRequestQueue(Request<T> request){
        requestQueue.add(request);

    }

}
