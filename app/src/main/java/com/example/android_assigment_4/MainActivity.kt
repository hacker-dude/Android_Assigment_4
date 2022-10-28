package com.example.android_assigment_4

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerview: RecyclerView
    private lateinit var input: EditText
    private lateinit var button: Button
    private lateinit var data: ArrayList<Note>
    private lateinit var adapter: NoteAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview = findViewById(R.id.recycler)
        recyclerview.layoutManager = LinearLayoutManager(this)

        listeners()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun listeners() {
        button = findViewById(R.id.buttonAdd)
        input = findViewById(R.id.input)

        button.setOnClickListener {
            val text = input.text.toString()
            if (text.isNotEmpty()) {
                data.plusAssign(Note(text, LocalDateTime.now().toString()))
                adapter.notifyDataSetChanged()
           }
        }
    }

    private fun switchActivities(item: Note) {
        val switchActivityIntent = Intent(this, NoteDetailActivity::class.java)
        switchActivityIntent.putExtra("text", item.text).putExtra("time",item.timeCreated)
        startActivity(switchActivityIntent)
    }

    override fun onPause() {
        super.onPause()
        val sharedPref = this.getSharedPreferences("shared preferences", MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            val gson = Gson()
            val json: String = gson.toJson(data)
            putStringSet("notes", setOf(json))
            apply()
        }
    }

    override fun onResume() {
        super.onResume()

        val sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE)

        val gson = Gson()

        val jsonSet = sharedPreferences.getStringSet("notes", null)

        val json = jsonSet?.elementAt(0)

        val type: Type = object : TypeToken<ArrayList<Note?>?>() {}.type

        data = if (json == null) ArrayList()
        else gson.fromJson<Any>(json, type) as ArrayList<Note>

        adapter = NoteAdapter(data) {
            switchActivities(it)
        }
        recyclerview.adapter = adapter

        adapter.notifyDataSetChanged()

    }
}