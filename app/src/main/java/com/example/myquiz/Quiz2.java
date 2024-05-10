package com.example.myquiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Quiz2 extends AppCompatActivity {

    RadioGroup rg;
    RadioButton rb;
    TextView tvTimer, question;
    Button Next;

    int score;
    CountDownTimer countDownTimer;
    DatabaseReference databaseReference;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz2);

        rg = findViewById(R.id.rg);
        tvTimer = findViewById(R.id.tvTimer);
        question = findViewById(R.id.question);
        imageView = findViewById(R.id.imageView);
        Next =findViewById(R.id.NextBtn);
        Intent i2 = getIntent();
        score = i2.getIntExtra("score",0);

        // Initialize Firebase Realtime Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Questions").child("1");

        startTimer();
        loadQuestion(); // Load the first question
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                if (rg.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Please Choose an Answer", Toast.LENGTH_SHORT).show();
                } else {
                    rb = findViewById(rg.getCheckedRadioButtonId());
                    DatabaseReference questionRef = FirebaseDatabase.getInstance().getReference()
                            .child("Questions")
                            .child("1")
                            .child("Answer");

                    questionRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String correctAnswer = dataSnapshot.getValue(String.class);
                                String chosenAnswer = rb.getText().toString().trim(); // Trim to remove any leading or trailing spaces
                                if (chosenAnswer.equals(correctAnswer)) {
                                    score += 1;
                                    Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Incorrect!", Toast.LENGTH_SHORT).show();
                                }
                                proceedToNextPageWithScore(score);
                            } else {
                                // Handle if answer doesn't exist in the database
                                Toast.makeText(getApplicationContext(), "Answer not found in database", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle error
                            Toast.makeText(getApplicationContext(), "Error fetching answer: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
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

                proceedToNextPageWithScore(0);
            }
        }.start();
    }

    // Method to fetch question from Firebase Realtime Database
    // Method to fetch question from Firebase Realtime Database
    private void loadQuestion() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String questionText = dataSnapshot.child("Question").getValue(String.class);
                    String option1 = dataSnapshot.child("Option1").getValue(String.class);
                    String option2 = dataSnapshot.child("Option2").getValue(String.class);
                    String option3 = dataSnapshot.child("Option3").getValue(String.class);
                    String imagePath = dataSnapshot.child("imagePath").getValue(String.class);

                    // Update UI with fetched question data
                    question.setText(questionText);
                    ((RadioButton) rg.getChildAt(0)).setText(option1);
                    ((RadioButton) rg.getChildAt(1)).setText(option2);
                    ((RadioButton) rg.getChildAt(2)).setText(option3);

                    // Load image into CircleImageView using the imagePath
                    int resID = getResources().getIdentifier(imagePath, "drawable", getPackageName());
                    imageView.setImageResource(resID);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }
    private void proceedToNextPageWithScore(int score) {
        // Your code for proceeding to the next page
        Intent intent = new Intent(Quiz2.this, Quiz3.class);
        intent.putExtra("score", score);
        startActivity(intent);
        finish();
    }

}
