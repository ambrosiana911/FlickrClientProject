package com.android.mattia.flickclient.Controller;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.android.mattia.flickclient.ExecutorIntentService;
import com.android.mattia.flickclient.FlickrClientApplication;
import com.android.mattia.flickclient.MVC;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by mattia on 29/05/17.
 */

class DownloadImageService extends ExecutorIntentService {
    private final static String TAG = DownloadImageService.class.getName();
    private final static String ACTION_DOWNLOAD = "download";
    private final static String PARAM_URL = "url";
    private final static String PARAM_POS = "pos";

    public DownloadImageService() {
        super("download image service");
    }

    @Override
    protected ExecutorService mkExecutorService() {
        return Executors.newFixedThreadPool(50);
    }



    @UiThread
    static void download(Context context, List<String> urls) {
        Intent intent = new Intent(context, DownloadImageService.class);
        intent.setAction(ACTION_DOWNLOAD);
        for (int i=0; i<50; i++) {
            intent.putExtra(PARAM_POS,i);
            intent.putExtra(PARAM_URL, urls.get(i));
            context.startService(intent);
        }
    }

    @Override @WorkerThread
    protected void onHandleIntent(Intent intent) {
        switch (intent.getAction()) {
            case ACTION_DOWNLOAD:
                String url = (String) intent.getSerializableExtra(PARAM_URL);
                int pos = (int) intent.getSerializableExtra(PARAM_POS);
                Bitmap image = downloadImage(url);
                MVC mvc = ((FlickrClientApplication) getApplication()).getMVC();
                mvc.model.setImage(image,pos);
                break;
        }
    }

    @WorkerThread
    private Bitmap downloadImage(String url_s) {
        Log.d(TAG, "starting downloading " + url_s);

            Bitmap bitmap_image = null;
            // Download Image from URL
            try {
                URL url = new URL(url_s);
                URLConnection conn = url.openConnection();
                bitmap_image = BitmapFactory.decodeStream(conn.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap_image;
    }

}
