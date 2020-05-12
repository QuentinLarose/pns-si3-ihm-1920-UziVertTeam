package com.example.freshprox.vendor;


import android.content.SharedPreferences;

import com.example.freshprox.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import static android.content.Context.MODE_PRIVATE;

public class ListOfVendor extends ArrayList<Vendor> {

    private String key;

    public ListOfVendor(Boolean empty){
        super();
        if(!empty) {
            Vendor v1 = new Vendor("Légumes d'hiver", new Address("10 rue de la caroute", "06410", "Biot"), Vendor.PraticedPrice.moderate, Arrays.asList(new Vendor.Product[]{Vendor.Product.fruitLegumes}), R.mipmap.legumes, "01234567", 0, 0);
            this.add(v1);
            Vendor v2 = new Vendor("Légumes de printemps", new Address("10 rue de la caroute", "06410", "Biot"), Vendor.PraticedPrice.moderate, Arrays.asList(new Vendor.Product[]{Vendor.Product.fruitLegumes}), R.mipmap.legumes2, "00112233", 0, 0);
            this.add(v2);
            Vendor v3 = new Vendor("Légumes d'antan'", new Address("10 rue de la caroute", "06410", "Biot"), Vendor.PraticedPrice.moderate, Arrays.asList(new Vendor.Product[]{Vendor.Product.fruitLegumes}), R.mipmap.legumes2, "00112233", 2, 2);
            this.add(v3);
            Vendor v4 = new Vendor("Légumes aux saveurs anciennes", new Address("10 rue de la caroute", "06410", "Biot"), Vendor.PraticedPrice.moderate, Arrays.asList(new Vendor.Product[]{Vendor.Product.fruitLegumes}), R.mipmap.legumes2, "00112233", 1, 1);
            this.add(v4);

        }
    }


}
