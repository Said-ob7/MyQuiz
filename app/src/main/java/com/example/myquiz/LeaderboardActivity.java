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

        // Initialize Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("users");

        // Initialize UI components
        listView = findViewById(R.id.listView);
        leaderboardList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, leaderboardList);
        listView.setAdapter(adapter);

        // Fetch and display leaderboard data
        displayLeaderboard();
    }

    private void displayLeaderboard() {
        // Add ValueEventListener to fetch data from database
        usersRef.orderByChild("score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear the leaderboard list before adding new data
                leaderboardList.clear();

                // Iterate through each child node (user) in the "users" node
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    // Get the username and score of each user
                    String username = userSnapshot.child("username").getValue(String.class);
                    Integer scoreValue = userSnapshot.child("score").getValue(Integer.class);

                    // Check if the score value exists
                    if (scoreValue != null) {
                        int score = scoreValue.intValue();
                        // Add the username and score to the leaderboard list
                        leaderboardList.add(username + ": " + score);
                    }
                }

                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
            }
        });
    }
}
