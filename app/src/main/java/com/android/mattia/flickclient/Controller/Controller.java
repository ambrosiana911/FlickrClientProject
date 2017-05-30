package com.android.mattia.flickclient.Controller;

import android.content.Context;
import android.support.annotation.UiThread;

import com.android.mattia.flickclient.MVC;
import com.android.mattia.flickclient.Model.Model;
import com.android.mattia.flickclient.View.View;

import java.util.LinkedList;
import java.util.List;


public class Controller {
    private final static String TAG = Controller.class.getName();
    private MVC mvc;

    public void setMVC(MVC mvc) {
        this.mvc = mvc;
    }

    @UiThread
    public void fetchPictureInfos(Context context , String searchString) {
        PicturesInfoFetchers.fetcher(context , searchString);
    }

    @UiThread
    public void downloadImage(Context context ){
        LinkedList<String> urls = new LinkedList<>();
        for(Model.PictureInfo picture: mvc.model.getPictureInfos())
            urls.add(picture.url_s);
        DownloadImageService.download(context,urls);

    }

    @UiThread
    public void showPictureList() {
        mvc.forEachView(View::showPictureList);
    }
}