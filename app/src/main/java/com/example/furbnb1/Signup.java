package com.example.furbnb1;

import android.annotation.SuppressLint; //para mawala mga warning messages for error messages
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils; //to check if editText is empty
import android.text.method.HideReturnsTransformationMethod; // to view pw and cpw
import android.text.method.PasswordTransformationMethod; // to hide pw and cpw
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton; // para sa mata
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.furbnb1.Login;
import com.example.furbnb1.R;

import java.util.regex.Matcher; // to check if match yung pw and cpw
import java.util.regex.Pattern; // to check if match yung pw and cpw

public class Signup extends AppCompatActivity {

    private boolean isPasswordVisible = false; // to hide pw first and/or track if pw is hidden
    private boolean isConfirmPasswordVisible = false; //to hide cpw first and/or track if pw is hidden

    @SuppressLint("SetTextI18n") //para mawala mga warning messages for error messages
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        EditText nameInput = findViewById(R.id.signup_name);
        EditText emailInput = findViewById(R.id.signup_email);
        EditText passwordInput = findViewById(R.id.signup_password);
        EditText confirmPasswordInput = findViewById(R.id.signup_confirmpassword);
        ImageButton eyePassword = findViewById(R.id.ic_signup_pw);
        ImageButton eyeConfirmPassword = findViewById(R.id.ic_signup_cpw);
        Button signupButton = findViewById(R.id.signup_button);
        TextView cpwMessage = findViewById(R.id.cpw_message);
        TextView errorMessage = findViewById(R.id.signup_error_message);
        TextView logInText = findViewById(R.id.log_in);

        // to hide and unhide password
        eyePassword.setOnClickListener(v -> {
            if (isPasswordVisible) { // variable to hide pass
                passwordInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
                eyePassword.setImageResource(R.drawable.non_visible_pw); // para mag palit yung eye to another variant
            } else { // unhide
                passwordInput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                eyePassword.setImageResource(R.drawable.visible_pw); /// para mag palit yung eye to another variant
            }
            isPasswordVisible = !isPasswordVisible; //if true=visible and false=hidden; if true change to false and false change to true
            passwordInput.setSelection(passwordInput.getText().length()); // move cursor to the end

        });

        // to hide and unhide confirm password
        eyeConfirmPassword.setOnClickListener(v -> {
            if (isConfirmPasswordVisible) { // variable
                confirmPasswordInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
                eyeConfirmPassword.setImageResource(R.drawable.non_visible_pw); // para mag palit yung eye to another variant
            } else {
                confirmPasswordInput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                eyeConfirmPassword.setImageResource(R.drawable.visible_pw); // para mag palit yung eye to another variant
            }
            isConfirmPasswordVisible = !isConfirmPasswordVisible; // if true=visible and false=hidden; if true change to false and false change to true
            confirmPasswordInput.setSelection(confirmPasswordInput.getText().length()); // move cursor to the end
        });

        // once signup button is clicked:
        signupButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim(); // input converted to string
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            String confirmPassword = confirmPasswordInput.getText().toString().trim();

            // to require user to type their name
            if (TextUtils.isEmpty(name)) {
                errorMessage.setText("Please enter your name.");
                return;
            }

            // to require user to type their email
            if (TextUtils.isEmpty(email)) {
                errorMessage.setText("Enter your email.");
                return;
            }

            // to require user to type their password
            if (TextUtils.isEmpty(password)) {
                errorMessage.setText("Enter your password.");
                return;
            }

            // to require user to confirm their password
            if (TextUtils.isEmpty(confirmPassword)) {
                errorMessage.setText("Confirm your password.");
                return;
            }

            // to alidate email domain
            if (!isValidEmail(email)) {
                errorMessage.setText("Invalid email domain.");
                return;
            }

            // to validate password strength
            if (!isValidPassword(password)) {
                errorMessage.setText("Please use a strong password.");
                return;
            }

            // to check if pw and cw match
            if (!password.equals(confirmPassword)) {
                cpwMessage.setText("Passwords do not match."); // if do not match
                return;
            } else {
                cpwMessage.setText("Signing Up!"); // if matched then signup message
            }

            // if everything is valid no error message
            errorMessage.setText("");

            // no error -> proceed to home page
            Intent intent = new Intent(Signup.this, HomePage.class);
            startActivity(intent);
        });

        // Log-in text click listener
        logInText.setOnClickListener(v -> {
            Intent intent = new Intent(Signup.this, Login.class);
            startActivity(intent);
        });
    }

    // Email validation method
    private boolean isValidEmail(String email) { //method to check if email domain is valid
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && // check if input follows accepted domains
                (email.endsWith("@gmail.com") || email.endsWith("@yahoo.com") ||
                        email.endsWith("@outlook.com") || email.endsWith("@hotmail.com") ||
                        email.endsWith("@icloud.com") || email.endsWith("@aol.com") ||
                        email.endsWith("@protonmail.com")); //checks if input to accepted domains
    }

    // Password validation method
    private boolean isValidPassword(String password) { //method to check if pw format is valid
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!]).{8,}$"; //pw requirements
        Pattern pattern = Pattern.compile(passwordPattern); //compiles input to check pw
        Matcher matcher = pattern.matcher(password); // check if na meet yung pw requirements
        return matcher.matches(); // if input match sa pw requirements true else false
    }
}