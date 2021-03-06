package com.example.englishtohindi;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class NumbersFragment extends Fragment {

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

    public void releaseMediaPlayer() {

        if(audio != null)
            audio.release();

        audio = null;

        mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
    }



    public NumbersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.word_list_layout, container, false);

        mAudioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);

        numbers = new ArrayList<>();
        numbers.add(new Word("one", "??????", R.drawable.number_one, R.raw.number_one));
        numbers.add(new Word("two", "??????", R.drawable.number_two, R.raw.number_two));
        numbers.add(new Word("three", "?????????", R.drawable.number_three, R.raw.number_three));
        numbers.add(new Word("four", "?????????", R.drawable.number_four, R.raw.number_four));
        numbers.add(new Word("five", "????????????", R.drawable.number_five, R.raw.number_five));
        numbers.add(new Word("six", "??????", R.drawable.number_six, R.raw.number_six));
        numbers.add(new Word("seven", "?????????", R.drawable.number_seven, R.raw.number_seven));
        numbers.add(new Word("eight", "??????", R.drawable.number_eight, R.raw.number_eight));
        numbers.add(new Word("nine", "??????", R.drawable.number_nine, R.raw.number_nine));
        numbers.add(new Word("ten", "??????", R.drawable.number_ten, R.raw.number_ten));

        WordAdaptor numbersWordAdaptor = new WordAdaptor(getActivity(), numbers);

        ListView numbersListView = rootView.findViewById(R.id.wordListView);
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

                    audio = MediaPlayer.create(getActivity(), currWord.getAudioResourcId());
                    audio.start();

                    audio.setOnCompletionListener(audioCompleted);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();

        releaseMediaPlayer();
    }

}