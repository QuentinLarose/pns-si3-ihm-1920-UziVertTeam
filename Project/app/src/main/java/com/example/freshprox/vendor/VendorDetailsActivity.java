package com.example.freshprox.vendor;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.freshprox.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class VendorDetailsActivity extends AppCompatActivity {

    ListOfVendor vendors;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vendor_layout);
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("vendors list", null);
        Type type = new TypeToken<ArrayList<Vendor>>() {}.getType();
        ArrayList<Vendor> vendorsList = gson.fromJson(json, type);

        if (vendorsList == null){
            vendorsList = new ArrayList<>();
        }

        this.vendors = new ListOfVendor(false);
        vendors.addAll(vendorsList);
        Vendor vendor = vendors.get(getIntent().getIntExtra("position",0));
        RelativeLayout vendorDesc = findViewById(R.id.vend_desc);
        TextView name = vendorDesc.findViewById(R.id.name);
        name.setText(vendor.getName());
        ImageView image = vendorDesc.findViewById(R.id.image);
        image.setImageResource(vendor.getPicture());
        TextView address = vendorDesc.findViewById(R.id.address);
        address.setText(vendor.getAddress().toString());
        TextView categories = vendorDesc.findViewById(R.id.category);
        String ch = "";
        for(Vendor.Product pr : vendor.getSalesProduct()){
            ch+=pr.toString()+", ";
        }
        categories.setText(ch.substring(0,ch.length()-2));
    }
}
