package com.example.myquiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Quiz1 extends AppCompatActivity {

    Button Next;
    RadioGroup rg;
    RadioButton rb;
    TextView tvTimer;

    String Answer = "Vincent van Gogh";

    int score;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz1);

        Next = findViewById(R.id.NextBtn);
        rg = findViewById(R.id.rg);
        tvTimer = findViewById(R.id.tvTimer);


        startTimer();

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cancel the timer when the user clicks Next
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                if (rg.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Please Choose an Answer", Toast.LENGTH_SHORT).show();

                } else {
                    rb = findViewById(rg.getCheckedRadioButtonId());
                    if (rb.getText().toString().equals(Answer)) {
                        score += 1;
                    }
                    proceedToNextPageWithScore(score);
                }
            }
        });
    }

    // Method to start the countdown timer
    private void startTimer() {
        countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsLeft = millisUntilFinished / 1000;
                tvTimer.setText("Time left: " + secondsLeft + " seconds");
            }

            @Override
            public void onFinish() {
                // If timer finishes, proceed to the next question with score 0
                proceedToNextPageWithScore(0);
            }
        }.start();
    }

    // Method to proceed to the next question with a given score
    private void proceedToNextPageWithScore(int score) {
        // Your code for proceeding to the next page
        Intent intent = new Intent(Quiz1.this, Quiz2.class);
        intent.putExtra("score", score);
        startActivity(intent);
        finish();
    }
}
