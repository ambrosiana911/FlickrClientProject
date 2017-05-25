package com.android.mattia.flickclient.View;

import android.support.annotation.UiThread;

public interface View {

    @UiThread
    void onModelChanged();

    @UiThread
    void showPictureList();
}
