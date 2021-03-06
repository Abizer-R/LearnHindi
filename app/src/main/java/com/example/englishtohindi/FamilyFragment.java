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


public class FamilyFragment extends Fragment {

    private ArrayList<Word> family;

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

    public FamilyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.word_list_layout, container, false);

        mAudioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);

        family = new ArrayList<>();
        family.add(new Word("father", "???????????? ", R.drawable.family_father, R.raw.family_father));
        family.add(new Word("mother", "????????? ", R.drawable.family_mother, R.raw.family_mother));
        family.add(new Word("son", "??????????????? ", R.drawable.family_son, R.raw.family_son));
        family.add(new Word("daughter", "??????????????????", R.drawable.family_daughter, R.raw.family_daughter));
        family.add(new Word("younger brother", "(????????????) ????????? ", R.drawable.family_younger_brother, R.raw.family_youngbrother));
        family.add(new Word("elder brother", "????????????/(?????????) ????????? ", R.drawable.family_older_brother, R.raw.family_elderbrother));
        family.add(new Word("younger sister", "(????????????) ?????????", R.drawable.family_younger_sister, R.raw.family_youngersister));
        family.add(new Word("elder sister", "????????????/(?????????) ?????????", R.drawable.family_older_sister, R.raw.family_eldersister));
        family.add(new Word("grandfather (father's father)", "???????????? ", R.drawable.family_grandfather, R.raw.family_dada));
        family.add(new Word("grandmother (father's mother)", "????????????", R.drawable.family_grandmother, R.raw.family_dadi));
        family.add(new Word("grandfather (mother's father)", "????????????  ", R.drawable.family_grandfather, R.raw.family_nana));
        family.add(new Word("grandmother (mother's mother)", "???????????? ", R.drawable.family_grandmother, R.raw.family_nani));

        WordAdaptor familyWordAdaptor = new WordAdaptor(getActivity(), family);

        ListView familyListView = rootview.findViewById(R.id.wordListView);
        familyListView.setAdapter(familyWordAdaptor);

        familyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word currWord = family.get(i);

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

        return rootview;
    }

    @Override
    public void onStop() {
        super.onStop();

        releaseMediaPlayer();
    }
}