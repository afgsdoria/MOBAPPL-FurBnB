package com.example.furbnb1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Rate extends AppCompatActivity {

    private EditText feedbackEditText;
    private TextView messagerates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate);

        ImageView imageButton2 = findViewById(R.id.imageButton2);
        imageButton2.setImageResource(R.drawable.return_bttn);

        imageButton2.setOnClickListener(v -> {
            Intent intent = new Intent(Rate.this, AccSettings.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_right, R.anim.slide_right);
        });

        RatingBar rating = findViewById(R.id.ratingBar);
        feedbackEditText = findViewById(R.id.editTextText);
        TextView submitTextView = findViewById(R.id.submit_rate);
        TextView cancelTextView = findViewById(R.id.cancel_rate);
        messagerates = findViewById(R.id.message_rate);

        submitTextView.setOnClickListener(v -> {
            String feedback = feedbackEditText.getText().toString();
            float ratingValue = rating.getRating(); //  rating value

            if (ratingValue == 0) { // for no rating
                messagerates.setText("Please provide a rating.");
            } else {
                if (feedback.isEmpty()) {
                    messagerates.setText("Thank you for rating us!"); // kapag rating lang
                } else {
                    messagerates.setText("Thank you for your feedback and rating!"); // kapag nag bigay rating and feedback
                }
                Intent intent = new Intent(Rate.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });

        cancelTextView.setOnClickListener(v -> {
            Intent intent = new Intent(Rate.this, AccSettings.class);
            startActivity(intent);
            finish();
        });
    }
}