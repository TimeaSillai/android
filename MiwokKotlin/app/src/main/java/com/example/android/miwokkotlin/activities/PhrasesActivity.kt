package com.example.android.miwokkotlin.activities

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.miwokkotlin.R
import com.example.android.miwokkotlin.WordAdapter
import com.example.android.miwokkotlin.model.Word
import kotlinx.android.synthetic.main.word_list.*

class PhrasesActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private var completionListener: MediaPlayer.OnCompletionListener =
        MediaPlayer.OnCompletionListener {
            releaseMediaPlayer()
        }

    override fun onStop() {
        super.onStop()
        releaseMediaPlayer()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.word_list)

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
            //releaseMediaPlayer()
            val word: Word = words[position]
            mediaPlayer = MediaPlayer.create(this, word.srcAudio)
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener { completionListener }
        }
    }

    private fun releaseMediaPlayer() {
        mediaPlayer.release()
    }
}
