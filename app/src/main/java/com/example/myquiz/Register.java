package com.example.myquiz;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    EditText Username, Email, Password, ConfirmPassword;

    Button RegisterBtn;

    FirebaseAuth MyAuthentication;

    DatabaseReference root;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(getApplicationContext());
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Username = findViewById(R.id.UserNameReg);
        Email = findViewById(R.id.EmailRegister);
        Password = findViewById(R.id.PasswordRegister);
        ConfirmPassword = findViewById(R.id.ConfirmPasswordRegister);
        RegisterBtn = findViewById(R.id.RegisterRegister);
        MyAuthentication=FirebaseAuth.getInstance();
        root = FirebaseDatabase.getInstance().getReference();
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = Username.getText().toString();
                String mail = Email.getText().toString();
                String pass = Password.getText().toString();
                String pass2 = ConfirmPassword.getText().toString();
                if (TextUtils.isEmpty(mail) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(pass2)) {
                    Toast.makeText(Register.this, "Please Fill All The Required Fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pass.length() < 6) {
                    Toast.makeText(Register.this, "Password Should Be Longer Than 6", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!pass.equals(pass2)) {
                    Toast.makeText(Register.this, "Passwords Doesn't Match", Toast.LENGTH_SHORT).show();
                    return;
                }

                signup(mail, pass, username);
            }
        });

    }
    public void signup(String mail, String pass, String username) {
        MyAuthentication.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Get the user ID
                            String userId = MyAuthentication.getCurrentUser().getUid();

                            // Save username to database
                            root.child("users").child(userId).child("username").setValue(username);

                            // Initialize the user's score to 0
                            root.child("users").child(userId).child("score").setValue(0);

                            Toast.makeText(Register.this, "Register Successful", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Register.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(Register.this, "Register Failed" + task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
