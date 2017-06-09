package com.android.mattia.flickclient.Model;

import android.graphics.Bitmap;
import android.util.Log;

import com.android.mattia.flickclient.MVC;
import com.android.mattia.flickclient.View.FlickrSearchFragment;
import com.android.mattia.flickclient.View.View;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Future;


@ThreadSafe
public class Model {
    private MVC mvc;
    private final static String TAG = Model.class.getName();

    @GuardedBy("itself")
    private final LinkedList<PictureInfo> pictureInfos = new LinkedList<>();

     // TODO: create getter for bitmap_image and set private
   // @Immutable
    public static class PictureInfo {
        public final String title;
        public final String url;
        public Bitmap bitmap_image;
        public final String url_s;

        public PictureInfo(String title, String url, String url_s) {
            this.title = title;
            this.url = url;
            this.url_s=url_s;
        }

        public void setImage(Bitmap bitmap_image) {
            this.bitmap_image=bitmap_image;
        }
        @Override
        public String toString() {
            return title + "\n" + url;
        }
    }

    public void setMVC(MVC mvc) {
        this.mvc = mvc;
    }

    public void storePictureInfos(Iterable<PictureInfo> pictureInfos) {
        Log.d(TAG, "start storing picture info");
        synchronized (this.pictureInfos) {
            this.pictureInfos.clear();
            for (PictureInfo pi: pictureInfos) {
                Log.d(TAG, pi.toString());
                this.pictureInfos.add(pi);
            }
        }

        mvc.forEachView(View::onModelChanged);
    }

    public void setImage(Bitmap bitmap_image, int pos){
        Log.d(TAG, "setting in model bitmap " +pos);
        synchronized (this.pictureInfos) {
            pictureInfos.get(pos).setImage(bitmap_image);
        }
        mvc.forEachView(View::onModelChanged);

    }

    public PictureInfo[] getPictureInfos() {
        synchronized (pictureInfos) {
            return pictureInfos.toArray(new PictureInfo[pictureInfos.size()]);
        }
    }

}