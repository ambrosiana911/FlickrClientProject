package com.android.mattia.flickclient.Controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by mattia on 29/05/17.
 */
 public class DownloadImage {
    private final String url_s;

    public DownloadImage (String url_s) {
        this.url_s = url_s;
    }

    public Bitmap getBitmapImage(){
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
