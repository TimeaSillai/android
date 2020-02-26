package com.example.android.miwokkotlin

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import com.example.android.miwokkotlin.model.Word
import kotlinx.android.synthetic.main.list_item.view.*

class WordAdapter(context: Context, words: ArrayList<Word>,var resIdColor: Int) :
    ArrayAdapter<Word>(context, 0, words) {

    private lateinit var mediaPlayer: MediaPlayer

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {

        var listItemView: View? = convertView
        if(listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(
                R.layout.list_item, parent, false)
        }

        val currentWord = getItem(position)

        listItemView?.miwokTranslation?.text = currentWord?.mMiwokTranslation
        listItemView?.defaultTranslation?.text = currentWord?.mDefaultTranslation
        if(currentWord?.srcImage != 0){
            currentWord?.srcImage?.let { listItemView?.wordImage?.setImageResource(it) }
        } else {
            listItemView?.wordImage?.visibility= View.GONE
        }

        //list item background color
        val color = ContextCompat.getColor(context, resIdColor)
        listItemView?.textContainer?.setBackgroundColor(color)

        return listItemView
    }

}

