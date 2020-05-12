package com.example.freshprox.search;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.preference.PreferenceManager;

import com.example.freshprox.R;
import com.example.freshprox.SwitchActivity;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, IButtonClickListener, SwitchActivity {
    private IButtonClickListener mCallBack;
    private MapFragment mapFragment;
    private ListOfProducerFragment lOPF;
    private Boolean isLOPF;
    int density;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        density = metrics.heightPixels;
        Log.d("SearchActivity", String.valueOf(density));
        if(density < 1200) setContentView(R.layout.activity_search);
        else setContentView(R.layout.activity_search_tablet);
        Log.d("SearchActivity", String.valueOf(density));
        this.isLOPF = (savedInstanceState!=null) ? savedInstanceState.getBoolean("isLOPF"): true;
        //Toast.makeText(this,"dans le fragment dessous avec: ", Toast.LENGTH_LONG).show();
        /*findViewById( R.id.search_activity_button_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonMapClicked(v);
            }
        });
        findViewById(R.id.closeMapButton).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mapFragment != null){
                    Toast.makeText(v.getContext(),"Suppression Fragment: ", Toast.LENGTH_LONG).show();
                    getSupportFragmentManager().beginTransaction().remove(mapFragment).commit();
                    mapFragment = null;
                }
            }
        });*/

        mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_map);
        lOPF = (ListOfProducerFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_lOP);

        if(density < 1200 || getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if(mapFragment != null && density >= 1200) getSupportFragmentManager().beginTransaction().remove(mapFragment).commit();
            if (lOPF == null && isLOPF) {
                lOPF = ListOfProducerFragment.newInstance();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_lOP, lOPF)
                        .commitNow();
            }
        } else {
            if (lOPF == null) {
                lOPF = ListOfProducerFragment.newInstance();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_lOP, lOPF)
                        .commitNow();
            }
            mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_map);
            if(mapFragment == null){
                mapFragment = new MapFragment();
                Bundle args = new Bundle();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_map,mapFragment).commit();
            }
        }
    }

    @Override
    public void onClick(View button) {
        Toast.makeText(this,"dans le fragment dessous avec: ", Toast.LENGTH_LONG).show();
        //if(button.getId() == R.id.search_activity_button_map)mCallBack.onButtonMapClicked(button);
        Toast.makeText(this,"ZOUZA: ", Toast.LENGTH_LONG).show();
    }



    @Override
    public void onButtonMapClicked(View button) {

        Toast.makeText(this,"dans le fragment dessous avec: ", Toast.LENGTH_LONG).show();
       mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_map);
        if(mapFragment == null){
            mapFragment = new MapFragment();
            Bundle args = new Bundle();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout_map,mapFragment).commit();
        }
        if(mapFragment == null){
            mapFragment = new MapFragment();
            Bundle args = new Bundle();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout_map,mapFragment).commit();
        }
    }

    public void changeFragment(){
        if(lOPF != null){
            getSupportFragmentManager().beginTransaction().remove(lOPF).commit();
            isLOPF = false;
            lOPF = null;
            Toast.makeText(this,"dans le fragment dessous avec: ", Toast.LENGTH_LONG).show();
            mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_map);
            if(mapFragment == null){
                mapFragment = new MapFragment();
                Bundle args = new Bundle();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_map,mapFragment).commit();
            }
        }else{
            getSupportFragmentManager().beginTransaction().remove(mapFragment).commit();
            isLOPF = true;
            mapFragment = null;
            lOPF = (ListOfProducerFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_lOP);
            if(lOPF == null){
                Log.d("SA", "lOPF created via onCreate");
                lOPF = new ListOfProducerFragment();
                Bundle args = new Bundle();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_lOP, lOPF).commit();
            }
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isLOPF",this.isLOPF);
    }

    @Override
    public void onClickSwitch(Class<?> cls, Object object) {
        Integer position = (Integer) object;
        Intent intent = new Intent(getApplicationContext(), cls);
        intent.putExtra("position", position);
        this.startActivity(intent);
    }
}
