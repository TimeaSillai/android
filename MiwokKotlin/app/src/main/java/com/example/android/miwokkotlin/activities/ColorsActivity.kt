package com.example.android.miwokkotlin.activities

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.miwokkotlin.R
import com.example.android.miwokkotlin.WordAdapter
import com.example.android.miwokkotlin.model.Word
import kotlinx.android.synthetic.main.word_list.*

class ColorsActivity : AppCompatActivity() {

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
        words.add(Word("red", "weṭeṭṭi", R.drawable.color_red, R.raw.color_red))
        words.add(Word("green", "chokokki", R.drawable.color_green ,R.raw.color_green))
        words.add(Word("brown", "ṭakaakki", R.drawable.color_brown, R.raw.color_brown))
        words.add(Word("gray", "ṭopoppi", R.drawable.color_gray, R.raw.color_gray))
        words.add(Word("black", "massokka", R.drawable.color_black, R.raw.color_black))
        words.add(Word("white", "kelelli", R.drawable.color_white, R.raw.color_white))
        words.add(Word("dusty yellow", "ṭopiisә", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow))
        words.add(Word("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow))


        val itemsAdapter = WordAdapter(this, words, R.color.category_colors)
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
