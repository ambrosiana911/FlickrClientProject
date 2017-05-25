package com.android.mattia.flickclient.View;

import android.support.annotation.UiThread;

/**
 * Created by mattia on 16/05/17.
 */

public interface AbstractFragment {

    @UiThread
    void onModelChanged();
}
