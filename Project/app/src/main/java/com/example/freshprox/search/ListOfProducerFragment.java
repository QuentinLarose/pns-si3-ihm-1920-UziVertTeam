package com.example.freshprox.search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.freshprox.R;
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
    public ListOfProducerFragment() {
        // Required empty public constructor
    }

    public static ListOfProducerFragment newInstance() {
        return new ListOfProducerFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        this.view = inflater.inflate(R.layout.fragment_list_of_producer, container, false);

        vendors = new ListOfVendor(false);
        VendorAdapter adapter = new VendorAdapter(getContext(), vendors);
        ListView listView = view.findViewById(R.id.listView);
        listView.setAdapter(adapter);
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
