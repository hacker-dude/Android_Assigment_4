package com.example.android_assigment_4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(var items: ArrayList<Note>, val noteClick: ((name: Note) -> Unit)) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)

        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int = items.size

    inner class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val textView: TextView = view.findViewById(R.id.text)
        private val button: Button = view.findViewById(R.id.delete)

        fun onBind(pos: Int) {
            val item = items[pos]
            textView.text = item.text
            textView.setOnClickListener {
                noteClick(item)
            }

            button.setOnClickListener {
                items.removeAt(pos)
                notifyItemRemoved(pos)
            }
        }
    }
}