package com.example.furbnb1;

import android.annotation.SuppressLint; //para mawala mga warning messages for error messages
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils; //to check if editText is empty
import android.text.method.HideReturnsTransformationMethod; // to view pw and cpw
import android.text.method.PasswordTransformationMethod; // to hide pw and cpw
import android.util.Log; // to print error message
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton; // para sa mata
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    // predefined credetential since walang db
    private final String Email = "juandelacruz@gmail.com";
    private final String Password = "Rams@2024";

    private boolean isPasswordVisible = false;  // to hide pw first and/or track if pw is hidden

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        EditText emailInput = findViewById(R.id.login_email);
        EditText passwordInput = findViewById(R.id.login_password);
        ImageButton eyeIcon = findViewById(R.id.ic_login_pw);
        Button loginButton = findViewById(R.id.login_to_home_button);
        TextView loginMessage = findViewById(R.id.login_message);
        TextView signUpText = findViewById(R.id.sign_up);

        // to hide and unhide password
        eyeIcon.setOnClickListener(v -> {
            if (isPasswordVisible) { // variable to hide pass
                passwordInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
                eyeIcon.setImageResource(R.drawable.non_visible_pw); // para mag palit yung eye to another variant
            } else { // unhide
                passwordInput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                eyeIcon.setImageResource(R.drawable.visible_pw); // para mag palit yung eye to another variant
            }
            isPasswordVisible = !isPasswordVisible; //if true=visible and false=hidden; if true change to false and false change to true
            passwordInput.setSelection(passwordInput.getText().length()); // move cursor to the end
        });


        // once login button is clicked:
        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim(); // input converted to string
            String password = passwordInput.getText().toString().trim();

            Log.d("Login", "Email: " + email + ", Password: " + password); // for debugging and printing error message from login

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) { //checks if the input is empty
                loginMessage.setText("Please fill in both email and password."); //if no input
            } else if (!isValidEmail(email)) {
                loginMessage.setText("Invalid email or password."); // if email did not match to pattern
            } else if (email.equals(Email) && password.equals(Password)) { //if match both=login
                Log.d("Login", "Logging in");
                Intent intent = new Intent(Login.this, HomePage.class);
                startActivity(intent);
            } else {
                loginMessage.setText("Invalid email or password.");
            }
        });

        // to redirect to signup page
        signUpText.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Signup.class);
            startActivity(intent);
        });
    }

    // Method to validate email
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}