package com.example.android.miwokkotlin.activities

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.miwokkotlin.R
import com.example.android.miwokkotlin.WordAdapter
import com.example.android.miwokkotlin.model.Word
import kotlinx.android.synthetic.main.word_list.*

class FamilyActivity : AppCompatActivity() {

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