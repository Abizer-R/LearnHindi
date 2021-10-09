package com.example.englishtohindi;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {
    private ArrayList<Word> family;
    private ListView familyListView;
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

        family = new ArrayList<>();
        family.add(new Word("father", "पिता ", R.drawable.family_father, R.raw.family_father));
        family.add(new Word("mother", "माँ ", R.drawable.family_mother, R.raw.family_mother));
        family.add(new Word("son", "पुत्र ", R.drawable.family_son, R.raw.family_son));
        family.add(new Word("daughter", "पुत्री", R.drawable.family_daughter, R.raw.family_daughter));
        family.add(new Word("younger brother", "(छोटा) भाई ", R.drawable.family_younger_brother, R.raw.family_youngbrother));
        family.add(new Word("elder brother", "भैया/(बड़ा) भाई ", R.drawable.family_older_brother, R.raw.family_elderbrother));
        family.add(new Word("younger sister", "(छोटी) बहन", R.drawable.family_younger_sister, R.raw.family_youngersister));
        family.add(new Word("elder sister", "दीदी/(बड़ी) बहन", R.drawable.family_older_sister, R.raw.family_eldersister));
        family.add(new Word("grandfather (father's father)", "दादा ", R.drawable.family_grandfather, R.raw.family_dada));
        family.add(new Word("grandmother (father's mother)", "दादी", R.drawable.family_grandmother, R.raw.family_dadi));
        family.add(new Word("grandfather (mother's father)", "नाना  ", R.drawable.family_grandfather, R.raw.family_nana));
        family.add(new Word("grandmother (mother's mother)", "नानी ", R.drawable.family_grandmother, R.raw.family_nani));

        WordAdaptor familyWordAdaptor = new WordAdaptor(this, family, R.color.color_family);

        familyListView = findViewById(R.id.wordListView);
        familyListView.setAdapter(familyWordAdaptor);

        familyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word currWord = family.get(i);

                releaseMediaPlayer();
                audio = MediaPlayer.create(FamilyActivity.this, currWord.getAudioResourcId());
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