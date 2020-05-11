package com.example.freshprox.vendor;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.freshprox.R;
import com.example.freshprox.main.MainFragment;
import com.example.freshprox.search.ListOfProducerFragment;

public class VendorActivity extends AppCompatActivity {
    private ListOfVendor vendors;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.vendor_activity);
/*        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main, ListOfProducerFragment.newInstance())
                    .commitNow();
        }*/
    }
}
