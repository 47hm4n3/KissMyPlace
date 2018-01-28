package com.kissmyplace.m2sar.kissmyplace;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
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
import android.widget.CompoundButton;
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

    private FloatingActionButton profile;
    private Button novice;
    private Button medium;
    private Button expert;
    private Button score;
    private Switch modeCountry;
    private Switch modeReverse;
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

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permissions != null) {
            checkPermissions();
        }
        prefs = getSharedPreferences(PREFS_NAME, MODE_APPEND);

        retrieveProfile();

        profile = findViewById(R.id.addProfile);
        profile.setOnClickListener(this);

        modeCountry = findViewById(R.id.countryMode);
        modeCountry.setChecked(false);
        modeCountry.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    modeReverse.setChecked(false);
                    Toast.makeText(getApplicationContext(), "THE TOLERATED ERROR IS WITHIN THE SAME COUNTRY AS THE PLACE TO FIND ", Toast.LENGTH_LONG).show();
                }
            }
        });

        modeReverse = findViewById(R.id.reverseMode);
        modeReverse.setChecked(false);
        modeReverse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    modeCountry.setChecked(false);
                    Toast.makeText(getApplicationContext(), "THE PURPOSE IS TO BE THE FARTHEST POSSIBLE FROM THE PLACE TO FIND ", Toast.LENGTH_LONG).show();
                }
            }
        });

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
                alertDialog.setMessage("KissMyPlace is a game for evaluating you geographic knowledge, by finding places on the globe according to a given indications hidden in a street views.\nWith thee difficulty levels and two special plying modes, hope you will enjoy it ;-) ");
                alertDialog.show();
                break;
            case R.id.quit:
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
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setIcon(R.drawable.ic_action_no_network);
        alertDialog.setTitle("No connexion detected");
        alertDialog.setMessage("Please check your data connexion \nKissMyPlace needs internet connexion to display maps");
        alertDialog.setCancelable(true);
        switch (v.getId()) {
            case R.id.addProfile:
                intent = new Intent(this, ProfileActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("lname", lname);
                startActivityForResult(intent, 1);
                break;
            case R.id.noviceBtn:
                if (checkConnectivity()) {
                    intent = new Intent(this, MainActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("lname", lname);
                    intent.putExtra("modeCountry", modeCountry.isChecked());
                    intent.putExtra("modeReverse", modeReverse.isChecked());
                    intent.putExtra("level", LEVEL_NOVICE);
                    startActivity(intent);
                } else {
                    alertDialog.show();
                }
                break;
            case R.id.mediumBtn:
                if (checkConnectivity()) {
                    intent = new Intent(this, MainActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("lname", lname);
                    intent.putExtra("modeCountry", modeCountry.isChecked());
                    intent.putExtra("modeReverse", modeReverse.isChecked());
                    intent.putExtra("level", LEVEL_MEDIUM);
                    startActivity(intent);
                } else {
                    alertDialog.show();
                }
                break;
            case R.id.expertBtn:
                if (checkConnectivity()) {
                    intent = new Intent(this, MainActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("lname", lname);
                    intent.putExtra("modeCountry", modeCountry.isChecked());
                    intent.putExtra("modeReverse", modeReverse.isChecked());
                    intent.putExtra("level", LEVEL_EXPERT);
                    startActivity(intent);
                } else {
                    alertDialog.show();
                }
                break;
            case R.id.scoresBtn:
                intent = new Intent(this, ScoreActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
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

    private void retrieveProfile() {
        if (prefs.getString(PLAYER_NAME, "") == "" || prefs.getString(PLAYER_LNAME, "") == "") {
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
                            name = prefs.getString(PLAYER_NAME, "No_");
                            lname = prefs.getString(PLAYER_LNAME, "Name");
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        name = data.getStringExtra("name");
        lname = data.getStringExtra("lname");
    }

    private boolean checkConnectivity() {

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return ((networkInfo != null) && networkInfo.isConnected());
    }

    @Override
    public void onBackPressed() {
        System.out.println("BACK PRESSED");
    }

}
