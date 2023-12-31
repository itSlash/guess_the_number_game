package com.itslash.guessnumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Button button_start = findViewById(R.id.button_start);
        Button button_about_game = findViewById(R.id.button_about_game);
        Button button_exit = findViewById(R.id.button_exit);
        button_start.setOnClickListener(view -> {
            Intent intent = new Intent(MainMenu.this, SelectParameters.class);
            startActivity(intent);
        });
        button_about_game.setOnClickListener(view -> {
            Intent intent = new Intent(MainMenu.this, AboutGame.class);
            startActivity(intent);
        });
        button_exit.setOnClickListener(view -> finishAffinity());
    }
}