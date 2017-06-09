package com.android.mattia.flickclient.Controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.android.mattia.flickclient.FlickrClientApplication;
import com.android.mattia.flickclient.MVC;

/**
 * Created by mirkp on 09/06/2017.
 */

class DownloadHDImageService extends DownloadImageService {
    private final static String TAG = DownloadHDImageService.class.getName();
    private final static String ACTION_DOWNLOAD = "download";
    private final static String PARAM_URL = "url";


    @Override @WorkerThread
    protected void onHandleIntent(Intent intent) {
        String url = intent.getStringExtra(PARAM_URL);
        Log.d(TAG, "on handle intent ");
        Bitmap image = downloadImage(url);
        MVC mvc = ((FlickrClientApplication) getApplication()).getMVC();

    }

    @UiThread
    static void download(Context context, String url) {
        Intent intent = new Intent(context, DownloadHDImageService.class);
        intent.setAction(ACTION_DOWNLOAD);

        intent.putExtra(PARAM_URL, url);
        Log.d(TAG, "startService "+url);
        context.startService(intent);
    }
}
