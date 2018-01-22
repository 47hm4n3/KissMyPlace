package com.pixel.kissmyplace;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by pixel on 19/01/2018.
 */

public class ScoreFragment extends Fragment {

    TableLayout scoreTab;
    TableRow head;
    TableRow myRow;

    Button player;
    Button score;
    Button date;

    public ScoreFragment() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        scoreTab = (TableLayout) getView().findViewById(R.id.scoreTable);

        head = new TableRow(getView().getContext());
        head.setId(10);
        head.setBackgroundColor(Color.LTGRAY);
        head.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        ));

        player = new Button(getView().getContext());
        player.setId(20);
        player.setText("Player");
        player.setTextColor(view.getSolidColor());
        player.setPadding(5, 5, 5, 5);


        score = new Button(getView().getContext());
        score.setId(30);
        score.setText("Score");
        score.setTextColor(view.getSolidColor());
        score.setPadding(5, 5, 5, 5);

        date = new Button(getView().getContext());
        date.setId(40);
        date.setText("Player");
        date.setTextColor(view.getSolidColor());
        date.setPadding(5, 5, 5, 5);

        head.addView(player);
        head.addView(score);
        head.addView(date);

        scoreTab.addView(head, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.FILL_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
        ));

        String[][] myData = new String[3][3];
        myData[0][0] = "00";
        myData[0][1] = "01";
        myData[0][2] = "02";
        myData[1][0] = "10";
        myData[1][1] = "11";
        myData[1][2] = "12";
        myData[2][0] = "20";
        myData[2][1] = "21";
        myData[2][2] = "22";

        int cursor = 0;

        while (cursor <= 2) {
            myRow = new TableRow(getView().getContext());

            myRow.setId(100 + 1);
            myRow.setBackgroundColor(Color.LTGRAY);
            myRow.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            ));

            TextView playerl = new TextView(getView().getContext());
            playerl.setId(200+1);
            playerl.setText(myData[cursor][1]);
            playerl.setPadding(5, 5, 5, 5);

            TextView scorel = new TextView(getView().getContext());
            scorel.setId(300+2);
            scorel.setText(myData[cursor][2]);
            scorel.setPadding(5, 5, 5, 5);

            TextView datel = new TextView(getView().getContext());
            datel.setId(300+3);
            datel.setText(myData[cursor][3]);
            datel.setPadding(5, 5, 5, 5);

            myRow.addView(playerl);
            myRow.addView(scorel);
            myRow.addView(datel);

            scoreTab.addView(myRow, new TableRow.LayoutParams(
                    TableLayout.LayoutParams.FILL_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
            ));
            cursor ++;
        }
    }
}
