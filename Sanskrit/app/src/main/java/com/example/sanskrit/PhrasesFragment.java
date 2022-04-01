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


public class PhrasesFragment extends Fragment {

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
    public PhrasesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list, container, false);
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> phrases = new ArrayList<Word>();
        phrases.add(new Word("Where are you going?", "Bhavan/Bhavati kutra gacchati?",R.raw.bhavan));
        phrases.add(new Word("What is your name?", "Tava nama kim?", R.raw.tva_nama_ki));
        phrases.add(new Word("My name is...", "Mama nama ...", R.raw.mam_nama));
        phrases.add(new Word("How are you ?", "Kushalam va?", R.raw.kushalam_va));
        phrases.add(new Word("Welcome", "Svagatam", R.raw.svagatam));
        phrases.add(new Word("Hello", "Namaskarah", R.raw.namaskaar_ah));
        phrases.add(new Word("Good Morning", "Suprabhatam", R.raw.suprabhatam));
        phrases.add(new Word("Good Night", "Subharatri", R.raw.subhratrih));
        phrases.add(new Word("Good bye", "Punarmilama", R.raw.punarmilma));
        phrases.add(new Word("Good luck", "Saubhagyam", R.raw.saubhagyam));
        phrases.add(new Word("Sorry", "Krpaya Ksamyatam", R.raw.krapya_shamayam));
        phrases.add(new Word("Thank You", "Dhanyavadah", R.raw.dhanyavadah));
        phrases.add(new Word("Help", "Sahayyam", R.raw.sahayam));
        phrases.add(new Word("Stop", "Tistha", R.raw.tishtha));

        WordAdapter itemAdapter = new WordAdapter(getActivity(),phrases);
        ListView itemView = (ListView) rootView.findViewById(R.id.list);
        itemView.setAdapter(itemAdapter);
        itemView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Get the {@link Word} object at the given position the user clicked on
                releaseMediaPlayer();
                Word  number = phrases.get(position);


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