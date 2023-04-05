package com.greencode27.tiktaktoe;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button bt;
    boolean gameActive = true;
    boolean winnerDisplayed = false;
    int playerOneScoreCount = 0;
    int playerTwoScoreCount = 0;

    int noOfRoundsPlayed = 0;
    // Player Representation
    // 0 - X
    // 1 - O

    int activePlayer = 0;
    int[] gameState = {2,2,2,2,2,2,2,2,2};

    // State Means
    // 0 - X
    // 1 - O
    // 2 - Null

    int[][] winPositions = {{0,1,2}, {3,4,5}, {6,7,8},
            {0,3,6}, {1,4,7}, {2,5,8},
            {0,4,8}, {2,4,6}};
    public void playerTap(View view){
        TextView playerOneScore = (TextView)findViewById(R.id.ScoreButton1);
        TextView playerTwoScore = (TextView)findViewById(R.id.ScoreButton2);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.click);
        mp.start();
        ImageView img = (ImageView) view;
        int tappedImage = Integer.parseInt(img.getTag().toString());
        if(!gameActive){
            gameReset(view);
        }
        if(winnerDisplayed)
        {
            gameReset(view);
        }
        if(gameState[tappedImage] == 2) {
            gameState[tappedImage] = activePlayer;
            img.setTranslationY(-1000f);
            if (activePlayer == 0) {
                img.setImageResource(R.drawable.x);
                activePlayer = 1;

                TextView status = findViewById(R.id.status);
                status.setText("O's Turn - Tap to play");

            } else {
                img.setImageResource(R.drawable.o);
                activePlayer = 0;

                TextView status = findViewById(R.id.status);
                status.setText("X's Turn - Tap to play");

            }
            img.animate().translationYBy(1000f).setDuration(300);
        }
        // Check if any player has won
        for(int[] winPosition: winPositions){
            if(gameState[winPosition[0]] == gameState[winPosition[1]] &&
                    gameState[winPosition[1]] == gameState[winPosition[2]] &&
                    gameState[winPosition[0]]!=2){
                // Somebody has won! - Find out who!
                String winnerStr;
                gameActive = false;
                if(gameState[winPosition[0]] == 0){
                    final MediaPlayer winner_sound = MediaPlayer.create(this, R.raw.winner_sound);
                    winnerStr = "X has won";
                    noOfRoundsPlayed++;
                    playerOneScoreCount++;
                    if(playerTwoScoreCount > 0) {
                        playerTwoScoreCount--;
                    }
                    playerOneScore.setText("Player1: " + Integer.toString(playerOneScoreCount));
                    playerTwoScore.setText("Player2: " + Integer.toString(playerTwoScoreCount));
                    if(noOfRoundsPlayed == 3) {
                        winnerDisplayed=true;
                        winner_sound.start();
                        View linerView = (View) findViewById(R.id.linearLayout);
                        View imageView = (View) findViewById(R.id.imageView);
                        View winner_view = (View) findViewById(R.id.gifImageView);
                        linerView.setVisibility(View.GONE);
                        imageView.setVisibility(View.GONE);
                        winner_view.setVisibility(View.VISIBLE);
                    }
                    View score_view1 = (View) findViewById(R.id.ScoreButton1);
                    View score_view2 = (View) findViewById(R.id.ScoreButton2);
                    score_view1.setVisibility(View.VISIBLE);
                    score_view2.setVisibility(View.VISIBLE);
                }
                else{
                    final MediaPlayer winner_sound = MediaPlayer.create(this, R.raw.winner_sound);
                    winnerStr = "O has won";
                    noOfRoundsPlayed++;
                    playerTwoScoreCount++;
                    if(playerOneScoreCount > 0) {
                        playerOneScoreCount--;
                    }
                    playerOneScore.setText("Player1: "+Integer.toString(playerOneScoreCount));
                    playerTwoScore.setText("Player2: "+Integer.toString(playerTwoScoreCount));
                    if(noOfRoundsPlayed == 3) {
                        winnerDisplayed=true;
                        winner_sound.start();
                        View linerView = (View) findViewById(R.id.linearLayout);
                        View imageView = (View) findViewById(R.id.imageView);
                        View winner_view = (View) findViewById(R.id.gifImageView);
                        linerView.setVisibility(View.GONE);
                        imageView.setVisibility(View.GONE);
                        winner_view.setVisibility(View.VISIBLE);
                    }
                    View score_view1 = (View) findViewById(R.id.ScoreButton1);
                    View score_view2 = (View) findViewById(R.id.ScoreButton2);
                    score_view1.setVisibility(View.VISIBLE);
                    score_view2.setVisibility(View.VISIBLE);
                }
                // Update the status bar for winner announcement
                TextView status = findViewById(R.id.status);
                status.setText(winnerStr);
            }
        }
    }

    public void gameReset(View view) {
        boolean winnerDisplayed = false;
        int playerOneScoreCount = 0;
        int playerTwoScoreCount = 0;
        int noOfRoundsPlayed = 0;
        gameActive = true;
        activePlayer = 0;
        for(int i=0; i<gameState.length; i++){
            gameState[i] = 2;
        }
        ((ImageView)findViewById(R.id.imageView0)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView1)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView2)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView3)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView4)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView5)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView6)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView7)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView8)).setImageResource(0);

        TextView status = findViewById(R.id.status);
        status.setText("X's Turn - Tap to play");

    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        bt = (Button)findViewById(R.id.custom_button);
        final MediaPlayer mp1 = MediaPlayer.create(this, R.raw.reset_sound);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp1.start();
                TextView playerOneScore = (TextView)findViewById(R.id.ScoreButton1);
                TextView playerTwoScore = (TextView)findViewById(R.id.ScoreButton2);
                playerOneScore.setText("Player1: "+Integer.toString(0));
                playerTwoScore.setText("Player2: "+Integer.toString(0));
                View linerView = (View) findViewById(R.id.linearLayout);
                View imageView = (View) findViewById(R.id.imageView);
                View winner_view=(View)findViewById(R.id.gifImageView);
                linerView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);
                winner_view.setVisibility(View.GONE);
                gameReset(view);
            }
        });
    }
}
