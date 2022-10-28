package com.example.android_assigment_4

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NoteDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        val noteText: TextView = findViewById(R.id.noteText)
        val noteDate: TextView = findViewById(R.id.date)


        noteText.text = intent.getStringExtra("text").toString()
        noteDate.text = intent.getStringExtra("time").toString()

    }
}