package com.pixel.kissmyplace;

import java.util.ArrayList;
import java.util.List;


public class Places {
    public List<Place> placeList;
    private int position;

    public Places() {
        placeList = new ArrayList<Place>();
        position = 0;
    }

    public Place getCurrentPlace() {
        return placeList.get(position);
    }

    public void add(Place p) {
        placeList.add(p);
    }

    public void find() {
        position++;
    }

    public int getPosition() {
        return position;
    }

    public boolean end() {
        return position == placeList.size() - 1;
    }

    public void run1() {
        placeList.clear();
        Place p = new Place(-33.87365, 151.20689);
        placeList.add(p);
        p = new Place(48.866667, 2.333333);
        placeList.add(p);
        p = new Place(-27.47093, 153.0235);
        placeList.add(p);
        position = 0;
    }
}

