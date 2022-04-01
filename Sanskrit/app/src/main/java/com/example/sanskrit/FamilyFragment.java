package com.example.sanskrit;

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
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };
    private MediaPlayer.OnCompletionListener OnComplete = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    public FamilyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView  =  inflater.inflate(R.layout.word_list, container, false);
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> family = new ArrayList<Word>();
        family.add(new Word("Mother", "जननी ",R.drawable.family_mother,R.raw.mata));
        family.add(new Word("Father", "जनक ",R.drawable.family_father,R.raw.pitah));
        family.add(new Word("husband", "पति  ",R.drawable.family_older_brother,R.raw.patih));
        family.add(new Word("Wife", "पत्नी  ",R.drawable.family_mother,R.raw.patni));
        family.add(new Word("Son", "तनय ",R.drawable.family_son,R.raw.putrah));
        family.add(new Word("Paternal GrandFather", "पितामही ",R.drawable.family_grandmother,R.raw.pitamah));
        family.add(new Word("Paternal Grandmother ", "मातामही   ",R.drawable.family_grandmother,R.raw.matamahi));
        family.add(new Word("Elder sister", "अग्रजा ",R.drawable.family_daughter,R.raw.jyesthabhagini));
        family.add(new Word("Younger sister", "अनुजा  ",R.drawable.family_younger_brother,R.raw.knishthabhagini));
        family.add(new Word("Elder Brother", "अग्रज ",R.drawable.family_younger_sister,R.raw.jyeshtha_bhrata));
        family.add(new Word("Younger Brother", "अनुज",R.drawable.family_younger_brother,R.raw.kanisthabhrata));
        family.add(new Word("Father-in-Law", "Svasurah", R.drawable.family_grandfather, R.raw.svasurah));
        family.add(new Word("Mother-in-Law", "Svasruh", R.drawable.family_grandmother, R.raw.svasruh));
        family.add(new Word("Wife’s Brother", "Syalah", R.drawable.family_younger_brother, R.raw.syalah));
        family.add(new Word("Wife’s Sister", "Syali", R.drawable.family_older_sister, R.raw.syali));
        family.add(new Word("Husband’s Brother (Elder)", "Jyesthabhrata", R.drawable.family_older_brother, R.raw.jyeshtha_bhrata));
        family.add(new Word("Husband’s Brother (Younger)", "Devarah", R.drawable.family_younger_brother, R.raw.dverah));
        family.add(new Word("Husband’s Sister", "Nananda", R.drawable.family_younger_sister, R.raw.nanadah));
        family.add(new Word("Grandson", "Pautrah", R.drawable.family_son, R.raw.pautrah));
        family.add(new Word("Granddaughter", "Pautri", R.drawable.family_daughter, R.raw.pautri));
        family.add(new Word("Daughter’s Son", "Dauhitrah", R.drawable.family_son, R.raw.dauhitrah));
        family.add(new Word("Daughter’s Daughter", "Dauhitri", R.drawable.family_daughter, R.raw.dauhitri));
        family.add(new Word("Friend", "Sakha", R.drawable.family_younger_brother, R.raw.sakha));

        WordAdapter itemsAdapter = new WordAdapter(getActivity(),family);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Get the {@link Word} object at the given position the user clicked on
                releaseMediaPlayer();
                Word  number = family.get(position);


                // Request audio focus for playback
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(getActivity(), number.getmMusic());

                    // Start the audio file
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(OnComplete);
                }

            }
        });
        // Inflate the layout for this fragment

       return rootView;
    }
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}