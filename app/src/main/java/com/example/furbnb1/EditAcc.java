package com.example.furbnb1;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EditAcc extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private TextView textViewError;
    private ImageView imageViewShowPassword;
    private ImageView imageViewShowConfirmPassword;
    private boolean isPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_acc);

        editTextName = findViewById(R.id.new_name);
        editTextEmail = findViewById(R.id.new_email);
        editTextPassword = findViewById(R.id.new_pw);
        editTextConfirmPassword = findViewById(R.id.conf_new_pw);
        textViewError = findViewById(R.id.pw_error);
        imageViewShowPassword = findViewById(R.id.ic_new_pw);
        imageViewShowConfirmPassword = findViewById(R.id.ic_conf_pw);

        ImageView imageButton3 = findViewById(R.id.imageButton3);
        imageButton3.setImageResource(R.drawable.return_bttn);

        imageButton3.setOnClickListener(v -> {
            Intent intent = new Intent(EditAcc.this, AccSettings.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_right, R.anim.slide_right);
        });

        // confirm button on edit account details
        Button confirmButton = findViewById(R.id.new_conf_button);
        confirmButton.setOnClickListener(v -> checkAndSaveChanges());

        // click listener for new password eye icon
        imageViewShowPassword.setOnClickListener(v -> togglePasswordVisibility(editTextPassword, imageViewShowPassword, true));

        // click listener for confirm password eye icon
        imageViewShowConfirmPassword.setOnClickListener(v -> togglePasswordVisibility(editTextConfirmPassword, imageViewShowConfirmPassword, false));
    }

    private void togglePasswordVisibility(EditText editText, ImageView imageView, boolean isPasswordField) {
        if (isPasswordField) {
            if (isPasswordVisible) {
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                imageView.setImageResource(R.drawable.non_visible_pw);
            } else {
                editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                imageView.setImageResource(R.drawable.visible_pw);
            }
            isPasswordVisible = !isPasswordVisible;
        } else {
            if (isConfirmPasswordVisible) {
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                imageView.setImageResource(R.drawable.non_visible_pw);
            } else {
                editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                imageView.setImageResource(R.drawable.visible_pw);
            }
            isConfirmPasswordVisible = !isConfirmPasswordVisible;
        }
        editText.setSelection(editText.length());
    }

    // collect info na nilagay ni user
    private void checkAndSaveChanges() {
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();

        // reset error message; hides prev error messages tas isa lang kita
        textViewError.setVisibility(View.GONE);

        // check if valid email format IF may input si user
        if (!email.isEmpty() && !isValidEmail(email)) {
            textViewError.setText("Invalid email format");
            textViewError.setVisibility(View.VISIBLE);
            return;
        }

        // password checking IF may input si user
        if (!password.isEmpty() || !confirmPassword.isEmpty()) {

            // checks if may laman yung password tapos walang laman yung confirm password
            if (password.isEmpty() || confirmPassword.isEmpty()) {
                textViewError.setText("Please fill in both password fields");
                textViewError.setVisibility(View.VISIBLE);
                return;
            }

            // check if passwords match
            if (!password.equals(confirmPassword)) {
                textViewError.setText("Passwords do not match");
                textViewError.setVisibility(View.VISIBLE);
                return;
            }

            // checks password length. error message visible if less than 8
            if (password.length() < 8) {
                textViewError.setText("Password must contain more than 8 characters");
                textViewError.setVisibility(View.VISIBLE);
                return;
            }
        }

        // proceed to show dialog for saving changes
        showSaveDialog();
    }

    private void showSaveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save changes");
        builder.setMessage("Are you sure you want to save changes?");

        builder.setPositiveButton("Save changes", (dialog, which) -> {
            Intent intent = new Intent(EditAcc.this, AccSettings.class);
            startActivity(intent);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // checks email format
    private boolean isValidEmail(String email) {
        // uses emailPattern that defines kung ano yung valid email format
        // any of: letters a-z in caps and non-caps, numbers, ., underscore
        // required to have @ and expects a dot followed by lowercase a-z (.com)
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
}