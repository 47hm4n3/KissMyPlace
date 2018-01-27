package com.kissmyplace.m2sar.kissmyplace;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by youssefamri on 21/01/2018.
 */

public class Place {
    private double lng;
    private double lat;

    public Place(double lat,double lng) {
        this.lng = lng;
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public LatLng getLatLng(){
        return new LatLng(this.getLat(),this.getLng());
    }

}
