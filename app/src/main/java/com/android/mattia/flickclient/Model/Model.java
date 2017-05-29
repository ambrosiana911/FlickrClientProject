package com.android.mattia.flickclient.Model;

import android.graphics.Bitmap;

import com.android.mattia.flickclient.MVC;
import com.android.mattia.flickclient.View.View;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.concurrent.Future;


@ThreadSafe
public class Model {
    private MVC mvc;

    @GuardedBy("itself")
    private final LinkedList<PictureInfo> pictureInfos = new LinkedList<>();

    @Immutable
    public static class PictureInfo {
        public final String title;
        public final String url;
        //public final Bitmap bitmap_image;
        public Future<Bitmap> bitmap_image;

        public PictureInfo(String title, String url, Future<Bitmap> bitmap_image) {
            this.title = title;
            this.url = url;
            this.bitmap_image = bitmap_image;
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
        synchronized (this.pictureInfos) {
            this.pictureInfos.clear();
            for (PictureInfo pi: pictureInfos)
                this.pictureInfos.add(pi);
        }

        mvc.forEachView(View::onModelChanged);
    }

    public PictureInfo[] getPictureInfos() {
        synchronized (pictureInfos) {
            return pictureInfos.toArray(new PictureInfo[pictureInfos.size()]);
        }
    }
}