package com.example.englishtohindi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView numbersTextView;
    private TextView familyTextView;
    private TextView colorsTextView;
    private TextView phrasesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numbersTextView = findViewById(R.id.numbersTextView);
        numbersTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NumbersActivity.class);
                startActivity(intent);
            }
        });

        familyTextView = findViewById(R.id.familyTextView);
        familyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FamilyActivity.class);
                startActivity(intent);
            }
        });

        colorsTextView = findViewById(R.id.colorsTextView);
        colorsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ColorsActivity.class);
                startActivity(intent);
            }
        });

        phrasesTextView = findViewById(R.id.phrasesTextView);
        phrasesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PhrasesActivity.class);
                startActivity(intent);
            }
        });


    }

}