package com.rattletech.cityofontario.fragments;

/**
 * Created by android1 on 8/31/2017.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rattletech.cityofontario.R;



public class RulesManagementFragment extends Fragment {




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rule_management_fragment, container, false);




        return view;
    }

    @Override
    public void onResume() {
        super.onResume();



    }

}
