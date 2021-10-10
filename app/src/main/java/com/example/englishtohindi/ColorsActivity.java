package com.example.englishtohindi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    private ArrayList<Word> colors;

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

        colors = new ArrayList<>();
        colors.add(new Word("Black", "काला", R.drawable.color_black, R.raw.color_black));
        colors.add(new Word("Brown", "भूरा", R.drawable.color_brown, R.raw.color_brown));
        colors.add(new Word("Gray", "धूसर, स्लेटी", R.drawable.color_gray, R.raw.color_gray));
        colors.add(new Word("Green", "हरा", R.drawable.color_green, R.raw.color_green));
        colors.add(new Word("Yellow", "पीली", R.drawable.color_mustard_yellow, R.raw.color_yellow));
        colors.add(new Word("Red", "लाल", R.drawable.color_red, R.raw.color_red));
        colors.add(new Word("White", "सफ़ेद", R.drawable.color_white, R.raw.color_red));

        WordAdaptor colorsWordAdaptor = new WordAdaptor(this, colors, R.color.color_colors);

        ListView colorsListView = findViewById(R.id.wordListView);
        colorsListView.setAdapter(colorsWordAdaptor);

        colorsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word currWord = colors.get(i);

                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // We have audio focus now

                    audio = MediaPlayer.create(ColorsActivity.this, currWord.getAudioResourcId());
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