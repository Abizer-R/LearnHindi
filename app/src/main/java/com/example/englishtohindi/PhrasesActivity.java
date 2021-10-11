package com.example.englishtohindi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

    private ArrayList<Word> phrases;

    private MediaPlayer audio;

    private final MediaPlayer.OnCompletionListener audioCompleted = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // Permanent loss of audio focus
                        // stop playback and release the resources
                        releaseMediaPlayer();
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Pause playback
                        audio.pause();
                        audio.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Your app has been granted audio focus again
                        // resume playback
                        audio.start();
                    }
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

        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        phrases = new ArrayList<>();
        phrases.add(new Word("Where are you going?", "आप कहाँ जा रहे हैं?", R.raw.phrases_1));
        phrases.add(new Word("What is your name?", "आपका नाम क्या है?", R.raw.phrases_2));
        phrases.add(new Word("How are you feeling?", "आप कैसा महसूस कर रहे हैं?", R.raw.phrases_3));
        phrases.add(new Word("I’m feeling good.", "मैं अच्छा महसूस कर रहा हूं", R.raw.phrases_4));
        phrases.add(new Word("Are you coming?", "क्या आप आ रहे हैं?", R.raw.phrases_5));
        phrases.add(new Word("Yes, I’m coming", "हां, मैं आ रहा हूं", R.raw.phrases_6));
        phrases.add(new Word("Let’s go.", "चलो चलें", R.raw.phrases_7));
        phrases.add(new Word("Come here", "यहाँ आओ", R.raw.phrases_8));

        WordAdaptor phrasesWordAdaptor = new WordAdaptor(this, phrases);

        ListView phrasesListView = findViewById(R.id.wordListView);
        phrasesListView.setAdapter(phrasesWordAdaptor);

        phrasesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word currWord = phrases.get(i);

                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // We have audio focus now

                    audio = MediaPlayer.create(PhrasesActivity.this, currWord.getAudioResourcId());
                    audio.start();

                    audio.setOnCompletionListener(audioCompleted);
                }

            }
        });



    }

    public void releaseMediaPlayer() {

        if(audio != null)
            audio.release();

        audio = null;

        mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
    }
}