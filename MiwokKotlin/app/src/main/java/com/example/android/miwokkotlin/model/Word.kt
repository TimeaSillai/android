package com.example.android.miwokkotlin.model

import androidx.annotation.IntegerRes

class Word (var mDefaultTranslation: String,var mMiwokTranslation: String,var srcImage: Int = 0, var srcAudio: Int = 0) {

    override fun toString(): String {
        return "Word(mDefaultTranslation='$mDefaultTranslation', mMiwokTranslation='$mMiwokTranslation', srcImage=$srcImage, srcAudio=$srcAudio)"
    }
}