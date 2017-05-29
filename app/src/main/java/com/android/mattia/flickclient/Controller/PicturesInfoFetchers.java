package com.android.mattia.flickclient.Controller;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.android.mattia.flickclient.FlickrClientApplication;
import com.android.mattia.flickclient.MVC;
import com.android.mattia.flickclient.Model.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by mattia on 09/05/17.
 */

public class PicturesInfoFetchers extends IntentService {
    private final static String TAG = Controller.class.getName();

    public PicturesInfoFetchers() {
        super("pictures info fetchers");
    }

    static void fetcher(Context context, String stringSearch){
        Intent intent = new Intent(context , PicturesInfoFetchers.class);
        intent.putExtra("STRING_SEARCH" , stringSearch);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String searchString = intent.getStringExtra("STRING_SEARCH");
        Iterable<Model.PictureInfo> iterable = fetchPictureInfos(searchString);
        MVC mvc = ((FlickrClientApplication) getApplication()).getMVC();
        mvc.model.storePictureInfos(iterable);
    }

    private final static String API_KEY = "9f0a7d09d0e1ff62598c289b9d629f28";

    @WorkerThread
    private Iterable<Model.PictureInfo> fetchPictureInfos(String searchString) {
        String query = String.format("https://api.flickr.com/services/rest?method=flickr.photos.search&api_key=%s&text=%s&extras=url_s,url_z,description,tags&per_page=50",
                API_KEY,
                searchString);

        Log.d(TAG, query);
        try {
            URL url = new URL(query);
            URLConnection conn = url.openConnection();
            String answer = "";

            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line;
                while ((line = in.readLine()) != null) {
                    answer += line + "\n";
                    Log.d(TAG, line);
                }
            }
            finally {
                if (in != null)
                    in.close();
            }

            return parse(answer);
        }
        catch (IOException e) {
            return Collections.emptyList();
        }
    }
    private Iterable<Model.PictureInfo> parse(String xml) {
        List<Model.PictureInfo> infos = new LinkedList<>();
//      Executor e = new Executor() {
//            @Override
//            public void execute(@NonNull Runnable command) {
//                new Thread(command).start();
//            }
//        };
//      ExecutorService image_downloaders = Executors.newFixedThreadPool(5);
//        CompletionService<Bitmap> complService = new ExecutorCompletionService<>(e);

        int nextPhoto = -1;
        do {
            nextPhoto = xml.indexOf("<photo id", nextPhoto + 1);
            if (nextPhoto >= 0) {
                Log.d(TAG, "nextPhoto = " + nextPhoto);
                int titlePos = xml.indexOf("title=", nextPhoto) + 7;
                int url_zPos = xml.indexOf("url_z=", nextPhoto) + 7;
                int url_sPos = xml.indexOf("url_s=", nextPhoto) + 7;
                String title = xml.substring(titlePos, xml.indexOf("\"", titlePos + 1));
                String url_z = xml.substring(url_zPos, xml.indexOf("\"", url_zPos + 1));
                String url_s = xml.substring(url_sPos, xml.indexOf("\"", url_sPos + 1));

//                complService.submit(() -> download_image(url_s));
//              image_downloaders.execute(() -> download_image(url_s));

                infos.add(new Model.PictureInfo(title, url_z, download_image(url_s)));
            }
        }
        while (nextPhoto != -1);

        return infos;
    }

    private Bitmap download_image(String url_s) {
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