package com.pixel.kissmyplace;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements OnStreetViewPanoramaReadyCallback,OnMapReadyCallback,
        GoogleMap.OnMapClickListener,
        DialogInterface.OnClickListener {
    private GoogleMap carte;
    public MarkerOptions research;
    private StreetViewPanoramaFragment streetViewPanoramaFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Global g = Global.getInstance();
        g.getPlaces().run1();
        setContentView(R.layout.activity_main);
        streetViewPanoramaFragment =
                (StreetViewPanoramaFragment) getFragmentManager()
                        .findFragmentById(R.id.street_fragment);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        Global g = Global.getInstance();
        streetViewPanorama.setPosition(g.getPlaces().getCurrentPlace().getLatLng());
    }

    @Override
    public void onMapReady(GoogleMap map) {
        Log.d("MAP","PASSAGE 1");
        carte = map;
        carte.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Global g = Global.getInstance();
        research = new MarkerOptions().position(g.getPlaces().getCurrentPlace().getLatLng());

        carte.addMarker(research).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        carte.addMarker(new MarkerOptions().position(latLng));
        scoringCircle(carte,1);
        Log.d("MAP","PASSAGE 2");
        carte.animateCamera(CameraUpdateFactory.newLatLng(research.getPosition()),1500,null);
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Resultat ");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Next",this);
        alertDialog.show();
    }

    public void distance(LatLng l1,LatLng l2) {
        float[]res =new float[10];
        int a = 0;
        Location.distanceBetween(l1.latitude,l1.longitude,l2.latitude,l2.longitude,res);
        for (int i=0;i<10;i++) {
            a++;
            Log.d("Distance", "PASSAGE 3 :: " +i+"  "+res[i]);
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Global g = Global.getInstance();
        Log.d("FINISH"," PASSAGE "+g.getPlaces().getPosition()+" List size"+g.getPlaces().run.size());
        if(g.getPlaces().end()) {
            Log.d("FINISH","PASSAGE");
            g.getPlaces().run1();
            Intent intent = new Intent(this,AccueilActivity.class);
            startActivity(intent);
        }else
            g.getPlaces().find();
        carte.clear();


        // StreetMapFragment sf = (StreetMapFragment) getFragmentManager().findFragmentById(R.id.street_fragment);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);

        dialog.dismiss();
    }

    public void scoringCircle(GoogleMap map,int lvl){
        CircleOptions far = new CircleOptions();
        far.center(research.getPosition());//centre du cerle
        far.radius(2000000*lvl); //RAYON
        far.strokeColor(Color.RED);//Couleur du bord
        far.fillColor(0x30ff0000);
        far.strokeWidth(5);
        CircleOptions medium = new CircleOptions();
        medium.center(research.getPosition());//centre du cerle
        medium.radius(1000000*lvl); //RAYON
        medium.strokeColor(Color.BLUE);//Couleur du bord
        medium.fillColor(0x300000ff);
        medium.strokeWidth(5);
        CircleOptions near = new CircleOptions();
        near.center(research.getPosition());//centre du cerle
        near.radius(500000*lvl); //RAYON
        near.strokeColor(Color.GREEN);//Couleur du bord
        near.fillColor(0x3000ff00);
        near.strokeWidth(5);
        carte.addCircle(far);
        carte.addCircle(medium);
        carte.addCircle(near);
    }

}
