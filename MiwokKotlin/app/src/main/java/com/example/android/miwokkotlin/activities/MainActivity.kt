package com.example.android.miwokkotlin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.miwokkotlin.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numbers.setOnClickListener {
            openNumberList()
        }

        family.setOnClickListener {
            openFamilyList()
        }

        colors.setOnClickListener {
            openColorList()
        }

        phrases.setOnClickListener {
            openPhrasesList()
        }
    }

    private fun openNumberList(){
        val numberPage = Intent(this, NumbersActivity::class.java)
        startActivity(numberPage)

    }

    private fun openFamilyList(){
        val familyPage = Intent(this, FamilyActivity::class.java)
        startActivity(familyPage)
    }

    private fun openColorList(){
        val colorPage = Intent(this, ColorsActivity::class.java)
        startActivity(colorPage)
    }

    private fun openPhrasesList(){
        val phrasesPage = Intent(this, PhrasesActivity::class.java)
        startActivity(phrasesPage)
    }


}
