package com.android.mattia.flickclient.Controller;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.android.mattia.flickclient.FlickrClientApplication;
import com.android.mattia.flickclient.MVC;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by mattia on 29/05/17.
 */

public class DownloadSmallImageService extends DownloadImageService {
    private final static String TAG = DownloadSmallImageService.class.getName();
    private final static String ACTION_DOWNLOAD = "download";
    private final static String PARAM_URL = "url";
    private final static String PARAM_POS = "pos";

    public DownloadSmallImageService() {
        super();
    }

 /*   @Override
    protected ExecutorService mkExecutorService() {
        return Executors.newFixedThreadPool(50);
    }

*/

    @UiThread
    static void download(Context context,int position, String url_s) {
        Intent intent = new Intent(context, DownloadSmallImageService.class);
        intent.setAction(ACTION_DOWNLOAD);
        intent.putExtra(PARAM_POS,position);
        intent.putExtra(PARAM_URL, url_s);
        Log.d(TAG, "startService "+url_s + " " +position);
        context.startService(intent);
    }

    @Override @WorkerThread
    protected void onHandleIntent(Intent intent) {
        String url = intent.getStringExtra(PARAM_URL);
        int pos = (int) intent.getSerializableExtra(PARAM_POS);
        Log.d(TAG, "on handle intent " + pos);
        Bitmap image = downloadImage(url);
        MVC mvc = ((FlickrClientApplication) getApplication()).getMVC();
        mvc.model.setImage(image,pos);

    }


}
