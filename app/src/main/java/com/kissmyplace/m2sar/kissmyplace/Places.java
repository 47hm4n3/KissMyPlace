package com.kissmyplace.m2sar.kissmyplace;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;


public class Places {
    public List<LatLng> placesList;
    public List<LatLng> noviceList;
    public List<LatLng> mediumList;
    public List<LatLng> expertList;

    private int index;

    public Places(){
        placesList = new ArrayList<LatLng>();
        noviceList = new ArrayList<LatLng>();
        mediumList = new ArrayList<LatLng>();
        expertList = new ArrayList<LatLng>();
        index = 0;
    }

    public LatLng getCurrentEntry() {
        return placesList.get(index);
    }

    public void add(LatLng p){
        placesList.add(p);
    }

    public void find(){
        index++;
    }

    public int getEntry(){ return index;}
    public boolean end(){
        return index == placesList.size()-1;
    }

    public void fillPositions(){
        placesList.clear();
        index = 0;
        placesList.add(new LatLng(-33.87365,151.20689));
        placesList.add(new LatLng(48.866667, 2.333333));
        placesList.add(new LatLng(-27.47093, 153.0235));
    }
}
