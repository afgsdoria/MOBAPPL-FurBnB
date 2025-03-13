package com.example.furbnb1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Booking extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.booking_page);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView bathandblow = findViewById(R.id.bathandblow);
        ImageView fullgrooming = findViewById(R.id.fullgrooming);
        ImageView dayboarding = findViewById(R.id.dayboarding);
        ImageView overnightstay = findViewById(R.id.overnightstay);

        ImageView imageHome = findViewById(R.id.image_home);
        ImageView imageAccount = findViewById(R.id.image_account);

        TextView textHome = findViewById(R.id.text_home);
        TextView textAccount = findViewById(R.id.text_account);

        bathandblow.setOnClickListener(v -> {
            Intent intent = new Intent(Booking.this, Booking_BathAndBlow.class);
            startActivity(intent);
        });

        fullgrooming.setOnClickListener(v -> {
            Intent intent = new Intent(Booking.this, Booking_FullGrooming.class);
            startActivity(intent);
        });

        dayboarding.setOnClickListener(v -> {
            Intent intent = new Intent(Booking.this, Booking_DayBoarding.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        });

        overnightstay.setOnClickListener(v -> {
            Intent intent = new Intent(Booking.this, Booking_OvernightStay.class);
            startActivity(intent);
        });

        View.OnClickListener bookNowClickListener = v -> {
            Intent intent = new Intent(Booking.this, HomePage.class);
            startActivity(intent);
        };

        View.OnClickListener accountClickListener = v -> {
            Intent intent = new Intent(Booking.this, Account.class);
            startActivity(intent);
        };

        imageHome.setOnClickListener(bookNowClickListener);
        textHome.setOnClickListener(bookNowClickListener);

        imageAccount.setOnClickListener(accountClickListener);
        textAccount.setOnClickListener(accountClickListener);

    }
}
