package com.example.englishtohindi;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {
    private ArrayList<Word> colors;
    private ListView colorsListView;
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

        colors = new ArrayList<>();
        colors.add(new Word("Black", "काला", R.drawable.color_black, R.raw.color_black));
        colors.add(new Word("Brown", "भूरा", R.drawable.color_brown, R.raw.color_brown));
        colors.add(new Word("Gray", "धूसर, स्लेटी", R.drawable.color_gray, R.raw.color_gray));
        colors.add(new Word("Green", "हरा", R.drawable.color_green, R.raw.color_green));
        colors.add(new Word("Yellow", "पीली", R.drawable.color_mustard_yellow, R.raw.color_yellow));
        colors.add(new Word("Red", "लाल", R.drawable.color_red, R.raw.color_red));
        colors.add(new Word("White", "सफ़ेद", R.drawable.color_white, R.raw.color_red));

        WordAdaptor colorsWordAdaptor = new WordAdaptor(this, colors, R.color.color_colors);

        colorsListView = findViewById(R.id.wordListView);
        colorsListView.setAdapter(colorsWordAdaptor);

        colorsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word currWord = colors.get(i);

                releaseMediaPlayer();
                audio = MediaPlayer.create(ColorsActivity.this, currWord.getAudioResourcId());
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