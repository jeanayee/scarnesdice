package com.jeana.scarnesdice;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.ImageView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static int scoreState;
    private static int turnScore;
    private static int scoreStateComp;
    private static int turnScoreComp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreState = 0;
        turnScore = 0;
        scoreStateComp = 0;
        turnScoreComp = 0;
    }

    public void hold(View view) {
        holdHelper(0);
        this.delayedComputerTurn();
    }

    public void holdHelper(int player) {
        TextView s = (TextView)findViewById(R.id.textView2);
        TextView t = (TextView)findViewById(R.id.textView5);
        TextView t2 = (TextView)findViewById(R.id.textView6);
        Button b = (Button)findViewById(R.id.button);
        Button b2 = (Button)findViewById(R.id.button2);

        if (player == 1) {
            b.setEnabled(true);
            b2.setEnabled(true);
            s = (TextView)findViewById(R.id.textView4);
            scoreStateComp += turnScore;
            s.setText(String.valueOf(scoreStateComp));
        } else {
            b.setEnabled(false);
            b2.setEnabled(false);
            scoreState += turnScore;
            s.setText(String.valueOf(scoreState));
        }

        turnScore = 0;
        t.setText("");
        t2.setText("");

    }

    public void roll(View view) {
        TextView s = (TextView) findViewById(R.id.textView2);
        TextView t = (TextView) findViewById(R.id.textView5);
        TextView t2 = (TextView) findViewById(R.id.textView6); //turn score value
        int roll = this.rollHelper();
        if (roll == 1) {
            Button b = (Button) findViewById(R.id.button);
            Button b2 = (Button) findViewById(R.id.button2);
            turnScore = 0;
            t.setText("");
            t2.setText("");
            b.setEnabled(false);
            b2.setEnabled(false);
            this.delayedComputerTurn();
        } else {
            turnScore += roll;
            t.setText("Turn Score:");
            t2.setText(String.valueOf(turnScore));
        }
    }

    private int rollHelper() {
        ImageView image = (ImageView) findViewById(R.id.imageView1);
        Random rand = new Random();
        int roll = rand.nextInt(6) + 1;
        String url = "drawable/" + "dice" + roll;
        int imageKey = getResources().getIdentifier(url, "drawable", getPackageName());
        image.setImageResource(imageKey);
        return roll;
    }

/*    private int rollHelper(int player) {
        TextView s = (TextView) findViewById(R.id.textView2);
        TextView t = (TextView) findViewById(R.id.textView5);
        TextView t2 = (TextView) findViewById(R.id.textView6); //turn score value
        if (player == 1) {
            s = (TextView) findViewById(R.id.textView4);
        }
        if (roll == 1) {
            Button b = (Button)findViewById(R.id.button);
            Button b2 = (Button)findViewById(R.id.button2);
            turnScore = 0;
            t.setText("");
            t2.setText("");
            if (player == 0) {
                b.setEnabled(false);
                b2.setEnabled(false);

                this.delayedComputerTurn();
            } else {
                b.setEnabled(true);
                b2.setEnabled(true);
                return 0;
            }

        } else {
            turnScore += roll;
            t.setText("Turn Score:");
            t2.setText(String.valueOf(turnScore));
        }

        return 1;
    }*/

    public void reset (View view) {
        scoreState = 0;
        turnScore = 0;
        scoreStateComp = 0;
        turnScoreComp = 0;

        TextView s = (TextView)findViewById(R.id.textView2);
        TextView sComp = (TextView)findViewById(R.id.textView4);
        s.setText("0");
        sComp.setText("0");
    }
    private void delayedComputerTurn() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                computerTurn();
            }
        }, 2000);
    }

    private void computerTurn() {
        Random rand = new Random();
        TextView t = (TextView) findViewById(R.id.textView5);
        TextView t2 = (TextView) findViewById(R.id.textView6); //turn score value
        int choice = 0;
        int roll = this.rollHelper();
        if (roll == 1) { //computer rolled a 1
            holdHelper(1);
        } else {
            turnScore += roll;
            t.setText("Turn Score:");
            t2.setText(String.valueOf(turnScore));
            choice = rand.nextInt(2);
            if (choice == 1) {
                this.delayedComputerTurn();
            } else {
                holdHelper(1);
            }
        }
    }


}
