package com.example.freshprox.search;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.example.freshprox.R;
import com.example.freshprox.vendor.ListOfVendor;
import com.example.freshprox.vendor.Vendor;
import com.example.freshprox.vendor.VendorActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class MapFragment extends Fragment implements View.OnClickListener{
    private MapView map;
    IMapController mapController;
    SearchActivity sA;
    VendorActivity vA;
    ArrayList<Vendor> vendors;
    boolean param = true;
    GeoPoint point = new GeoPoint(0.0,0.0);

    public MapFragment(){
        Log.d("LAROSE","MapFrgament");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        if(getActivity() instanceof SearchActivity)
            this.sA = (SearchActivity) getActivity();
        else
            this.vA = (VendorActivity) getActivity();
        Log.d("LAROSE","Creation map debut OnCreateView");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Log.d("LAROSE"," TEST");
        param = getArguments().getBoolean("AJOUT");

        Log.d("LAROSE","Valeur param from activity: "+param);

        Configuration.getInstance().load(getContext(), PreferenceManager.getDefaultSharedPreferences(getContext()));

        View view = inflater.inflate(R.layout.map_fragment, container, false);
        Button btn = view.findViewById(R.id.retour);
        if(sA != null) {
            if (getResources().getDisplayMetrics().heightPixels < 1200 || getResources().getConfiguration().orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT) {
                btn.setOnClickListener(v -> {
                    sA.changeFragment();
                });
            } else {
                ((ViewGroup) btn.getParent()).removeView(btn);
            }
        } else if (vA != null){
            btn.setOnClickListener(v -> {
                vA.closeMap(new GeoPoint(point.getLatitude(),point.getLongitude()));
            });
        }
        map = (MapView) view.findViewById(R.id.map);
        Log.d("LAROSE","map= "+map);

        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        GeoPoint startPoint = new GeoPoint(43.55,7.0167);
        mapController = map.getController();
        mapController.setZoom(10.0);
        mapController.setCenter(startPoint);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        Log.d("TAG","Config map");

        map.setFlingEnabled(true);

        ArrayList<OverlayItem> items = new ArrayList<>();

        MapEventsReceiver mReceive = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                if(param){
                    param = false;
                    Log.d("LAROSE","Point = "+p.getLatitude()+" ; "+p.getLongitude());
                    Bundle bundle = new Bundle();
                    bundle.putDouble("lat",p.getLatitude());
                    bundle.putDouble("long",p.getLongitude());
                    items.add(new OverlayItem("test","test",new GeoPoint(p.getLatitude(),p.getLongitude())));
                    point = new GeoPoint(p.getLatitude(),p.getLongitude());
                }
                return true;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        };
        MapEventsOverlay OverlayEvents = new MapEventsOverlay(getContext(), mReceive);
        map.getOverlays().add(OverlayEvents);


        OverlayItem home = new OverlayItem("Ptit Tieks","my home",new GeoPoint(43.55710983276367,6.980123519897461));
        Drawable m = home.getMarker(0);
        items.add(home);
        items.add(new OverlayItem("King Tacos","Tacos le S",new GeoPoint(43.555202,6.9719)));

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("vendors list", null);
        Type type = new TypeToken<ArrayList<Vendor>>() {
        }.getType();
        ArrayList<Vendor> vendorsList = gson.fromJson(json, type);
        if (vendorsList == null) {
            vendorsList = new ArrayList<>();
        }
        this.vendors = new ListOfVendor(false);
        vendors.addAll(vendorsList);

        for(Vendor v : vendors){
            items.add(new OverlayItem(v.getName(),v.getName(),new GeoPoint(v.getLatitude(),v.getLongitude())));
            Log.d("LAROSE","Vendeur = "+v.getName()+" : "+v.getLatitude()+" ; "+v.getLongitude());
        }

        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(getContext(), items, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {

            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                return true;
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return false;
            }
        });

        mOverlay.setFocusItemsOnTap(true);
        map.getOverlays().add(mOverlay);
        Log.d("LAROSE","Creation map FIN");

        return view;
    }

    private void configMap(){
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        Log.d("TAG","Config map");

        map.setFlingEnabled(true);
        IMapController mapController = map.getController();
        mapController.setZoom(6);
        GeoPoint startPoint = new GeoPoint(50.77623, 6.06937);
        mapController.setCenter(startPoint);

    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    public void onClick(View v) {

    }
}
