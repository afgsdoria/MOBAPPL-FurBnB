package com.example.furbnb1;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Account extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);

        ImageView imageHome = findViewById(R.id.image_home);
        ImageView imageBookNow = findViewById(R.id.image_book_now);

        TextView textHome = findViewById(R.id.text_home);
        TextView textBookNow = findViewById(R.id.text_book_now);

        View.OnClickListener homeClickListener = v -> {
            Intent intent = new Intent(Account.this, HomePage.class);
            startActivity(intent);
        };

        View.OnClickListener bookNowClickListener = v -> {
            Intent intent = new Intent(Account.this, Booking.class);
            startActivity(intent);
        };

        imageHome.setOnClickListener(homeClickListener);
        textHome.setOnClickListener(homeClickListener);

        imageBookNow.setOnClickListener(bookNowClickListener);
        textBookNow.setOnClickListener(bookNowClickListener);

        TableRow helpCenterRow = findViewById(R.id.help_center_row);
        TableRow privacyPolicyRow = findViewById(R.id.privacy_policy_row);
        TableRow termsAndConditionsRow = findViewById(R.id.terms_and_conditions_row);

        helpCenterRow.setOnClickListener(v -> {
            Intent intent = new Intent(Account.this, FAQ.class);
            startActivity(intent);
        });

        privacyPolicyRow.setOnClickListener(v -> {
            Intent intent = new Intent(Account.this, PrivacyPolicy.class);
            startActivity(intent);
        });

        termsAndConditionsRow.setOnClickListener(v -> {
            Intent intent = new Intent(Account.this, TermsAndCondition.class);
            startActivity(intent);
        });

        CardView logoutCard = findViewById(R.id.cardView_logout);
        logoutCard.setOnClickListener(v -> showLogoutDialog());

        TextView textView12 = findViewById(R.id.textView12);
        textView12.setOnClickListener(v -> {
            Intent intent = new Intent(Account.this, AccSettings.class);
            startActivity(intent);
        });
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Sign Out")
                .setMessage("Are you sure you want to sign out?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    Intent intent = new Intent(Account.this, Welcome.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish(); // close the current activity
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss(); // close the dialog
                })
                .show();

        TextView signUpText = findViewById(R.id.textView12);

        // to redirect to signup page
        signUpText.setOnClickListener(v -> {
            Intent intent = new Intent(Account.this, AccSettings.class);
            startActivity(intent);
        });
    }
}