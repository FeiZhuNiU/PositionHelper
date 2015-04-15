package com.yu.eric.positionhelper;

import android.app.Application;
import android.content.Context;

/**
 * Created by lliyu on 4/15/2015.
 */
public class ContextProvider extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
