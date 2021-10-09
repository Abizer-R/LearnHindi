package com.example.englishtohindi;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
    private ArrayList<Word> numbers;
    private ListView numbersListView;
    private MediaPlayer audio;
    private MediaPlayer.OnCompletionListener audioCompleted = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list_layout);

        numbers = new ArrayList<>();
        numbers.add(new Word("one", "एक", R.drawable.number_one, R.raw.number_one));
        numbers.add(new Word("two", "दो", R.drawable.number_two, R.raw.number_two));
        numbers.add(new Word("three", "तीन", R.drawable.number_three, R.raw.number_three));
        numbers.add(new Word("four", "चार", R.drawable.number_four, R.raw.number_four));
        numbers.add(new Word("five", "पांच", R.drawable.number_five, R.raw.number_five));
        numbers.add(new Word("six", "छह", R.drawable.number_six, R.raw.number_six));
        numbers.add(new Word("seven", "सात", R.drawable.number_seven, R.raw.number_seven));
        numbers.add(new Word("eight", "आठ", R.drawable.number_eight, R.raw.number_eight));
        numbers.add(new Word("nine", "नौ", R.drawable.number_nine, R.raw.number_nine));
        numbers.add(new Word("ten", "दस", R.drawable.number_ten, R.raw.number_ten));

        WordAdaptor numbersWordAdaptor = new WordAdaptor(this, numbers, R.color.color_numbers);

        numbersListView = findViewById(R.id.wordListView);
        numbersListView.setAdapter(numbersWordAdaptor);

        numbersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word currWord = numbers.get(i);

                releaseMediaPlayer();
                audio = MediaPlayer.create(NumbersActivity.this, currWord.getAudioResourcId());
                audio.start();

                audio.setOnCompletionListener(audioCompleted);
            }
        });

    }

    public void releaseMediaPlayer() {

        if(audio != null)
            audio.release();

        audio = null;
    }
}