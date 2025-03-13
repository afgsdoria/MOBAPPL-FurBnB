package com.example.furbnb1;

import android.content.Intent; //to start new activity
import android.os.Bundle; //to pass data between activities
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity; //for compatibility to old versions

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_main);

        Button loginButton = findViewById(R.id.login_button);
        TextView signUp = findViewById(R.id.sign_up);

        // on click redirect to login page
        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(Welcome.this, Login.class);
            startActivity(intent);
        });

        // on click redirect to sign up page
        signUp.setOnClickListener(v -> {
            Intent intent = new Intent(Welcome.this, Signup.class);
            startActivity(intent);
        });
    }
}