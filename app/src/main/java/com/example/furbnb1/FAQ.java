package com.example.furbnb1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class FAQ extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq);

        ImageView imageButton3 = findViewById(R.id.imageButton3);
        imageButton3.setImageResource(R.drawable.return_bttn);

        imageButton3.setOnClickListener(v -> {
            Intent intent = new Intent(FAQ.this, Account.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_right, R.anim.slide_right);
        });
    }
}