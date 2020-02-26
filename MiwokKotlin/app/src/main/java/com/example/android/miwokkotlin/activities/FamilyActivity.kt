package com.example.android.miwokkotlin.activities

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.miwokkotlin.R
import com.example.android.miwokkotlin.WordAdapter
import com.example.android.miwokkotlin.model.Word
import kotlinx.android.synthetic.main.word_list.*

class FamilyActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var audioManager: AudioManager

    /**
     * This listener gets triggered whenever the audio focus changes
     * (i.e., we gain or lose audio focus because of another app or device).
     */
    private val afChangeListener = AudioManager.OnAudioFocusChangeListener { focusChange ->
        when (focusChange) {
            AudioManager.AUDIOFOCUS_LOSS -> {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer()
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                // Pause playback and reset player to the start of the file
                // that way, we can play the word from the beginning when we resume playback
                mediaPlayer?.pause()
                mediaPlayer?.seekTo(0)
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
                // Lower the volume, keep playing
                mediaPlayer?.pause()
                mediaPlayer?.seekTo(0)
            }
            AudioManager.AUDIOFOCUS_GAIN -> {
                // Your app has been granted audio focus again
                // Raise volume to normal, restart playback if necessary
                mediaPlayer?.start()
            }
        }
    }

    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private var completionListener: MediaPlayer.OnCompletionListener =
        MediaPlayer.OnCompletionListener {
            releaseMediaPlayer()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.word_list)

        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        val words: ArrayList<Word> = arrayListOf()
        words.add(Word("father", "әpә", R.drawable.family_father, R.raw.family_father))
        words.add(Word("mother", "әṭa", R.drawable.family_mother, R.raw.family_mother))
        words.add(Word("son", "angsi", R.drawable.family_son, R.raw.family_son))
        words.add(Word("daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter))
        words.add(Word("older brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother))
        words.add(Word("younger brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother))
        words.add(Word("older sister", "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister))
        words.add(Word("younger sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister))
        words.add(Word("grandmother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother))
        words.add(Word("grandfather", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather))


        val itemsAdapter = WordAdapter(this, words, R.color.category_family)
        list.adapter = itemsAdapter

        list.setOnItemClickListener { parent, view, position, id ->
            releaseMediaPlayer()
            val word: Word = words[position]

            // Request audio focus for playback
            val result: Int = audioManager.requestAudioFocus(
                afChangeListener,
                // Use the music stream.
                AudioManager.STREAM_MUSIC,
                // Request permanent focus.
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT
            )

            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                mediaPlayer = MediaPlayer.create(this, word.srcAudio)
                mediaPlayer?.start()
                mediaPlayer?.setOnCompletionListener { completionListener }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer()
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private fun releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if(mediaPlayer != null)
        {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer?.release()
            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null
            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            audioManager.abandonAudioFocus(afChangeListener)
        }

    }
}