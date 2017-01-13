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
    TextView s, s2, t, t2, m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreState = 0;
        turnScore = 0;
        scoreStateComp = 0;
        turnScoreComp = 0;
        s = (TextView) findViewById(R.id.textView2);
        s2 = (TextView)findViewById(R.id.textView4);
        t = (TextView) findViewById(R.id.textView5);
        t2 = (TextView) findViewById(R.id.textView6);
        m = (TextView) findViewById(R.id.textView7);

        Button reset = (Button) findViewById(R.id.button3);
        reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reset();
            }
        });
    }

    public void hold(View view) {
        holdHelper(0);
        this.delayedComputerTurn();
    }

    public void holdHelper(int player) {
        final Button b = (Button)findViewById(R.id.button);
        final Button b2 = (Button)findViewById(R.id.button2);

        if (player == 1) {
            m.setText("Computer holds");
            scoreStateComp += turnScore;
            s2.setText(String.valueOf(scoreStateComp));
            if (scoreStateComp >= 100) {
                m.setText("Computer won");
                reset();
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    b.setEnabled(true);
                    b2.setEnabled(true);

                }
            }, 2000);
        } else {
            b.setEnabled(false);
            b2.setEnabled(false);
            scoreState += turnScore;
            s.setText(String.valueOf(scoreState));
            if (scoreState >= 100) {
                m.setText("You won!");
                reset();
            }
        }

        turnScore = 0;
        t.setText("");
        t2.setText("");

    }

    public void roll(View view) {
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

    public void reset() {
        scoreState = 0;
        turnScore = 0;
        scoreStateComp = 0;
        turnScoreComp = 0;

        s.setText("0");
        s2.setText("0");
    }
    private void delayedComputerTurn() {
        m.setText("Computer Turn");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                computerTurn();
            }
        }, 2000);
    }

    private void computerRollsOne() {
        final Button b = (Button) findViewById(R.id.button);
        final Button b2 = (Button) findViewById(R.id.button2);
        m.setText("Computer rolled one");
        turnScore = 0;
        t.setText("");
        t2.setText("");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                b.setEnabled(true);
                b2.setEnabled(true);
            }
        }, 2000);
    }

    private void computerTurn() {
        Random rand = new Random();
        int choice = 0;
        int roll = this.rollHelper();
        if (roll == 1) { //computer rolled a 1
            this.computerRollsOne();
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
