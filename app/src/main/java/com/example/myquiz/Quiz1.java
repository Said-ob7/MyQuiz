package com.example.myquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Quiz1 extends AppCompatActivity {

    Button Next;
    RadioGroup rg;
    RadioButton rb;

    String Answer = "Vincent van Gogh";

    int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Next = findViewById(R.id.NextBtn);
        rg = findViewById(R.id.rg);

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rg.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(Quiz1.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                }
                else{
                    rb=findViewById(rg.getCheckedRadioButtonId());
                    if(rb.getText().toString().equals(Answer)) score+=1;
                    Intent i2=new Intent(Quiz1.this, Quiz2.class);
                    i2.putExtra("score",score);
                    startActivity(i2);
                    finish();

                }
            }
        });
    }
}
