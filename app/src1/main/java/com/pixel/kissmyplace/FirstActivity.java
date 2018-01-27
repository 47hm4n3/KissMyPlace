package com.pixel.kissmyplace;

/**
 * Created by pixel on 22/01/2018.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import android.support.v7.app.AppCompatActivity;

public class FirstActivity extends AppCompatActivity  implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        FloatingActionButton fab = findViewById(R.id.addProfile);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                FragmentTransaction profileft = getSupportFragmentManager().beginTransaction();
                profileft.add(R.id.completeframe, new ProfileFragment());
                profileft.commit();
            }
        });



        Button b = (Button)findViewById(R.id.noviceBtn);
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
