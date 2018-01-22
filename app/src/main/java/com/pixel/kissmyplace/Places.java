package com.pixel.kissmyplace;

import java.util.ArrayList;
import java.util.List;


public class Places {
    public List<Place> run;
    private int position;

    public Places(){
        run = new ArrayList<Place>();
        position = 0;
    }

    public Place getCurrentPlace() {
        return run.get(position);
    }

    public void add(Place p){
        run.add(p);
    }

    public void find(){
        position ++;
    }

    public int getPosition(){ return position;}
    public boolean end(){
        return position == run.size()-1;
    }

    public void run1(){
        run.clear();
        Place p = new Place(-33.87365,151.20689);
        run.add(p);
        p = new Place(48.866667, 2.333333);
        run.add(p);
        p = new Place(-27.47093, 153.0235);
        run.add(p);
        position = 0;
    }
}

