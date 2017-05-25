package com.android.mattia.flickclient.Model;

import com.android.mattia.flickclient.MVC;
import com.android.mattia.flickclient.View.View;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;



@ThreadSafe
public class Model {
    private MVC mvc;

    @GuardedBy("itself")
    private final LinkedList<PictureInfo> pictureInfos = new LinkedList<>();

    @Immutable
    public static class PictureInfo {
        public final String title;
        public final String url;

        public PictureInfo(String title, String url) {
            this.title = title;
            this.url = url;
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