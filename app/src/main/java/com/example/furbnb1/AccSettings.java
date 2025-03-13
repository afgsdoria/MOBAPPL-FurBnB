package com.example.furbnb1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AccSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acc_settings);

        ImageView imageButton3 = findViewById(R.id.imageButton3);
        imageButton3.setImageResource(R.drawable.return_bttn);

        imageButton3.setOnClickListener(v -> {
            Intent intent = new Intent(AccSettings.this, Account.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_right, R.anim.slide_right);
        });

        // for edit account image button; pag pinindot mapupunta to EditAccActivity
        ImageButton imageButton = findViewById(R.id.editAccBttn);
        imageButton.setOnClickListener(v -> {
            Intent intent = new Intent(AccSettings.this, EditAcc.class);
            startActivity(intent);
        });
    }


    public void onCardClick(View view) {
        Intent intent = new Intent(AccSettings.this, Rate.class);
        startActivity(intent);
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Log Out");
        builder.setMessage("Are you sure you want to log out?");

        builder.setPositiveButton("Log Out", (dialog, which) -> {
            Intent intent = new Intent(AccSettings.this, Login.class);
            startActivity(intent);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}