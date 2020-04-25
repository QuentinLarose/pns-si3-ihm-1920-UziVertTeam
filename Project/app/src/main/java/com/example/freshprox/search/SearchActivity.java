package com.example.freshprox.search;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.freshprox.R;
import com.example.freshprox.main.MainFragment;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{
    private IButtonClickListener mCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_map);
        if(mainFragment == null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout_map, new MapFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public void onClick(View button) {
        if(button.getId() == R.id.activity_main_button_map)mCallBack.onButtonMapClicked(button);
    }
}
