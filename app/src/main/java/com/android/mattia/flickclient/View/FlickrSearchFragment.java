package com.android.mattia.flickclient.View;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.mattia.flickclient.FlickrClientApplication;
import com.android.mattia.flickclient.MVC;
import com.android.mattia.flickclient.R;
/**
 * Created by mattia on 17/05/17.
 */

public class FlickrSearchFragment extends Fragment implements AbstractFragment{
    private final static String TAG = FlickrSearchFragment.class.getName();

    private MVC mvc;
    private EditText insertString;
    private Button send;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override @UiThread
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.flickr_search_fragment, container, false);

        insertString = (EditText) view.findViewById(R.id.insert_string);
        send = (Button) view.findViewById(R.id.send_button);
        send.setOnClickListener(__ -> { mvc.controller.fetchPictureInfos(getActivity(), insertString.getText().toString());
                                        mvc.controller.showPictureList();
                                        mvc.controller.downloadImage(getActivity());

                                    }
                                );

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        mvc = ((FlickrClientApplication) getActivity().getApplication()).getMVC();
        onModelChanged();
    }


    @Override
    public void onModelChanged() {

    }
}
