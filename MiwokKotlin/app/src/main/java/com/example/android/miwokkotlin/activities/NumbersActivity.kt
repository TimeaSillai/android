package com.example.android.miwokkotlin.activities

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.example.android.miwokkotlin.R
import com.example.android.miwokkotlin.WordAdapter
import com.example.android.miwokkotlin.model.Word
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.android.synthetic.main.word_list.*
import java.text.FieldPosition
import java.util.concurrent.TimeUnit

class NumbersActivity : AppCompatActivity() {

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
        words.add(Word("one", "lutti", R.drawable.number_one, R.raw.number_one))
        words.add(Word("two", "otiiko", R.drawable.number_two, R.raw.number_two))
        words.add(Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three))
        words.add(Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four))
        words.add(Word("five", "massokka", R.drawable.number_five, R.raw.number_five))
        words.add(Word("six", "temmokka", R.drawable.number_six, R.raw.number_six))
        words.add(Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven))
        words.add(Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight))
        words.add(Word("nine", "wo'e", R.drawable.number_nine, R.raw.number_nine))
        words.add(Word("ten", "na'aacha", R.drawable.number_ten, R.raw.number_ten))

        val itemsAdapter = WordAdapter(this, words, R.color.category_numbers)
        list.adapter = itemsAdapter

        list.setOnItemClickListener { _, _, position, _ ->
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


