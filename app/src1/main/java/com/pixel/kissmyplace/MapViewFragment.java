package com.pixel.kissmyplace;

import android.location.Location;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.DialogInterface;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by pixel on 19/01/2018.
 */

public class MapViewFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener, DialogInterface.OnClickListener {

    public MarkerOptions research;
    private GoogleMap googleMap;
    private MapView mapView;
    private View view;

    private StreetViewPanoramaFragment streetViewPanoramaFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);

        view = inflater.inflate(R.layout.fragment_mapview, parent, false);

        return view;
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mapView = (MapView) view.findViewById(R.id.streetframe);

        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }

    @Override
    public void onMapReady(GoogleMap googlemap) {
        MapsInitializer.initialize(getContext());
        googleMap = googlemap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(new MarkerOptions().position(new LatLng(53.558,9.927)));
        CameraPosition pos = CameraPosition.builder().target(new LatLng(53.558,9.927)).zoom(6).bearing(0).tilt(45).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(pos));
        Log.d("MAP", "PASSAGE 1");
        googleMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Global g = Global.getInstance();
        research = new MarkerOptions().position(g.getPlaces().getCurrentPlace().getLatLng());

        googleMap.addMarker(research).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        googleMap.addMarker(new MarkerOptions().position(latLng));
        scoringCircle(googleMap, 1);
        Log.d("MAP", "PASSAGE 2");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(research.getPosition()), 1500, null);
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Resultat ");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Next", this);
        alertDialog.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Global g = Global.getInstance();
        Log.d("FINISH", " PASSAGE " + g.getPlaces().getPosition() + " List size" + g.getPlaces().placeList.size());
        if (g.getPlaces().end()) {
            Log.d("FINISH", "PASSAGE");
            g.getPlaces().run1();
            Intent intent = new Intent();
            startActivity(intent);
        } else
            g.getPlaces().find();
        //map.clear();


        //StreetMapFragment sf = (StreetMapFragment) getFragmentManager().findFragmentById(R.id.street_fragment);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync((OnStreetViewPanoramaReadyCallback) getActivity());

        dialog.dismiss();
    }

    public void distance(LatLng l1, LatLng l2) {
        float[] res = new float[10];
        int a = 0;
        Location.distanceBetween(l1.latitude, l1.longitude, l2.latitude, l2.longitude, res);
        for (int i = 0; i < 10; i++) {
            a++;
            Log.d("Distance", "PASSAGE 3 :: " + i + "  " + res[i]);
        }
    }

    public void scoringCircle(GoogleMap map, int lvl) {
        CircleOptions far = new CircleOptions();
        far.center(research.getPosition());//centre du cerle
        far.radius(2000000 * lvl); //RAYON
        far.strokeColor(Color.RED);//Couleur du bord
        far.fillColor(0x30ff0000);
        far.strokeWidth(5);
        CircleOptions medium = new CircleOptions();
        medium.center(research.getPosition());//centre du cerle
        medium.radius(1000000 * lvl); //RAYON
        medium.strokeColor(Color.BLUE);//Couleur du bord
        medium.fillColor(0x300000ff);
        medium.strokeWidth(5);
        CircleOptions near = new CircleOptions();
        near.center(research.getPosition());//centre du cerle
        near.radius(500000 * lvl); //RAYON
        near.strokeColor(Color.GREEN);//Couleur du bord
        near.fillColor(0x3000ff00);
        near.strokeWidth(5);
        map.addCircle(far);
        map.addCircle(medium);
        map.addCircle(near);
    }

}
