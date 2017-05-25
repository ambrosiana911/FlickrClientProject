package com.android.mattia.flickclient;

import android.os.Handler;
import android.os.Looper;

import com.android.mattia.flickclient.Controller.Controller;
import com.android.mattia.flickclient.Model.Model;
import com.android.mattia.flickclient.View.View;

import net.jcip.annotations.ThreadSafe;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;



@ThreadSafe
public class MVC {
    public final Model model;
    public final Controller controller;
    private final List<View> views = new CopyOnWriteArrayList<>();

    public MVC(Model model, Controller controller) {
        this.model = model;
        this.controller = controller;

        model.setMVC(this);
        controller.setMVC(this);
    }

    public void register(View view) {
        views.add(view);
    }

    public void unregister(View view) {
        views.remove(view);
    }

    public interface ViewTask {
        void process(View view);
    }

    public void forEachView(ViewTask task) {
        // run a Runnable in the UI thread
        new Handler(Looper.getMainLooper()).post(() -> {
            for (View view: views)
                task.process(view);
        });
    }
}