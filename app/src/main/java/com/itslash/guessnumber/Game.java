package com.itslash.guessnumber;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Game extends AppCompatActivity {

    int value_from, value_before, value_number_of_attempts, hidden_number, diff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        TextView tv_guess_the_number = findViewById(R.id.label_guess_the_number);
        EditText etn_number = findViewById(R.id.editTextNumber_number);
        TextView tv_attempts_left = findViewById(R.id.attempts_left);
        Button button_check = findViewById(R.id.button_check);
        Button button_give_up = findViewById(R.id.button_give_up);
        Intent intent = getIntent();
        value_from = intent.getIntExtra("value_from", 0);
        value_before = intent.getIntExtra("value_before", 1);
        value_number_of_attempts = intent.getIntExtra("value_number_of_attempts", 0);
        diff = value_before - value_from;
        Random random = new Random();
        hidden_number = random.nextInt(diff + 1);
        hidden_number += value_from;
        tv_guess_the_number.setText("Guess the number from " + value_from + " to " + value_before);
        tv_attempts_left.setText("Attempts left: " + value_number_of_attempts);
        etn_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        button_check.setOnClickListener(view -> {
            if (!etn_number.getText().toString().equals(""))
            {
                if (tryParseInt(etn_number.getText().toString(), value_from) < value_from)
                {
                    etn_number.setText(String.valueOf(value_from));
                }
                else if (tryParseInt(etn_number.getText().toString(), value_from) > value_before) {
                    etn_number.setText(String.valueOf(value_before));
                }
                if (etn_number.getText().toString().charAt(0) == '0') {
                    for (int count = 0; count < etn_number.getText().length(); count++)
                    {
                        if (etn_number.getText().toString().charAt(0) == '0') {
                            etn_number.setText(etn_number.getText().delete(0, 1));
                        }
                    }
                }
            }
            if (!etn_number.getText().toString().equals("")) {
                if (tryParseInt(etn_number.getText().toString(), value_from) == hidden_number) {
                    AlertDialog alertDialog = dialogOneButton(R.string.dialog_game_win, R.string.dialog_number_guess, R.string.button_main_menu);
                    alertDialog.show();
                } else {
                    value_number_of_attempts--;
                    if (value_number_of_attempts > 0) {
                        tv_attempts_left.setText("Attempts left: " + value_number_of_attempts);
                        if (tryParseInt(etn_number.getText().toString(), value_from) > hidden_number) {
                            Toast.makeText(getApplicationContext(), R.string.dialog_hidden_number_less, Toast.LENGTH_SHORT).show();
                        } else if (tryParseInt(etn_number.getText().toString(), value_from) < hidden_number) {
                            Toast.makeText(getApplicationContext(), R.string.dialog_hidden_number_more, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        AlertDialog alertDialog = dialogOneButton(R.string.dialog_game_lose, R.string.dialog_number_not_guess, R.string.button_main_menu);
                        alertDialog.show();
                    }
                }
            }
        });
        button_give_up.setOnClickListener(view -> {
            AlertDialog alertDialog = dialogYesNo(R.string.dialog_give_up, R.string.dialog_give_up_question);
            alertDialog.show();
        });
    }

    AlertDialog dialogOneButton(int title, int message, int button) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(button, (dialog, id) -> finish());
        return builder.create();
    }

    AlertDialog dialogYesNo(int title, int message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.button_yes, (dialog, id) -> finish());
        builder.setNegativeButton(R.string.button_no, (dialog, i) -> {});
        return builder.create();
    }

    public int tryParseInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}