package com.example.freshprox.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.freshprox.R;
import com.example.freshprox.main.MainActivity;
import com.example.freshprox.main.MainFragment;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViewById( R.id.google_connection_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonActivityClicked(v);
            }
        });
        findViewById(R.id.guest_connection_button).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(),"Suppression Fragment: ", Toast.LENGTH_LONG).show();
                onButtonActivityClicked(v);
            }
        });
        Log.d("TAG","HomeActivity");
    }


    public void onButtonActivityClicked(View button) {
        //Toast.makeText(this,"Changement vers MainActivity: ", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
