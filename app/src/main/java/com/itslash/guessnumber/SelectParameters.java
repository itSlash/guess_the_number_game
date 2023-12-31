package com.itslash.guessnumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SelectParameters extends AppCompatActivity {

    int value_number_of_attempts = 1;
    int value_from = 0;
    int value_before = 0;
    int value_from_editText, value_before_editText;
    boolean checked_number = false;
    boolean checked_number_from = false;
    boolean checked_number_before = false;
    String[] str = { "0-1", "0-5", "0-10", "0-50", "0-100", "0-500", "0-1000", "Your interval"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_parameters);
        EditText etn_number_of_attempts = findViewById(R.id.editTextNumber_number_of_attempts);
        EditText etn_from = findViewById(R.id.editTextNumber_from);
        EditText etn_before = findViewById(R.id.editTextNumber_before);
        TextView tv_from = findViewById(R.id.label_from);
        TextView tv_before = findViewById(R.id.label_before);
        Button button_start_game = findViewById(R.id.button_start_game);
        Button button_back = findViewById(R.id.button_back);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_selected_item, str);
        Spinner spinner = findViewById(R.id.spinner_hidden_number_interval);
        spinner.setAdapter(adapter);
        etn_number_of_attempts.setText("1");
        etn_from.setText("0");
        etn_before.setText("1");
        value_number_of_attempts = Integer.parseInt(etn_number_of_attempts.getText().toString());
        value_from_editText = Integer.parseInt(etn_from.getText().toString());
        value_before_editText = Integer.parseInt(etn_before.getText().toString());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinner.getSelectedItem().toString().equals("0-1")) {
                    value_from = 0;
                    value_before = 1;
                } else if (spinner.getSelectedItem().toString().equals("0-5")) {
                    value_from = 0;
                    value_before = 5;
                } else if (spinner.getSelectedItem().toString().equals("0-10")) {
                    value_from = 0;
                    value_before = 10;
                } else if (spinner.getSelectedItem().toString().equals("0-50")) {
                    value_from = 0;
                    value_before = 50;
                } else if (spinner.getSelectedItem().toString().equals("0-100")) {
                    value_from = 0;
                    value_before = 100;
                } else if (spinner.getSelectedItem().toString().equals("0-500")) {
                    value_from = 0;
                    value_before = 500;
                } else if (spinner.getSelectedItem().toString().equals("0-1000")) {
                    value_from = 0;
                    value_before = 1000;
                } else if (spinner.getSelectedItem().toString().equals("Your interval")) {
                    value_from = value_from_editText;
                    value_before = value_before_editText;
                }
                if (spinner.getSelectedItem().toString().equals("Your interval")) {
                    etn_from.setVisibility(View.VISIBLE);
                    etn_before.setVisibility(View.VISIBLE);
                    tv_from.setVisibility(View.VISIBLE);
                    tv_before.setVisibility(View.VISIBLE);
                }
                else {
                    etn_from.setVisibility(View.INVISIBLE);
                    etn_before.setVisibility(View.INVISIBLE);
                    tv_from.setVisibility(View.INVISIBLE);
                    tv_before.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        etn_number_of_attempts.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checked_number = checkNumber(etn_number_of_attempts, 1, 100);
                if (checked_number) {
                    value_number_of_attempts = tryParseInt(etn_number_of_attempts.getText().toString(), 1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        etn_from.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checked_number_from = checkNumber(etn_from, 0, 1000000);
                if (checked_number_from) {
                    value_from = tryParseInt(etn_from.getText().toString(), 0);
                    value_from_editText = tryParseInt(etn_from.getText().toString(), 0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        etn_before.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checked_number_before = checkNumber(etn_before, 0, 100000000);
                if (checked_number_before) {
                    value_before = tryParseInt(etn_before.getText().toString(), 1);
                    value_before_editText = tryParseInt(etn_before.getText().toString(), 1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        button_start_game.setOnClickListener(view -> {
            if (checkFields(etn_number_of_attempts, etn_from, etn_before, spinner)) {
                Intent intent_start_game = new Intent(SelectParameters.this, Game.class);
                intent_start_game.putExtra("value_from", value_from);
                intent_start_game.putExtra("value_before", value_before);
                intent_start_game.putExtra("value_number_of_attempts", value_number_of_attempts);
                startActivity(intent_start_game);
                finish();
            }
        });
        button_back.setOnClickListener(view -> finish());
    }

    private boolean checkNumber(EditText editText, int minValue, int maxValue)
    {
        if (!editText.getText().toString().equals(""))
        {
            if (tryParseInt(editText.getText().toString(), minValue) < minValue)
            {
                editText.setText(String.valueOf(minValue));
                return true;
            }
            else if (tryParseInt(editText.getText().toString(), minValue) > maxValue) {
                editText.setText(String.valueOf(maxValue));
                return true;
            }
            if (editText.getText().toString().charAt(0) == '0' & minValue > 0) {
                for (int i = 0; i < editText.getText().length(); i++)
                {
                    if (editText.getText().toString().charAt(0) == '0') {
                        editText.setText(editText.getText().delete(0, 1));
                    }
                }
                return true;
            }
            return true;
        }
        else { return false; }
    }

    private boolean reverseNumbers(EditText editTextFirst, EditText editTextSecond)
    {
        if (!editTextFirst.getText().toString().equals("") & !editTextSecond.getText().toString().equals(""))
        {
            if (tryParseInt(editTextFirst.getText().toString(), 0) > tryParseInt(editTextSecond.getText().toString(), 1)) {
                String reverseNumber = editTextFirst.getText().toString();
                editTextFirst.setText(editTextSecond.getText().toString());
                editTextSecond.setText(reverseNumber);
                value_from = tryParseInt(editTextFirst.getText().toString(), 0);
                value_before = tryParseInt(editTextSecond.getText().toString(), 1);
                return true;
            } else if (tryParseInt(editTextFirst.getText().toString(), 0) == tryParseInt(editTextSecond.getText().toString(), 1)) {
                Toast.makeText(this, R.string.dialog_equal_numbers, Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        }
        else { return false;}
    }

    private boolean checkFields(EditText etn_number_of_attempts, EditText etn_from, EditText etn_before, Spinner spinner) {
        if (!spinner.getSelectedItem().toString().equals("Your interval")) {
            if (etn_number_of_attempts.getText().toString().equals("")) {
                Toast.makeText(this, R.string.dialog_empty_value_numbers_of_attempts, Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        } else {
            if (etn_number_of_attempts.getText().toString().equals("")) {
                Toast.makeText(this, R.string.dialog_empty_value_numbers_of_attempts, Toast.LENGTH_SHORT).show();
                return false;
            } else if (etn_from.getText().toString().equals("")) {
                Toast.makeText(this, R.string.dialog_empty_value_from, Toast.LENGTH_SHORT).show();
                return false;
            } else if (etn_before.getText().toString().equals("")) {
                Toast.makeText(this, R.string.dialog_empty_value_before, Toast.LENGTH_SHORT).show();
                return false;
            } else return reverseNumbers(etn_from, etn_before);
        }
    }

    public int tryParseInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}