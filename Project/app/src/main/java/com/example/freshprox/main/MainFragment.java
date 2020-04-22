package com.example.freshprox.main;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.freshprox.R;
import com.example.freshprox.SwitchActivity;
import com.example.freshprox.search.SearchActivity;
import com.example.freshprox.vendor.VendorActivity;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private SwitchActivity switchActivity;
    private ConstraintLayout layoutItem;
    private View view;


    public MainFragment(SwitchActivity sA){
        this.switchActivity = sA;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.main_fragment, container, false);
        Button search = this.view.findViewById(R.id.tosearch);
        Button vendor = this.view.findViewById(R.id.vendor);
        this.setButtonClickListener(search, SearchActivity.class);
        this.setButtonClickListener(vendor, VendorActivity.class);
        return this.view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }

    private void setButtonClickListener(Button button, Class<?> cls){
        button.setOnClickListener((view) -> {
            if (switchActivity != null) switchActivity.onClickSwitch(cls);
        });
    }

}
