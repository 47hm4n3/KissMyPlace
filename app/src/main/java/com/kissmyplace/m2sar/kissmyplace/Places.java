package com.kissmyplace.m2sar.kissmyplace;

import java.util.ArrayList;
import java.util.List;


public class Places {
    public List<Place> placeList;
    public List<Place> noviceList;
    public List<Place> mediumList;
    public List<Place> expertList;

    private int entry;

    public Places(){
        placeList = new ArrayList<Place>();
        noviceList = new ArrayList<Place>();
        mediumList = new ArrayList<Place>();
        expertList = new ArrayList<Place>();
        entry = 0;
    }

    public Place getCurrentEntry() {
        return placeList.get(entry);
    }

    public void add(Place p){
        placeList.add(p);
    }

    public void find(){
        entry++;
    }

    public int getEntry(){ return entry;}
    public boolean end(){
        return entry == placeList.size()-1;
    }

    public void fillPositions(){
        placeList.clear();
        Place p = new Place(-33.87365,151.20689);
        placeList.add(p);
        p = new Place(48.866667, 2.333333);
        placeList.add(p);
        p = new Place(-27.47093, 153.0235);
        placeList.add(p);
        entry = 0;
    }
}
