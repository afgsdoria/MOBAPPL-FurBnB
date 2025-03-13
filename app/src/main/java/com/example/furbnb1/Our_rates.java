package com.example.furbnb1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Our_rates extends Activity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.our_rates);

        ImageView imageButton3 = findViewById(R.id.imageButton3);
        imageButton3.setImageResource(R.drawable.return_bttn);

        TextView textView7 = findViewById(R.id.textView7);
        textView7.setText("Our Rates");

        ImageView imageView4 = findViewById(R.id.imageView4);
        imageView4.setImageResource(R.drawable.room_boarding_rates);

        ImageView imageView5 = findViewById(R.id.imageView5);
        imageView5.setImageResource(R.drawable.full_grooming_rates);

        ImageView imageView6 = findViewById(R.id.imageView6);
        imageView6.setImageResource(R.drawable.bath_blow_rates);

        imageButton3.setOnClickListener(v -> {
            Intent intent = new Intent(Our_rates.this, HomePage.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_right, R.anim.slide_right);
        });
    }
}