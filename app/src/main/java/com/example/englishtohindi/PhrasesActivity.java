package com.example.englishtohindi;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {
    private ArrayList<Word> phrases;
    private ListView phrasesListView;
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

        phrases = new ArrayList<>();
        phrases.add(new Word("Where are you going?", "आप कहाँ जा रहे हैं?", R.raw.phrases_1));
        phrases.add(new Word("What is your name?", "आपका नाम क्या है?", R.raw.phrases_2));
        phrases.add(new Word("How are you feeling?", "आप कैसा महसूस कर रहे हैं?", R.raw.phrases_3));
        phrases.add(new Word("I’m feeling good.", "मैं अच्छा महसूस कर रहा हूं", R.raw.phrases_4));
        phrases.add(new Word("Are you coming?", "क्या आप आ रहे हैं?", R.raw.phrases_5));
        phrases.add(new Word("Yes, I’m coming", "हां, मैं आ रहा हूं", R.raw.phrases_6));
        phrases.add(new Word("Let’s go.", "चलो चलें", R.raw.phrases_7));
        phrases.add(new Word("Come here", "यहाँ आओ", R.raw.phrases_8));

        WordAdaptor phrasesWordAdaptor = new WordAdaptor(this, phrases, R.color.color_phrases);

        phrasesListView = findViewById(R.id.wordListView);
        phrasesListView.setAdapter(phrasesWordAdaptor);

        phrasesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word currWord = phrases.get(i);

                releaseMediaPlayer();

                audio = MediaPlayer.create(PhrasesActivity.this, currWord.getAudioResourcId());
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