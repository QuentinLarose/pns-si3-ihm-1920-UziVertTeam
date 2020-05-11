package com.example.freshprox.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.freshprox.R;
import com.example.freshprox.SwitchActivity;

public class MainActivity extends AppCompatActivity implements SwitchActivity {
    MainFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
        Log.d("TAG","MainActivity");
    }

    @Override
    public void onClickSwitch(Class<?> cls) {
        Intent intent = new Intent(getApplicationContext(), cls);
        startActivity(intent);
    }


}
