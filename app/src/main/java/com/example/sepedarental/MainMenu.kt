package com.example.sepedarental

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView


class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        val menu1: CardView = findViewById<View>(R.id.menu1) as CardView
        menu1.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    ListData::class.java
                )
            )
        })
    }
}
