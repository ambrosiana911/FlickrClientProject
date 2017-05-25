package com.android.mattia.flickclient;

import android.app.Application;

import com.android.mattia.flickclient.Controller.Controller;
import com.android.mattia.flickclient.Model.Model;


public class FlickrClientApplication extends Application {
    private MVC mvc;

    @Override
    public void onCreate() {
        super.onCreate();

        mvc = new MVC(new Model(), new Controller());
    }

    public MVC getMVC() {
        return mvc;
    }
}
