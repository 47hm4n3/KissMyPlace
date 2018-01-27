package com.kissmyplace.m2sar.kissmyplace;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static java.util.Locale.FRANCE;

public class MainActivity extends FragmentActivity implements
        OnStreetViewPanoramaReadyCallback,
        OnMapReadyCallback,
        GoogleMap.OnMapClickListener,
        DialogInterface.OnClickListener,
        View.OnClickListener,
        GoogleMap.CancelableCallback {

    private static final String PREFS_NAME = "KissMyPlacePrefs";
    private SharedPreferences prefs;


    private static final int LEVEL_NOVICE = 1;
    private static final int LEVEL_MEDIUM = 2;
    private static final int LEVEL_EXPERT = 3;

    private static final int SCOPE_1 = 500;
    private static final int SCOPE_2 = 1000;
    private static final int SCOPE_3 = 5000;

    private GoogleMap carte;
    public MarkerOptions research;
    private StreetViewPanoramaFragment streetViewPanoramaFragment;
    private SimpleDateFormat sdf;
    private Score myScore;
    private int level;
    private boolean mode;
    private String lname;
    Geocoder geocoder;
    private Global g;

    private FloatingActionButton home;
    private Intent intent;

    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences(PREFS_NAME, MODE_APPEND);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            lname = extras.getString("lname");
            mode = extras.getBoolean("mode");
            level = extras.getInt("level");
        }

        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        g = Global.getInstance();
        sdf = new SimpleDateFormat("dd/MM/yyyy", FRANCE);
        myScore = new Score(lname, level, 0, sdf.format(Calendar.getInstance().getTime()));
        g.getPlaces().fillPositions();

        home = findViewById(R.id.goHome);
        home.setOnClickListener(this);

        alertDialog = new AlertDialog.Builder(this).create();

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
        streetViewPanorama.setPosition(g.getPlaces().getCurrentEntry().getLatLng());
    }

    @Override
    public void onMapReady(GoogleMap map) {
        Log.d("MAP", "PASSAGE 1");
        carte = map;
        carte.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Global g = Global.getInstance();
        research = new MarkerOptions().position(g.getPlaces().getCurrentEntry().getLatLng());
        carte.addMarker(research).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        carte.addMarker(new MarkerOptions().position(latLng));
        scoringCircle(carte, 1);
        Log.d("MAP", "PASSAGE 2");
        carte.animateCamera(CameraUpdateFactory.newLatLng(research.getPosition()), 1500, this);
        manageScore(level, g.getPlaces().getCurrentEntry().getLatLng(), latLng);
    }

    public int distance(LatLng l1, LatLng l2) {
        float[] res = new float[10];
        int a = 0;
        Location.distanceBetween(l1.latitude, l1.longitude, l2.latitude, l2.longitude, res);
        for (int i = 0; i < 10; i++) {
            a++;
            Log.d("Distance", "PASSAGE 3 :: " + i + "  " + res[i]);
        }
        return (int) res[0];
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        intent = new Intent(this, AccueilActivity.class);
        g = Global.getInstance();
        Log.d("FINISH", " PASSAGE " + g.getPlaces().getEntry() + " List size" + g.getPlaces().placeList.size());
        if (g.getPlaces().end()) {
            Log.d("FINISH", "PASSAGE");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Share your new performance ?")
                    .setTitle("Share")
                    .setIcon(R.drawable.ic_action_score)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent i = new Intent(Intent.ACTION_SEND);
                            i.setType("text/plain");
                            i.putExtra(Intent.EXTRA_SUBJECT, "SUBJECT");
                            i.putExtra(Intent.EXTRA_TEXT, "My new performance today is : " + myScore.score);
                            startActivityForResult(Intent.createChooser(i, "SHARE"), 1);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            g.getPlaces().fillPositions();
                            startActivity(intent);
                        }
                    });
            ArrayList<Score> l = retrieveScores();
            l.add(myScore);
            persistScores(l);
            AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.show();
        } else
            g.getPlaces().find();
        carte.clear();

        // StreetMapFragment sf = (StreetMapFragment) getFragmentManager().findFragmentById(R.id.street_fragment);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);
        dialog.dismiss();
        carte.animateCamera(CameraUpdateFactory.zoomTo(0));
    }

    public void scoringCircle(GoogleMap map, int level) {

        PolylineOptions line = new PolylineOptions();
        line.add(research.getPosition(), g.getPlaces().getCurrentEntry().getLatLng());
        line.color(Color.BLACK);
        line.width(5);
        carte.addPolyline(line);

        CircleOptions far = new CircleOptions();
        far.center(research.getPosition());//centre du cerle
        far.radius(SCOPE_3 * 200 * level); //RAYON
        far.strokeColor(Color.RED);//Couleur du bord
        far.fillColor(0x30ff0000);
        far.strokeWidth(5);

        CircleOptions medium = new CircleOptions();
        medium.center(research.getPosition());//centre du cerle
        medium.radius(SCOPE_2 * 1000 * level); //RAYON
        medium.strokeColor(Color.BLUE);//Couleur du bord
        medium.fillColor(0x300000ff);
        medium.strokeWidth(5);

        CircleOptions near = new CircleOptions();
        near.center(research.getPosition());//centre du cerle
        near.radius(SCOPE_1 * 1000 * level); //RAYON
        near.strokeColor(Color.GREEN);//Couleur du bord
        near.fillColor(0x3000ff00);
        near.strokeWidth(5);

        carte.addCircle(far);
        carte.addCircle(medium);
        carte.addCircle(near);
    }

    private void manageScore(int level, LatLng src, LatLng dest) {
        int distance = distance(src, dest);
        alertDialog.setIcon(R.drawable.ic_action_up);
        alertDialog.setMessage("Good job ...");
        switch (level) {
            case LEVEL_NOVICE:
                if (!mode) {
                    if (distance <= SCOPE_1) myScore.score += 50;
                    else if (distance > SCOPE_1 && distance <= SCOPE_2) myScore.score += 30;
                    else if (distance > SCOPE_2 && distance <= SCOPE_3) myScore.score += 20;
                    else {
                        myScore.score -= 10;
                        alertDialog.setMessage("Too far ...");
                        alertDialog.setIcon(R.drawable.ic_action_down);
                    }
                } else {
                    try {
                        if (geocoder.getFromLocation(src.latitude,  src.longitude, 1).get(0).getCountryName() ==
                                geocoder.getFromLocation(dest.latitude,  dest.longitude, 1).get(0).getCountryName()) {
                            alertDialog.setMessage("Yes " + geocoder.getFromLocation(dest.latitude,  dest.longitude, 1).get(0).getCountryName().toUpperCase());
                            myScore.score += 100;
                        } else {
                            myScore.score -= 10;
                            alertDialog.setMessage("Wrong country ...");
                            alertDialog.setIcon(R.drawable.ic_action_down);
                        }

                    } catch (IOException e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case LEVEL_MEDIUM:
                if (!mode) {
                    if (distance <= SCOPE_1) myScore.score += 50;
                    else if (distance > SCOPE_1 && distance <= SCOPE_2) myScore.score += 30;
                    else if (distance > SCOPE_2 && distance <= SCOPE_3) myScore.score += 20;
                    else {
                        myScore.score -= 10;
                        alertDialog.setMessage("Too far ...");
                        alertDialog.setIcon(R.drawable.ic_action_down);
                    }
                } else {
                    try {
                        if (geocoder.getFromLocation(src.latitude,  src.longitude, 1).get(0).getCountryName() ==
                                geocoder.getFromLocation(dest.latitude,  dest.longitude, 1).get(0).getCountryName()) {
                            alertDialog.setMessage("Yes " + geocoder.getFromLocation(dest.latitude,  dest.longitude, 1).get(0).getCountryName().toUpperCase());
                            myScore.score += 200;
                        } else {
                            myScore.score -= 50;
                            alertDialog.setMessage("Wrong country ...");
                            alertDialog.setIcon(R.drawable.ic_action_down);
                        }

                    } catch (IOException e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case LEVEL_EXPERT:
                if (!mode) {
                    if (distance <= SCOPE_1) myScore.score += 50;
                    else if (distance > SCOPE_1 && distance <= SCOPE_2) myScore.score += 30;
                    else if (distance > SCOPE_2 && distance <= SCOPE_3) myScore.score += 20;
                    else {
                        myScore.score -= 10;
                        alertDialog.setMessage("Too far ...");
                        alertDialog.setIcon(R.drawable.ic_action_down);
                    }
                } else {
                    try {
                        if (geocoder.getFromLocation(src.latitude,  src.longitude, 1).get(0).getCountryName() ==
                                geocoder.getFromLocation(dest.latitude,  dest.longitude, 1).get(0).getCountryName()) {
                            alertDialog.setMessage("Yes " + geocoder.getFromLocation(dest.latitude,  dest.longitude, 1).get(0).getCountryName().toUpperCase());
                            myScore.score += 250;
                        } else {
                            myScore.score -= 100;
                            alertDialog.setMessage("Wrong country ...");
                            alertDialog.setIcon(R.drawable.ic_action_down);
                        }
                    } catch (IOException e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                break;
        }
        g.setScore(myScore.score);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == home.getId()) {
            intent = new Intent(this, AccueilActivity.class);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    @Override
    public void onFinish() {
        carte.animateCamera(CameraUpdateFactory.zoomTo(0));
        carte.animateCamera(CameraUpdateFactory.zoomTo(6));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        alertDialog.setTitle(" Hey " + lname.toUpperCase() + ", your actual score is : " + myScore.score);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Next", this);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private void persistScores(ArrayList<Score> list) {
        prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        System.out.println("PERSISTING SCORE");
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray l = new JSONArray();
        for (Score s : list) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("playerName", s.playerName);
                obj.put("level", s.level);
                obj.put("score", s.score);
                obj.put("date", s.date);
                l.put(obj);
            } catch (JSONException e) {
                System.out.println("Error with JSON object");
            }
        }
        editor.putString("selectedScores", l.toString());
        editor.apply();
    }

    private ArrayList<Score> retrieveScores() {
        prefs = getSharedPreferences(PREFS_NAME, MODE_APPEND);
        System.out.println("RETRIEVING SCORES");
        ArrayList<Score> oldScores = new ArrayList<>();
        if (prefs == null) {
            System.out.println("RETRIEVING NULL");
            return null;
        }
        String jsonArrayString = prefs.getString("selectedScores", "");
        if (!TextUtils.isEmpty(jsonArrayString)) {
            try {
                JSONArray jsonArray = new JSONArray(jsonArrayString);
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = (JSONObject) jsonArray.get(i);
                        oldScores.add(new Score((String) obj.get("playerName"), (int) obj.get("level"), (int) obj.get("score"), (String) obj.get("date")));
                    }
                }
            } catch (JSONException e) {
                System.out.println("Error parsing JSON file in main");
            }
        }
        for (Score str : oldScores) {
            System.out.println(str);
        }
        return oldScores;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            Intent i = new Intent(this, AccueilActivity.class);
            startActivity(i);
        }
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onBackPressed() {
        System.out.println("BACK PRESSED");
    }
}
