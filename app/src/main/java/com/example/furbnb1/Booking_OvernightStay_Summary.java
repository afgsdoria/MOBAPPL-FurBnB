package com.example.furbnb1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Booking_OvernightStay_Summary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_overnight_stay_summary);

        ImageView returnButton = findViewById(R.id.return_bttn);
        returnButton.setOnClickListener(v -> {
            Intent intent = new Intent(Booking_OvernightStay_Summary.this, Booking_OvernightStay.class);
            startActivity(intent);
        });

        //kinukuha yung info from bath and blow page
        int dogNum = getIntent().getIntExtra("dogNum", 1);
        String dogName = getIntent().getStringExtra("dogName");
        String dogBreed = getIntent().getStringExtra("dogBreed");
        String dogSize1 = getIntent().getStringExtra("dogSize1");
        String dogSize2 = getIntent().getStringExtra("dogSize2");
        String date = getIntent().getStringExtra("date");
        String checkInTime = getIntent().getStringExtra("checkInTime");
        String checkOutTime = getIntent().getStringExtra("checkOutTime");
        int totalPrice = getIntent().getIntExtra("totalPrice", 0);

        //view displayed
        ((TextView) findViewById(R.id.dogNum)).setText("Number of Dogs: " + dogNum);
        ((TextView) findViewById(R.id.dogName)).setText("Dog Name: " + dogName);
        ((TextView) findViewById(R.id.dogBreed)).setText("Breed: " + dogBreed);
        ((TextView) findViewById(R.id.dogSize1)).setText("Dog 1 Size: " + dogSize1);
        ((TextView) findViewById(R.id.dogSize2)).setText(dogNum == 2 ? "Dog 2 Size: " + dogSize2 : "");
        ((TextView) findViewById(R.id.selectedCheckInDate)).setText("Selected Date: " + date);
        ((TextView) findViewById(R.id.selectedCheckOutDate)).setText("Selected Date: " + date);
        ((TextView) findViewById(R.id.selectedCheckInTime)).setText("Check-in Time: " + checkInTime);
        ((TextView) findViewById(R.id.selectedCheckOutTime)).setText("Check-out Time: " + checkOutTime);
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

                    Intent intent = new Intent(Booking_OvernightStay_Summary.this, HomePage.class);
                    startActivity(intent);
                    finish();
                })
                .setCancelable(false)
                .show();
    }
}