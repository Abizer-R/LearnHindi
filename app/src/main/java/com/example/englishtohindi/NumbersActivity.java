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

public class NumbersActivity extends AppCompatActivity {

    private ArrayList<Word> numbers;

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

        WordAdaptor numbersWordAdaptor = new WordAdaptor(this, numbers);

        ListView numbersListView = findViewById(R.id.wordListView);
        numbersListView.setAdapter(numbersWordAdaptor);

        numbersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word currWord = numbers.get(i);

                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // We have audio focus now

                    audio = MediaPlayer.create(NumbersActivity.this, currWord.getAudioResourcId());
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