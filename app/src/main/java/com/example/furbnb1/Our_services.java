package com.example.furbnb1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class Our_services extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.our_services);

        ImageView imageButton2 = findViewById(R.id.imageButton2);
        imageButton2.setImageResource(R.drawable.return_bttn);

        ImageView imageView2 = findViewById(R.id.imageView2);
        imageView2.setImageResource(R.drawable.hotel_services_img);

        ImageView imageView3 = findViewById(R.id.imageView3);
        imageView3.setImageResource(R.drawable.grooming_services_img);

        imageButton2.setOnClickListener(v -> {
            Intent intent = new Intent(Our_services.this, HomePage.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_right, R.anim.slide_right);
        });
    }
}