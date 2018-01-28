package com.kissmyplace.m2sar.kissmyplace;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import static java.util.Locale.FRANCE;


/**
 * Created by pixel on 21/01/2018.
 */

public class ScoreActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int BY_NAME = 1;
    private static final int BY_LEVEL = 2;
    private static final int BY_SCORE = 3;
    private static final int BY_DATE = 4;

    private static final String PREFS_NAME = "KissMyPlacePrefs";
    private SharedPreferences prefs;

    private Button profile;
    private Button level;
    private Button score;
    private Button date;

    private int orderBy;

    private ListView scoresLV;

    private MenuInflater menuInflater;
    private ArrayList<Score> scores;

    private ScoreAdapter scoreAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        prefs = getSharedPreferences(PREFS_NAME, MODE_APPEND);

        scores = retrieveScores();
        if (scores == null) {
            scores = new ArrayList<>();
        }

        orderBy = 1;

        profile = findViewById(R.id.sortProfile);
        profile.setOnClickListener(this);

        level = findViewById(R.id.sortLevel);
        level.setOnClickListener(this);

        score = findViewById(R.id.sortScore);
        score.setOnClickListener(this);

        date = findViewById(R.id.sortDate);
        date.setOnClickListener(this);


        scoresLV = findViewById(R.id.scoreList);

        scoreAdapter = new ScoreAdapter(this, scores);

        scoresLV.setAdapter(scoreAdapter);
        orderScores(orderBy);
        displayeScores();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_scores, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.doneScores:
                Intent intent = new Intent(this, AccueilActivity.class);
                finish();
                startActivity(intent);
                break;
            case R.id.refreshScores:
                displayeScores();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.sortProfile:
                orderScores(BY_NAME);
                break;
            case R.id.sortLevel:
                orderScores(BY_LEVEL);
                break;
            case R.id.sortScore:
                orderScores(BY_SCORE);
                break;
            case R.id.sortDate:
                orderScores(BY_DATE);
                break;
            default:
                break;
        }
    }

    private void orderScores(int tag) {
        ArrayList<Score> list = new ArrayList<Score>();
        switch (tag) {
            case BY_NAME:
                Collections.sort(scores, Score.compareByName);
                displayeScores();
                orderBy = BY_NAME;
                break;
            case BY_LEVEL:
                Collections.sort(scores, Score.compareByLevel);
                displayeScores();
                orderBy = BY_LEVEL;
                break;
            case BY_SCORE:
                Collections.sort(scores, Score.compareByScore);
                displayeScores();
                orderBy = BY_SCORE;
                break;
            case BY_DATE:
                Collections.sort(scores, Score.compareByDate);
                displayeScores();
                orderBy = BY_DATE;
                break;
            default:
                break;
        }
    }

    private void addScore(Score newScore) {
        scores.add(newScore);
    }

    private void displayeScores() {
        scoreAdapter.notifyDataSetChanged();
    }

    private void persistScores(ArrayList<Score> list) {
        System.out.println("PERSISTING SCORES");
        if (prefs == null) {
            System.out.println("PERSISTING NULL");
            return;
        }
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
                System.out.println("Error parsing JSON file in score");
            }
        }
        for (Score s : oldScores) {
            System.out.println(s);
        }
        return oldScores;
    }

    @Override
    public void onBackPressed() {
        System.out.println("BACK PRESSED");
    }

    @Override
    protected void onDestroy() {
        persistScores(scores);
        super.onDestroy();
    }
}
