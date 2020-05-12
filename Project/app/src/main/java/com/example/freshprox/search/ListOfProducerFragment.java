package com.example.freshprox.search;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.freshprox.R;
import com.example.freshprox.SwitchActivity;
import com.example.freshprox.vendor.ListOfVendor;
import com.example.freshprox.vendor.VendorAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListOfProducerFragment extends Fragment {

    View view;
    ListOfVendor vendors;
    SearchActivity sA;

    public ListOfProducerFragment() {

    }

    public static ListOfProducerFragment newInstance() {
        return new ListOfProducerFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.sA = (SearchActivity) getActivity();

        super.onCreate(savedInstanceState);
        this.view = inflater.inflate(R.layout.fragment_list_of_producer, container, false);

        vendors = new ListOfVendor(false);
        VendorAdapter adapter = new VendorAdapter(getContext(), vendors);
        adapter.setSwitchActivity((SwitchActivity) getActivity());
        ListView listView = view.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        Button btn = view.findViewById(R.id.btnMap);
        if(getResources().getDisplayMetrics().heightPixels<1200 || getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            btn.setOnClickListener(v -> {
            sA.changeFragment();
            });
        } else {
            ((ViewGroup) btn.getParent()).removeView(btn);
        }
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
