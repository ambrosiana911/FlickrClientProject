package com.android.mattia.flickclient.View;

import android.app.ListFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.mattia.flickclient.Controller.Controller;
import com.android.mattia.flickclient.FlickrClientApplication;
import com.android.mattia.flickclient.MVC;
import com.android.mattia.flickclient.Model.Model;
import com.android.mattia.flickclient.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

/**
 * Created by mattia on 17/05/17.
 */

public class PictureListFragment extends ListFragment implements AbstractFragment{
    private MVC mvc;
    private final static String TAG = PictureListFragment.class.getName();

    @Override @UiThread
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        Log.d(TAG, "create activity");
        mvc = ((FlickrClientApplication) getActivity().getApplication()).getMVC();
        super.onActivityCreated(savedInstanceState);

        onModelChanged();

    }

    @Override @UiThread
    public void onModelChanged() {
        Log.d(TAG, "Setting ListAdapter");
        if(getListAdapter()==null)
            setListAdapter(new PictureListAdapter());
    }

    private class PictureListAdapter extends ArrayAdapter<Model.PictureInfo> {
        private final Model.PictureInfo[] pictureInfos = mvc.model.getPictureInfos();

        private PictureListAdapter() {
            super(getActivity(), R.layout.list_item_layout, mvc.model.getPictureInfos());


        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            Log.d(TAG, "getting position " + position);
            View row = convertView;

            if (row == null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                row = inflater.inflate(R.layout.list_item_layout, parent, false);
            }

            Model.PictureInfo pictureInfo = pictureInfos[position];
            if (position==0)
                for(int i=0; i<50; i++){
                    mvc.controller.downloadImage(getActivity(),i, pictureInfos[i].url_s);
                }
            ((TextView) row.findViewById(R.id.picture_infos)).setText(pictureInfo.toString());

            ((ImageView) row.findViewById(R.id.preview)).setImageBitmap(pictureInfo.bitmap_image);



            return row;
        }
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);

        Model.PictureInfo pictureInfo = mvc.model.getPictureInfos()[position];

        mvc.controller.downloadImageHD(getActivity(), pictureInfo.url);

    }

}
