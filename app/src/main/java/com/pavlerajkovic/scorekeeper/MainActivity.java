package com.pavlerajkovic.scorekeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int[] pointsNum;
    private TextView[] pointsText;

    private int serveNo = 1;
    private int servePlayer;

    Button servePlayerOne;
    Button servePlayerTwo;

    TextView commentary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pointsNum = new int[2];
        pointsText = new TextView[2];

        pointsText[0] = findViewById(R.id.score_playerOne);
        pointsText[1] = findViewById(R.id.score_playerTwo);

        commentary = findViewById(R.id.commentary);

        servePlayerOne = findViewById(R.id.serve_playerOne);
        servePlayerTwo = findViewById(R.id.serve_playerTwo);
    }

    public void startNewGame(View view) {
        pointsNum[0] = pointsNum[1] = 0;

        pointsText[0].setText("0");
        pointsText[1].setText("0");

        commentary.setText(" ");

        if(Math.random() < 0.5) {
            enableButton(servePlayerOne);
            disableButton(servePlayerTwo);
            servePlayer = 1;
        }
        else {
            enableButton(servePlayerTwo);
            disableButton(servePlayerOne);
            servePlayer = 2;
        }
    }

    public void enableButton(Button b) {
        b.setEnabled(true);
        b.setBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    public void disableButton(Button b) {
        b.setEnabled(false);
        b.setBackgroundColor(getResources().getColor(R.color.colorInactive));
    }

    public void changeServe() {
        if(servePlayer == 1) {
            disableButton(servePlayerOne);
            enableButton(servePlayerTwo);
            servePlayer = 2;
        } else {
            disableButton(servePlayerTwo);
            enableButton(servePlayerOne);
            servePlayer = 1;
        }
    }

    public void serve(View view) {
        if(view.getId() == R.id.serve_playerOne)
            playPoint(0, 1);
        else playPoint(1, 0);
    }

    public void playPoint(int server, int returner) {
        if(serveNo == 1) {
            double probability = Math.random();
            if(probability < 0.17) {
                updateScore(server, returner);
                makeComment("Ace!");
            } else if(probability < 0.62) {
                if(probability < 0.77) {
                    updateScore(server, returner);
                    makeComment("Point Won!");
                } else {
                    updateScore(returner, server);
                    makeComment("Point Lost!");
                }
            } else {
                serveNo++;
                makeComment("Out!");
            }
        } else {
            double probability = Math.random();
            if(probability < 0.33) {
                updateScore(returner, server);
                makeComment("Double Fault!");
            } else if(probability < 0.57) {
                updateScore(server, returner);
                makeComment("Point Won!");
            } else {
                updateScore(returner, server);
                makeComment("Point Lost!");
            }
            serveNo--;
        }
    }

    public void updateScore(int won, int lost) {
        if((pointsNum[won] == 3 && pointsNum[lost] < 3) ||
                (pointsNum[won] == 4 && pointsNum[lost] == 3)) {
            pointsNum[won] = 5;
            pointsNum[won] = pointsNum[lost] = 0;
            pointsText[won].setText("0");
            pointsText[lost].setText("0");
            Toast.makeText(this, "Game Player " + (won + 1), Toast.LENGTH_LONG).show();
            changeServe();
        }
        else if(pointsNum[won] == 3 && pointsNum[lost] == 4) {
            pointsNum[lost] = 3;
            displayScore(pointsNum[lost], pointsText[lost]);
        } else {
            pointsNum[won]++;
            displayScore(pointsNum[won], pointsText[won]);
        }
    }

    public void displayScore(int scoreNum, TextView scoreTxt) {
        if(scoreNum == 1)
            scoreTxt.setText("15");
        else if(scoreNum == 2)
            scoreTxt.setText("30");
        else if(scoreNum == 3)
            scoreTxt.setText("40");
        else if(scoreNum == 4)
            scoreTxt.setText("ADV");
    }

    public void makeComment(String text) {
        commentary.setText(text);
    }

}
