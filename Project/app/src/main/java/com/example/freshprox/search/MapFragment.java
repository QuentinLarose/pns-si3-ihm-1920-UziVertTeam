package com.example.freshprox.search;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.example.freshprox.BuildConfig;
import com.example.freshprox.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

public class MapFragment extends Fragment implements View.OnClickListener{
    private MapView map;
    IMapController mapController;

    public MapFragment(){
        Log.d("LAROSE","MapFrgament");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Log.d("LAROSE","Creation map debut OnCreateView");
        View v = inflater.inflate(R.layout.map_fragment, container, false);
        map = (MapView) v.findViewById(R.id.map);
        Log.d("LAROSE","map= "+map);
        /*
        map.setTileSource(TileSourceFactory.MAPNIK);
        configMap();
         */
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        GeoPoint startPoint = new GeoPoint(43.55,7.0167);
        mapController = map.getController();
        mapController.setZoom(10.0);
        mapController.setCenter(startPoint);

        ArrayList<OverlayItem> items = new ArrayList<>();
        OverlayItem home = new OverlayItem("Ptit Tieks","my home",new GeoPoint(43.55710983276367,6.980123519897461));
        Drawable m = home.getMarker(0);
        items.add(home);
        items.add(new OverlayItem("King Tacos","Tacos le S",new GeoPoint(43.555202,6.9719)));

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
        return v;
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
