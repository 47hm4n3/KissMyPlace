package com.pixel.kissmyplace;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.StreetViewPanoramaView;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by pixel on 19/01/2018.
 */

public class StreetViewFragment extends Fragment implements OnStreetViewPanoramaReadyCallback, DialogInterface.OnClickListener  {

    private StreetViewPanorama streetView;
    private View view;
    private StreetViewPanoramaFragment streetViewPanoramaFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("toto", "Hiiiiiiiii");
        StreetViewFragment svf = new StreetViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);
        Log.d("toto", "Haaaaaaaaaaaa");
        SupportStreetViewPanoramaFragment svpf = (SupportStreetViewPanoramaFragment) getChildFragmentManager().findFragmentById(R.id.fragmentstreet);

        if (svpf != null) {
            svpf.getStreetViewPanoramaAsync(this);
            Log.d("toto", "Hoooooooooooo");
        }

        view = inflater.inflate(R.layout.fragment_streetview, parent, false);
        return view;
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        Log.d("toto", "Hel  lo");
        Global g = Global.getInstance();
        streetViewPanorama.setPosition(new LatLng(-33.87365, 151.20689));
        //streetViewPanorama.setPosition(new LatLng(48.844322,2.356291));
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        Global g = Global.getInstance();
        Log.d("FINISH"," PASSAGE "+g.getPlaces().getPosition()+" List size"+g.getPlaces().placeList.size());
        if(g.getPlaces().end()) {
            Log.d("FINISH","PASSAGE");
            g.getPlaces().run1();
            //Intent intent = new Intent(String.valueOf(FirstActivity.class.));
            //startActivity(intent);
        }else
            g.getPlaces().find();

        // StreetMapFragment sf = (StreetMapFragment) getFragmentManager().findFragmentById(R.id.street_fragment);
        //streetViewPanoramaFragment = (StreetViewPanoramaFragment) getChildFragmentManager().findFragmentById(R.id.streetview);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);

        dialogInterface.dismiss();
    }
}
