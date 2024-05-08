package com.example.myquiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Score extends AppCompatActivity {

    ProgressBar Pb;

    TextView Tvscore;

    int score;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent i2 = getIntent();
        score = i2.getIntExtra("score",0);

        Pb = findViewById(R.id.Progressbar);
        Tvscore = findViewById(R.id.tvScore);

        int percentageScore = (score * 100) / 5;

        Pb.setMax(100);

        Pb.setProgress(percentageScore);

        Tvscore.setText(percentageScore + "%");
        Pb.setRotation(0);
    }
}
