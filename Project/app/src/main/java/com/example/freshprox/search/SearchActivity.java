package com.example.freshprox.search;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.freshprox.R;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, IButtonClickListener{
    private IButtonClickListener mCallBack;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toast.makeText(this,"dans le fragment dessous avec: ", Toast.LENGTH_LONG).show();
        findViewById( R.id.search_activity_button_map).setOnClickListener(new View.OnClickListener() {
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
        });
    }

    @Override
    public void onClick(View button) {
        Toast.makeText(this,"dans le fragment dessous avec: ", Toast.LENGTH_LONG).show();
        if(button.getId() == R.id.search_activity_button_map)mCallBack.onButtonMapClicked(button);
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
    }


}
