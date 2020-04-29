package com.example.freshprox.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.example.freshprox.IRotatationChange;
import com.example.freshprox.R;
import com.example.freshprox.SwitchActivity;
import com.example.freshprox.main.MainFragment;
import com.example.freshprox.search.SearchActivity;

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
    }

    @Override
    public void onClickSwitch(Class<?> cls) {
        Intent intent = new Intent(getApplicationContext(), cls);
        startActivity(intent);
    }


}
