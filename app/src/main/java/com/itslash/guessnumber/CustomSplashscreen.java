package com.itslash.guessnumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class CustomSplashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(CustomSplashscreen.this, MainMenu.class);
            startActivity(intent);
            finish();
        }, 1000);
    }
}