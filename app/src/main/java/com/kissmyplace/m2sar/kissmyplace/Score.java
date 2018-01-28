package com.kissmyplace.m2sar.kissmyplace;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import static java.util.Locale.FRANCE;

/**
 * Created by pixel on 21/01/2018.
 */

public class Score {

    String playerName;
    int level;
    int score;
    String date;


    Score () {
        this.playerName = "";
        this.level = 0;
        this.score = 0;
        this.date = "01/01/1970 - 00:00";
    }

    Score (String profile, int level, int score, String date) {
        this.playerName = profile;
        this.level = level;
        this.score = score;
        this.date = date;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static Comparator<Score> compareByName  = new Comparator<Score>() {
        public int compare(Score s1, Score s2) {
            String name1 = s1.getPlayerName().toUpperCase();
            String name2 = s2.getPlayerName().toUpperCase();
            //ascending order
            return name1.compareTo(name2);
    }};


    public static Comparator<Score> compareByLevel  = new Comparator<Score>() {
        public int compare(Score s1, Score s2) {
            int level1 = s1.getLevel();
            int level2 = s2.getLevel();
            //descending order
            return level2-level1;
        }};

    public static Comparator<Score> compareByScore  = new Comparator<Score>() {
        public int compare(Score s1, Score s2) {
            int score1 = s1.getScore();
            int score2 = s2.getScore();
            //descending order
            return score2-score1;
        }};

    public static Comparator<Score> compareByDate  = new Comparator<Score>() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - hh:mm", FRANCE);
        Date date1;
        Date date2;
        public int compare(Score s1, Score s2) {
            try {
                date1 = sdf.parse(s1.getDate());
                date2 = sdf.parse(s2.getDate());
            } catch (ParseException e) {
                System.out.println("Error in parsing date");
            }
            //descending order
            return date1.compareTo(date2);
        }};

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PlayerName = ");
        sb.append(playerName);
        sb.append(" Level = ");
        sb.append(level);
        sb.append(" Score = ");
        sb.append(score);
        sb.append(" Date = ");
        sb.append(date);
        return sb.toString();
    }
}
