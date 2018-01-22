package com.pixel.kissmyplace;

/**
 * Created by pixel on 22/01/2018.
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.support.v7.app.AppCompatActivity;

public class AccueilActivity extends AppCompatActivity  implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        Button b = (Button)findViewById(R.id.btn_easy);
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
