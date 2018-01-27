package com.kissmyplace.m2sar.kissmyplace;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by youssefamri on 19/01/2018.
 */

public class Global {
    private static Global instance;

    // Global variable
    private Places circuit;
    private int score;
    private List<Integer> allScore;
    public int level;
    public boolean mode;

    // Restrict the constructor from being instantiated
    private Global(){
        this.circuit = new Places();
        this.score = 0;
        this.allScore = new ArrayList<Integer>();
    }

    public void setScore(int d){
        this.score=d;
    }
    public int getScore(){
        return this.score;
    }

    public Places getPlaces(){
        return circuit;
    }
    public static synchronized Global getInstance(){
        if(instance==null){
            instance=new Global();
        }
        return instance;
    }
}
