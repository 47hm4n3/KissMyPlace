package com.pixel.kissmyplace;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by pixel on 21/01/2018.
 */

public class GameFragment extends Fragment implements OnStreetViewPanoramaReadyCallback, OnMapReadyCallback,
        GoogleMap.OnMapClickListener,
        DialogInterface.OnClickListener {

    private GoogleMap carte;
    public MarkerOptions research;
    private StreetViewPanoramaFragment streetViewPanoramaFragment;
    private View view;
    private FragmentTransaction ft;
    private SupportStreetViewPanoramaFragment svpf;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ft = getFragmentManager().beginTransaction();

        // Replace the contents of the container with the new fragment
        ft.replace(R.id.streetframe, new StreetViewFragment());
        ft.replace(R.id.mapframe, new MapViewFragment());

        // or ft.add(R.id.your_placeholder, new FooFragment());
        //ft.add(R.id.streetview, new StreetViewFragment());
        //ft.add(R.id.mapview, new MapViewFragment());

        // Complete the changes added above
        ft.commit();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {

    }
}
