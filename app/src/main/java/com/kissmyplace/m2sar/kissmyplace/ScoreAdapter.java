package com.kissmyplace.m2sar.kissmyplace;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by pixel on 21/01/2018.
 */

public class ScoreAdapter extends ArrayAdapter<Score> {

    public ScoreAdapter(Context context, ArrayList<Score> scores) {
        super(context, 0, scores);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Log.e("DEBUG LISTVIEW", "Enter getView");
        Score score = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_player, parent, false);
        }
        // Lookup view for data population
        TextView playerName = (TextView) convertView.findViewById(R.id.playerName);
        playerName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        TextView playerLevel = (TextView) convertView.findViewById(R.id.playerLevel);

        TextView playerScore = (TextView) convertView.findViewById(R.id.playerScore);
        TextView playerDate = (TextView) convertView.findViewById(R.id.playerDate);
        // Populate the data into the template view using the data object
        playerName.setText(score.getPlayerName());
        switch (score.getLevel()) {
            case 1 : playerLevel.setText("NOVICE");
                break;
            case 2 : playerLevel.setText("MEDIUM");
                break;
            case 3 : playerLevel.setText("EXPERT");
                break;
            default:
                break;
        }
        playerScore.setText(score.getScore()+"");
        playerDate.setText(score.getDate());
        // Return the completed view to render on screen
        return convertView;
    }
}
