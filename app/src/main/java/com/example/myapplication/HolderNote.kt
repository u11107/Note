package com.example.myapplication

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.NotesItemBinding
import java.text.SimpleDateFormat
import java.util.Date

class HolderNote(itemNote: View) : RecyclerView.ViewHolder(itemNote) {

    private val binding = NotesItemBinding.bind(itemNote)

//    private val dateFormat = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
//    private val currentDate: String = dateFormat.format(Date())

    fun bind(note: Note) = with(binding) {
//        tvDate.text = currentDate
        tvContent.text = note.content
    }


}