package com.kissmyplace.m2sar.kissmyplace;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


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
        Random r = new Random();
        index = r.nextInt(placesList.size()-1);
        return placesList.remove(index);
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
        placesList.add(new LatLng(48.8030784, 2.1273862000000463));
        placesList.add(new LatLng(48.85837009999999,2.2944813000000295));
        placesList.add(new LatLng(-33.8567844,151.21529669999995));
        placesList.add(new LatLng(40.4319077,116.57037489999993));
        placesList.add(new LatLng(19.4326018,-99.13320490000001));
        placesList.add(new LatLng(48.88670459999999,2.34310430000005));
        placesList.add(new LatLng(41.0106848,28.968068099999982));
        placesList.add(new LatLng(39.9163447,116.39715460000002));
        placesList.add(new LatLng(43.0828162,-79.07416289999998));
        placesList.add(new LatLng(40.7660682,-73.97702900000002));
        placesList.add(new LatLng(40.758895,-73.98513100000002));
        placesList.add(new LatLng(48.87335400000001,2.2947073000000273));
        placesList.add(new LatLng(36.11470649999999,-115.17284840000002));
        placesList.add(new LatLng(21.2762181,-157.82709139999997));
        placesList.add(new LatLng(51.50072919999999,-0.12462540000001354));
        placesList.add(new LatLng(35.6585696,139.74548400000003));
        placesList.add(new LatLng(48.850019,2.2796899999999596));

    }
}
