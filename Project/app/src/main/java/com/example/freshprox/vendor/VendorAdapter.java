package com.example.freshprox.vendor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.freshprox.R;

import java.util.ArrayList;
import java.util.List;

public class VendorAdapter extends BaseAdapter implements Filterable {
    LayoutInflater mInflater;
    ListOfVendor vendors;
    ListOfVendor currentVendors;
    private ValueFilter valueFilter;

    public VendorAdapter(Context context, ListOfVendor vendors) {
        this.mInflater = LayoutInflater.from(context);
        this.vendors = vendors;
        this.currentVendors = vendors;
    }


    @Override
    public int getCount() {
        return currentVendors.size();
    }

    @Override
    public Object getItem(int position) {
        return currentVendors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ConstraintLayout layoutItem;

        //(1) : Réutilisation des layouts
        layoutItem = (ConstraintLayout) (convertView == null ? mInflater.inflate(R.layout.vendor_layout, parent, false) : convertView);
        ImageView imageVendor = layoutItem.findViewById(R.id.imageVendor);
        TextView vendorName = layoutItem.findViewById(R.id.vendorName);
        vendorName.setText(currentVendors.get(position).getName());
        imageVendor.setImageResource(currentVendors.get(position).getPicture());
        layoutItem.setOnClickListener(v -> {
            // if (listener!=null) listener.onClickName(position);
            Log.d("VendorAdapter", "click " + (position + 1));
        });

        return layoutItem;
    }


    @Override
    public Filter getFilter() {
        if(valueFilter==null){
            return new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {

        //Invoked in a worker thread to filter the data according to the constraint.
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ListOfVendor filterList = new ListOfVendor(true);
                for (int i = 0; i < vendors.size(); i++) {
                    if ((vendors.get(i).getName().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        filterList.add(vendors.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = vendors.size();
                results.values = vendors;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            currentVendors = (ListOfVendor) results.values;
            notifyDataSetChanged();
        }

    }
}
