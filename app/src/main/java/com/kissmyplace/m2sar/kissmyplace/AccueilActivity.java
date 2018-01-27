package com.kissmyplace.m2sar.kissmyplace;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class AccueilActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String PREFS_NAME = "KissMyPlacePrefs";
    private static final String PLAYER_NAME = "lastProfileName";
    private static final String PLAYER_LNAME = "lastProfileLName";
    private SharedPreferences prefs;

    private static final int LEVEL_NOVICE = 1;
    private static final int LEVEL_MEDIUM = 2;
    private static final int LEVEL_EXPERT = 3;

    private android.support.v4.app.FragmentTransaction ft;
    private FloatingActionButton profile;
    private Button novice;
    private Button medium;
    private Button expert;
    private Button score;
    private Switch mode;
    private Intent intent;
    private MenuInflater menuInflater;
    private String name;
    private String lname;
    private AlertDialog alertDialog;

    private String[] permissions = {Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private static final int PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        //checkPermissions();
        prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        retrieveProfile();

        profile = findViewById(R.id.addProfile);
        profile.setOnClickListener(this);

        mode = findViewById(R.id.playMode);
        mode.setChecked(false);

        novice = findViewById(R.id.noviceBtn);
        novice.setOnClickListener(this);

        medium = findViewById(R.id.mediumBtn);
        medium.setOnClickListener(this);

        expert = findViewById(R.id.expertBtn);
        expert.setOnClickListener(this);

        score = findViewById(R.id.scoresBtn);
        score.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_accueil, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.info:
                Toast.makeText(getApplicationContext(), "Info", Toast.LENGTH_SHORT).show();
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Info :");
                alertDialog.setMessage("INFOS about KissMyPlace \n ");
                alertDialog.show();
                break;
            case R.id.quit :
                Toast.makeText(getApplicationContext(), "Quit", Toast.LENGTH_SHORT).show();
                finishAndRemoveTask();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.addProfile:
                intent = new Intent(this, ProfileActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("lname", lname);
                break;
            case R.id.noviceBtn:
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("lname", lname);
                intent.putExtra("mode", mode.isChecked());
                intent.putExtra("level", LEVEL_NOVICE);
                break;
            case R.id.mediumBtn:
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("lname", lname);
                intent.putExtra("mode", mode.isChecked());
                intent.putExtra("level", LEVEL_MEDIUM);
                break;
            case R.id.expertBtn:
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("lname", lname);
                intent.putExtra("mode", mode.isChecked());
                intent.putExtra("level", LEVEL_EXPERT);
                break;
            case R.id.scoresBtn:
                intent = new Intent(this, ScoreActivity.class);
                break;
            default:
                break;
        }
        startActivity(intent);
    }

    private boolean checkPermissions() {
        String[] requestedPermissions = new String[permissions.length];
        int j = 0;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                requestedPermissions[j] = permission;
                j++;
            }
        }
        if (requestedPermissions.length > 0) {
            ActivityCompat.requestPermissions(this, requestedPermissions, PERMISSION_CODE);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(@NonNull int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "PERMISSION_GRANTED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void retrieveProfile () {
        if (prefs.getString(PLAYER_NAME, "") == null || prefs.getString(PLAYER_LNAME, "") == null) {
            intent = new Intent(this, ProfileActivity.class);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Want to create a new profile ?")
                    .setTitle("New player ?")
                    .setIcon(R.drawable.ic_action_profile)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            name = prefs.getString(PLAYER_NAME, "");
                            lname = prefs.getString(PLAYER_LNAME, "");
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.show();
        } else {
                name = prefs.getString(PLAYER_NAME, "");
                lname = prefs.getString(PLAYER_LNAME, "");
            }
        System.out.println("Name = " + name + "Last name = " + lname);
    }

    @Override
    public void onBackPressed() {
        System.out.println("BACK PRESSED");
    }

}
