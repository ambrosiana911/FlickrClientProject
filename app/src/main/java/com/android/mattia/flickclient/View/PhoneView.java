package com.android.mattia.flickclient.View;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.support.annotation.UiThread;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.android.mattia.flickclient.FlickrClientApplication;
import com.android.mattia.flickclient.MVC;
import com.android.mattia.flickclient.R;


/**
 * Created by mattia on 10/05/17.
 */

public class PhoneView extends FrameLayout implements View{
    private MVC mvc;

    public PhoneView(Context context) {
        super(context);
    }
    public PhoneView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private FragmentManager getFragmentManager() {
        return ((Activity) getContext()).getFragmentManager();
    }

    private AbstractFragment getFragment() {
        return (AbstractFragment) getFragmentManager().findFragmentById(R.id.phone_view);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mvc = ((FlickrClientApplication) getContext().getApplicationContext()).getMVC();
        mvc.register(this);

        if (getFragment() == null)
            getFragmentManager().beginTransaction().add(R.id.phone_view, new FlickrSearchFragment()).commit();
    }

    @Override
    protected void onDetachedFromWindow() {
        mvc.unregister(this);
        super.onDetachedFromWindow();
    }

    @Override
    public void onModelChanged() {
        getFragment().onModelChanged();
    }

    @UiThread
    @Override
    public void showPictureList() {
        getFragmentManager().beginTransaction().replace(R.id.phone_view, new PictureListFragment())
                .addToBackStack(null).commit();
    }
}
