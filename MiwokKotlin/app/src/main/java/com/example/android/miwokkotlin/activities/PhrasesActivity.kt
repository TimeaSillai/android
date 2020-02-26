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

class PhrasesActivity : AppCompatActivity() {

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
        words.add(Word("Where are you going", "minto wuksus",0, R.raw.phrase_where_are_you_going))
        words.add(Word("What is your name?", "tinnә oyaase'nә",0, R.raw.phrase_what_is_your_name))
        words.add(Word("My name is...", "oyaaset...",0, R.raw.phrase_my_name_is))
        words.add(Word("How are you feeling?", "michәksәs?",0, R.raw.phrase_how_are_you_feeling))
        words.add(Word("I’m feeling good.", "kuchi achit",0, R.raw.phrase_im_feeling_good))
        words.add(Word("Are you coming?", "әәnәs'aa?",0, R.raw.phrase_are_you_coming))
        words.add(Word("Yes, I’m coming.", "hәә’ әәnәm",0, R.raw.phrase_yes_im_coming))
        words.add(Word("I’m coming.", "әәnәm",0, R.raw.phrase_im_coming))
        words.add(Word("Let’s go.", "yoowutis",0, R.raw.phrase_lets_go))
        words.add(Word("Come here.", "әnni'nem",0, R.raw.phrase_come_here))


        val itemsAdapter = WordAdapter(this, words, R.color.category_phrases)
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


