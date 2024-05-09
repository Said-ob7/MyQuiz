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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Quiz2 extends AppCompatActivity {

    Button Next;
    RadioGroup rg;
    RadioButton rb;

    String Answer = "Mars";

    int score;

    TextView tvTimer;
    CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Next = findViewById(R.id.NextBtn);
        rg = findViewById(R.id.rg);
        Intent i2 = getIntent();
        score = i2.getIntExtra("score",0);
        tvTimer = findViewById(R.id.tvTimer);

        startTimer();
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                if(rg.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(getApplicationContext(), "Please Choose an Answer", Toast.LENGTH_SHORT).show();
                }
                else{
                    rb=findViewById(rg.getCheckedRadioButtonId());
                    if(rb.getText().toString().equals(Answer)) score+=1;
                    proceedToNextPageWithScore(score);
                }
            }
        });

    }
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
                proceedToNextPageWithScore(score);
            }
        }.start();
    }
    private void proceedToNextPageWithScore(int score) {
        Intent i1=new Intent(getApplicationContext(), Quiz3.class);
        i1.putExtra("score",score);
        startActivity(i1);
        finish();

    }
}
