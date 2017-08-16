package com.gts.gts.MainFragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gts.gts.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NetworkStatFragment extends Fragment {


    public NetworkStatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_network_stat, container, false);
    }

}
