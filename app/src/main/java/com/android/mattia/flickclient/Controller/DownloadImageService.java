package com.android.mattia.flickclient.Controller;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by mirkp on 09/06/2017.
 */

public abstract class DownloadImageService extends IntentService {


    public DownloadImageService() {
        super("download image service");
    }

    @Override
    protected abstract void onHandleIntent(@Nullable Intent intent);

    @WorkerThread
    public Bitmap downloadImage(String url_s) {
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
