package com.android.mattia.flickclient.Controller;

import android.content.Context;
import android.support.annotation.UiThread;
import android.util.Log;

import com.android.mattia.flickclient.MVC;
import com.android.mattia.flickclient.View.View;


public class Controller {
    private final static String TAG = Controller.class.getName();
    private MVC mvc;

    public void setMVC(MVC mvc) {
        this.mvc = mvc;
    }

    @UiThread
    public void fetchPictureInfos(Context context , String searchString){

        PicturesInfoFetchers.fetcher(context , searchString);

    }

    @UiThread
    public void downloadImage(Context context, int position, String url_s ){
        Log.d(TAG, "starting download " + position);
        DownloadSmallImageService.download(context,position,url_s);
    }
    @UiThread
    public void downloadImageHD(Context context, String url ){
        Log.d(TAG, "starting download HD");
        DownloadHDImageService.download(context,url);
    }

    @UiThread
    public void showPictureList() {
        mvc.forEachView(View::showPictureList);
    }
}