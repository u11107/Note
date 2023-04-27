package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.NotesItemBinding
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Date

const val PRACTICUM_EXAMPLE_PREFERENCES = "practicum_example_preferences"
const val EDIT_TEXT_KEY = "key_for_edit_text"
const val DATA = "data"


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingNotes: NotesItemBinding
    private val noteList = ArrayList<Note>()
    private var text = ""
    private var currentDate = Date()
    private val dateFormat = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
    var datetext = dateFormat.format(currentDate)
    private var adapterNote = AdapterNote()
    private var saveData  = ""


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        bindingNotes = NotesItemBinding.inflate(layoutInflater)
        setContentView(bindingNotes.root)
        setContentView(binding.root)
        binding.rvNote.adapter = adapterNote

        val simpleTextWatcher = binding.etNote.doOnTextChanged { text, _, _, _ ->
            this@MainActivity.text = text.toString()
            if (!text.isNullOrEmpty()) {
                binding.ivClear.visibility = View.VISIBLE
            } else {
                binding.ivClear.visibility = View.GONE
            }
        }
        binding.etNote.addTextChangedListener(simpleTextWatcher)

        binding.apply {
            ivClear.setOnClickListener {
                etNote.setText("")
                ivClear.visibility = View.GONE
            }
        }

        val sharedPreferences = getSharedPreferences(PRACTICUM_EXAMPLE_PREFERENCES, MODE_PRIVATE)


        binding.btNote.setOnClickListener {
            val text = binding.etNote.text.toString()
            adapterNote.addNote(Note(text))
            val note = sharedPreferences
                .edit()
                .putString(EDIT_TEXT_KEY, binding.etNote.text.toString())
                .putString(DATA, datetext)
                .apply()
            adapterNote.notifyDataSetChanged()
        }

        val notes  = sharedPreferences.getString(EDIT_TEXT_KEY, null).toString()
        adapterNote.noteList = createNotesListFromJson(notes)
    }

    private fun createNotesListFromJson(json: String): Array<Note> {
        return Gson().fromJson(json, Array<Note>::class.java)
    }


    override fun onStop() {
        super.onStop()

        val sharedPreferences = getSharedPreferences(PRACTICUM_EXAMPLE_PREFERENCES, MODE_PRIVATE)
        sharedPreferences.edit()
            .putString(EDIT_TEXT_KEY, createJsonFromNotesList(adapterNote.noteList))
            .apply()
    }

    private fun createJsonFromNotesList(note: Array<Note>): String {
        return Gson().toJson(note)
    }

}


