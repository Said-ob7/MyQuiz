package com.example.myquiz;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> leaderboardList;
    private ArrayAdapter<String> adapter;

    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("users");


        listView = findViewById(R.id.listView);
        leaderboardList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, leaderboardList);
        listView.setAdapter(adapter);


        displayLeaderboard();
    }

    private void displayLeaderboard() {

        usersRef.orderByChild("score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear the leaderboard list before adding new data
                leaderboardList.clear();


                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                    String username = userSnapshot.child("username").getValue(String.class);
                    Integer scoreValue = userSnapshot.child("score").getValue(Integer.class);

                    // Check if the score value exists
                    if (scoreValue != null) {
                        int score = scoreValue.intValue();
                        int percentage = (score * 100)/5;

                        leaderboardList.add(username + ": " + percentage + "%");
                    }
                }


                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
            }
        });
    }
}
