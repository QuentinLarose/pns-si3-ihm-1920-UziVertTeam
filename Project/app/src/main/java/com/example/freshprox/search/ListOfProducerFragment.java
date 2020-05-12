package com.example.freshprox.search;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.freshprox.R;
import com.example.freshprox.vendor.ListOfVendor;
import com.example.freshprox.vendor.Vendor;
import com.example.freshprox.vendor.VendorAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListOfProducerFragment extends Fragment {

    View view;
    ListOfVendor vendors;
    SearchActivity sA;
    public ListOfProducerFragment(SearchActivity sA) {
        this.sA = sA;
    }

    public static ListOfProducerFragment newInstance(SearchActivity sA) {
        return new ListOfProducerFragment(sA);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        this.view = inflater.inflate(R.layout.fragment_list_of_producer, container, false);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("vendors list", null);
        Type type = new TypeToken<ArrayList<Vendor>>() {}.getType();
        ArrayList<Vendor> vendorsList = gson.fromJson(json, type);

        if (vendorsList == null){
            vendorsList = new ArrayList<>();
        }

        vendors = new ListOfVendor(false);
        vendors.addAll(vendorsList);
        VendorAdapter adapter = new VendorAdapter(getContext(), vendors);
        ListView listView = view.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        Button btn = view.findViewById(R.id.btnMap);
        btn.setOnClickListener(v -> {
            sA.changeFragment();
        });
        SearchView searchBar = view.findViewById(R.id.searchBar);
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
        return this.view;
    }
}
