package com.kissmyplace.m2sar.kissmyplace;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by pixel on 21/01/2018.
 */

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String PREFS_NAME = "KissMyPlacePrefs";
    private static final String PLAYER_NAME = "lastProfileName";
    private static final String PLAYER_LNAME = "lastProfileLName";
    private SharedPreferences prefs;


    private Intent intent;
    private EditText name;
    private EditText lname;
    private Button done;
    private String playerName;
    private String playerLName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        prefs = getSharedPreferences(PREFS_NAME, MODE_APPEND);

        playerName = retrieveString(PLAYER_NAME);
        playerLName = retrieveString(PLAYER_LNAME);

        System.out.println("NAME = "+ playerName + "LAST NAME = " + playerLName);

        done = findViewById(R.id.doneBtn);
        done.setOnClickListener(this);

        name = findViewById(R.id.playerName);
        lname = findViewById(R.id.playerLName);

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            lname.setText(extras.getString("lname"));
            name.setText(extras.getString("name"));
        }

        name.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == 66 && getWindow().getCurrentFocus().getId() == name.getId()) {
                    //lname.requestFocus();
                }
                return false;
            }
        });

        lname.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == 66 && getWindow().getCurrentFocus().getId() == lname.getId()) {
                    done.requestFocus();
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.doneBtn:
                if (!((name.getText().toString()).isEmpty() || (lname.getText().toString()).isEmpty())) {
                    playerName = name.getText().toString();
                    playerLName = lname.getText().toString();
                    persistProfile(playerName,  playerLName);
                    intent = new Intent(this, AccueilActivity.class);
                    intent.putExtra("name", playerName);
                    intent.putExtra("lname", playerLName);
                    setResult(1, intent);
                    finish();
                    //startActivity(intent);
                } else  Toast.makeText(getApplicationContext(), "Please fill the two fields ;-)", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private String retrieveString(String str) {
        if (prefs.getString("", "") == null) {
            return "Default";
        }
        return prefs.getString(str, "");
    }

    private void persistProfile(String name, String lname) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PLAYER_NAME, name);
        editor.putString(PLAYER_LNAME, lname);
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        System.out.println("BACK PRESSED");
    }
}
