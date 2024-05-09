package com.example.myquiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class Score extends AppCompatActivity {

    ProgressBar Pb;

    TextView Tvscore;

    int score;

    Button btnLogout;
    Button TryAgain;

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
        btnLogout = findViewById(R.id.btnLogout);
        TryAgain = findViewById(R.id.btnTryAgain);

        int percentageScore = (score * 100) / 5;

        Pb.setMax(100);

        Pb.setProgress(percentageScore);

        Tvscore.setText(percentageScore + "%");
        Pb.setRotation(0);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logout user
                FirebaseAuth.getInstance().signOut();
                // Navigate back to login or any other appropriate activity
                startActivity(new Intent(Score.this, MainActivity.class));
                finish();
            }
        });

        TryAgain.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i1=new Intent(getApplicationContext(), Quiz1.class);
                startActivity(i1);
                finish();
            }
        });
    }
}
