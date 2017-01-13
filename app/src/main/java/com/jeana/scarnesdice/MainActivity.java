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

    long startTime = 0;

    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            timerHandler.postDelayed(this, 500);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreState = 0;
        turnScore = 0;
        scoreStateComp = 0;
        turnScoreComp = 0;

        Button hold = (Button) findViewById(R.id.button2);
        hold.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                holdHelper(0);
                computerTurn();
            }
        });



    }

    public void holdHelper(int player) {
        TextView s = (TextView)findViewById(R.id.textView2);
        TextView t = (TextView)findViewById(R.id.textView5);
        TextView t2 = (TextView)findViewById(R.id.textView6);

        if (player == 1) {
            s = (TextView)findViewById(R.id.textView4);
            scoreStateComp += turnScore;
            s.setText(String.valueOf(scoreStateComp));
        } else {
            scoreState += turnScore;
            s.setText(String.valueOf(scoreState));
        }

        turnScore = 0;
        t.setText("");
        t2.setText("");

    }

    public void roll(View view) {
        rollHelper(0);
    }

    private int rollHelper(int player) {
        ImageView image = (ImageView) findViewById(R.id.imageView1);
        Random rand = new Random();
        int roll = rand.nextInt(6) + 1;
        System.out.println("roll: " + roll);
        String url = "drawable/" + "dice" + roll;
        int imageKey = getResources().getIdentifier(url, "drawable", getPackageName());
        image.setImageResource(imageKey);

        TextView s = (TextView) findViewById(R.id.textView2);
        TextView t = (TextView) findViewById(R.id.textView5);
        TextView t2 = (TextView) findViewById(R.id.textView6); //turn score value
        if (player == 1) {
            s = (TextView) findViewById(R.id.textView4);
        }
        if (roll == 1) {
            turnScore = 0;
            t.setText("");
            t2.setText("");
            if (player == 0) {
                scoreState = 0;
                s.setText(String.valueOf(scoreState));
                computerTurn();
            } else {
                scoreStateComp = 0;
                s.setText(String.valueOf(scoreStateComp));
                return 0;
            }

        } else {
            turnScore += roll;
            t.setText("Turn Score:");
            t2.setText(String.valueOf(turnScore));
        }

        return 1;
    }

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

    private void computerTurn() {
        Button b = (Button)findViewById(R.id.button);
        Button b2 = (Button)findViewById(R.id.button2);

        b.setEnabled(false);
        b2.setEnabled(false);

        Random rand = new Random();
        int choice = 0;
        while (choice == 0) {
            if (rollHelper(1) == 0) {
                break;
            }
            choice = rand.nextInt(2);
            startTime = System.currentTimeMillis();
            timerHandler.postDelayed(timerRunnable, 5000);

        }

        holdHelper(1);
        b.setEnabled(true);
        b2.setEnabled(true);
    }


}
