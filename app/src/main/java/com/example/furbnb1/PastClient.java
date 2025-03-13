package com.example.furbnb1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class PastClient extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.past_client);

        ImageView imageButton3 = findViewById(R.id.imageButton3);
        imageButton3.setImageResource(R.drawable.return_bttn);

        imageButton3.setOnClickListener(v -> {
            Intent intent = new Intent(PastClient.this, HomePage.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_right, R.anim.slide_right);
        });
    }
}