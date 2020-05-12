package com.example.freshprox.search;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.example.freshprox.R;

import org.osmdroid.config.Configuration;


public class CategorieFragment extends Fragment implements View.OnClickListener {
    SearchActivity sA;
    CategorieFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        this.sA = (SearchActivity) getActivity();
        Log.d("LAROSE","Creation map debut OnCreateView");
        Configuration.getInstance().load(getContext(), PreferenceManager.getDefaultSharedPreferences(getContext()));
        View view = inflater.inflate(R.layout.categorie_fragment, container, false);
        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
