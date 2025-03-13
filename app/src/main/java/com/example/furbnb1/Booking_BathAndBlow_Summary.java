package com.example.furbnb1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Booking_BathAndBlow_Summary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_bath_and_blow_summary);

        ImageView returnButton = findViewById(R.id.return_bttn);
        returnButton.setOnClickListener(v -> {
            Intent intent = new Intent(Booking_BathAndBlow_Summary.this, Booking_BathAndBlow.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_right, R.anim.slide_right);
        });

        //kinukuha yung info from bath and blow page
        int dogNum = getIntent().getIntExtra("dogNum", 1);
        String dogName = getIntent().getStringExtra("dogName");
        String dogBreed = getIntent().getStringExtra("dogBreed");
        String dogSize1 = getIntent().getStringExtra("dogSize1");
        String dogSize2 = getIntent().getStringExtra("dogSize2");
        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        int totalPrice = getIntent().getIntExtra("totalPrice", 0);

        //view displayed
        ((TextView) findViewById(R.id.dogNum)).setText("Number of Dogs: " + dogNum);
        ((TextView) findViewById(R.id.dogName)).setText("Dog Name: " + dogName);
        ((TextView) findViewById(R.id.dogBreed)).setText("Breed: " + dogBreed);
        ((TextView) findViewById(R.id.dogSize1)).setText("Dog 1 Size: " + dogSize1);
        ((TextView) findViewById(R.id.dogSize2)).setText(dogNum == 2 ? "Dog 2 Size: " + dogSize2 : "");
        ((TextView) findViewById(R.id.selectedDate)).setText("Selected Date: " + date);
        ((TextView) findViewById(R.id.selectedTime)).setText("Selected Time: " + time);
        ((TextView) findViewById(R.id.totalPrice)).setText("Total: ₱" + totalPrice);

        Button confirmButton = findViewById(R.id.send_btn);
        confirmButton.setOnClickListener(v -> showConfirmationDialog());
    }

    // confirmation of booking alert dialog
    private void showConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Appointment Confirmation")
                .setMessage("Your appointment request has been sent!")
                .setPositiveButton("OK", (dialog, which) -> {

                    Intent intent = new Intent(Booking_BathAndBlow_Summary.this, HomePage.class);
                    startActivity(intent);
                    finish();
                })
                .setCancelable(false)
                .show();
    }
}
