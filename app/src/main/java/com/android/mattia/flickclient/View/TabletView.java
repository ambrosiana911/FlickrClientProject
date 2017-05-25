package com.android.mattia.flickclient.View;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.support.annotation.UiThread;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.android.mattia.flickclient.FlickrClientApplication;
import com.android.mattia.flickclient.MVC;
import com.android.mattia.flickclient.R;

/**
 * Created by mattia on 16/05/17.
 */

public class TabletView extends LinearLayout implements View {

    private MVC mvc;

    public TabletView(Context context) {
        super(context);
    }

    public TabletView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private FragmentManager getFragmentManager() {
        return ((Activity) getContext()).getFragmentManager();
    }

    private AbstractFragment getFlickrSearchFragment() {
        return (AbstractFragment) getFragmentManager().findFragmentById(R.id.flickr_search_fragment);
    }

    private AbstractFragment getPictureListFragment() {
        return (AbstractFragment) getFragmentManager().findFragmentById(R.id.picture_list_fragment);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mvc = ((FlickrClientApplication) getContext().getApplicationContext()).getMVC();
        mvc.register(this);
    }
    @Override
    protected void onDetachedFromWindow() {
        mvc.unregister(this);
        super.onDetachedFromWindow();
    }

    @Override
    public void onModelChanged() {
        getFlickrSearchFragment().onModelChanged();
        getPictureListFragment().onModelChanged();
    }

    @UiThread
    @Override
    public void showPictureList() {
        //nothing to do
    }
}
