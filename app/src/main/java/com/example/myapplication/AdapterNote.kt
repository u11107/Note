package com.example.myapplication

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class AdapterNote() : RecyclerView.Adapter<HolderNote>() {

    var noteList =  arrayOf<Note>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderNote {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_item, parent, false)
        return HolderNote(view)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: HolderNote, position: Int) {
        holder.bind(noteList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addNote(note:Note) {
        noteList = arrayOf(note)
        notifyDataSetChanged()
    }

}