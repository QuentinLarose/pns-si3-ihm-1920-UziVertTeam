package com.example.freshprox.vendor;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.freshprox.R;

public class VendorActivity extends AppCompatActivity {
    private ListOfVendor vendors;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vendor_activity);
        vendors = new ListOfVendor(false);
        VendorAdapter adapter = new VendorAdapter(this, vendors);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        SearchView searchBar = findViewById(R.id.searchBar);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(searchBar.getQuery());
                return false;
            }
        });
    }
}
