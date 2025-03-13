package com.example.furbnb1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.Arrays;
import java.util.List;

public class HomePage extends AppCompatActivity {

    private ViewPager2 viewPager;
    private Handler handler;
    private Runnable runnable;
    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        // Initialize views
        ImageView services = findViewById(R.id.services);
        ImageView rates = findViewById(R.id.rates);
        TextView seeAll = findViewById(R.id.seeAll);

        ImageView imageBookNow = findViewById(R.id.image_book_now);
        ImageView imageAccount = findViewById(R.id.image_account);

        TextView textBookNow = findViewById(R.id.text_book_now);
        TextView textAccount = findViewById(R.id.text_account);

        // Set up ViewPager2 for image carousel
        viewPager = findViewById(R.id.viewPager);
        List<Integer> imageIds = Arrays.asList(
                R.drawable.appval,
                R.drawable.appgrad,
                R.drawable.appsummer
        );
        ImageCarouselAdapter adapter = new ImageCarouselAdapter(this, imageIds);
        viewPager.setAdapter(adapter);

        // Enable smooth scrolling
        viewPager.setOffscreenPageLimit(3);
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2, false);

        // Auto-scroll images every 5 seconds
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPage == adapter.getItemCount()) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
                handler.postDelayed(this, 5000); // 5 seconds delay
            }
        };
        handler.postDelayed(runnable, 9000); // Initial delay

        // Set click listeners for services and rates
        services.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, Our_services.class);
            startActivity(intent);
        });

        rates.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, Our_rates.class);
            startActivity(intent);
        });

        seeAll.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, PastClient.class);
            startActivity(intent);
        });

        // Set click listeners for book now and account
        View.OnClickListener bookNowClickListener = v -> {
            Intent intent = new Intent(HomePage.this, Booking.class);
            startActivity(intent);
        };

        View.OnClickListener accountClickListener = v -> {
            Intent intent = new Intent(HomePage.this, Account.class);
            startActivity(intent);
        };

        imageBookNow.setOnClickListener(bookNowClickListener);
        textBookNow.setOnClickListener(bookNowClickListener);

        imageAccount.setOnClickListener(accountClickListener);
        textAccount.setOnClickListener(accountClickListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable); // stop the handler when the activity is destroyed
    }
}