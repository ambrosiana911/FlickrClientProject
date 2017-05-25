package com.android.mattia.flickclient.View;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.mattia.flickclient.FlickrClientApplication;
import com.android.mattia.flickclient.MVC;
import com.android.mattia.flickclient.Model.Model;
import com.android.mattia.flickclient.R;

/**
 * Created by mattia on 17/05/17.
 */

public class PictureListFragment extends ListFragment implements AbstractFragment{
    private MVC mvc;

    @Override @UiThread
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        mvc = ((FlickrClientApplication) getActivity().getApplication()).getMVC();
        onModelChanged();
    }

    @Override @UiThread
    public void onModelChanged() {
        setListAdapter(new PictureListAdapter());
    }
    private class PictureListAdapter extends ArrayAdapter<Model.PictureInfo> {
        private final Model.PictureInfo[] pictureInfos = mvc.model.getPictureInfos();

        private PictureListAdapter() {
            super(getActivity(), R.layout.list_item_layout, mvc.model.getPictureInfos());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View row = convertView;

            if (row == null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                row = inflater.inflate(R.layout.list_item_layout, parent, false);
            }

            Model.PictureInfo pictureInfo = pictureInfos[position];
            ((TextView) row.findViewById(R.id.picture_infos)).setText(pictureInfo.toString());

            return row;
        }
    }

}
