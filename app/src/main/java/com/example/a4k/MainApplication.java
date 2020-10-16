package com.example.a4k;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.squareup.otto.Bus;

public class MainApplication extends Application {

    public static Context sContext;
    private static RequestQueue sQueue;
    private static Bus eventBus;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    /**
     * Volley requestQueue instance
     *
     * @return : Global volley request queue instance
     */
    public static RequestQueue getRequestQueue() {
        if (sQueue == null) {
            sQueue = Volley.newRequestQueue(sContext);
        }
        return sQueue;
    }

    /**
     * Otto EventBus instance
     *
     * @return Global Otto EventBus instance
     */
    public static synchronized Bus getEventBus() {
        if (eventBus == null) {
            eventBus = new Bus();
        }
        return eventBus;
    }
}