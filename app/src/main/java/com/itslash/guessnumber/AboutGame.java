package com.itslash.guessnumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

public class AboutGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_game);
        Button button_github = findViewById(R.id.button_github);
        Button button_back = findViewById(R.id.button_back);
        button_github.setOnClickListener(view -> {
            Intent intent_browse_link = new Intent(Intent.ACTION_VIEW);
            intent_browse_link.setData(Uri.parse("https://github.com/itSlash/guess_the_number_game"));
            startActivity(intent_browse_link);
        });
        button_back.setOnClickListener(view -> finish());
    }
}