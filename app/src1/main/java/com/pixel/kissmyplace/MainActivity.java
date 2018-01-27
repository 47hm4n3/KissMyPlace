package com.pixel.kissmyplace;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity {

    private GoogleMap googleMap;
    public MarkerOptions research;
    private SupportStreetViewPanoramaFragment streetViewPanoramaFragment;
    private SupportMapFragment mapFragment;

    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Global g = Global.getInstance();
        g.getPlaces().run1();
        setContentView(R.layout.activity_main);

        // Begin the transaction
        ft = getSupportFragmentManager().beginTransaction();

        // Replace the contents of the container with the new fragment
        ft.replace(R.id.mapframe, new GameFragment());

        // or ft.add(R.id.your_placeholder, new FooFragment());
        //ft.add(R.id.mainframe, new GameFragment());

        // Complete the changes added above
        ft.commit();
    }

}
