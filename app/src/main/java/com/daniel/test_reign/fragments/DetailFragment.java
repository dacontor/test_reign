package com.daniel.test_reign.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.daniel.test_reign.R;
import com.daniel.test_reign.core.models.HitsObject;

public class DetailFragment extends Fragment {

    private HitsObject mObject;

    public static DetailFragment newInstance(HitsObject data) {
        DetailFragment fragment = new DetailFragment();
        Bundle b = new Bundle();
        b.putParcelable("data", data);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getArguments();

        assert b != null;
        mObject = b.getParcelable("data");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        WebView myWebView = view.findViewById(R.id.webView2);
        myWebView.loadUrl(mObject.getStory_url());

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
