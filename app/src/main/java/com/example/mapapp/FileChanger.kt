package com.example.mapapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import org.json.JSONObject
import java.io.File


class FileEditorActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_editor)

        //elements of xml file
        val fileNameTextView: TextView = findViewById(R.id.file_editor_filename)
        val listView: ListView = findViewById(R.id.file_editor_listview)

        val questionIterator = parseJSON("$chosenFile.json").keys()
        val questionArray = ArrayList<String>()

        questionIterator.forEach {
           questionArray.add(it.toString())
        }

        //sets text of headline
        fileNameTextView.text = chosenFile

        //creating listview items
        listView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, questionArray)
        listView.setOnItemClickListener { adapterview, view, i, l ->

        }

    }

    fun openEntry(entryKey: String) {
        //work in progress
    }

    fun parseJSON(file: String): JSONObject {
        val jsonData = File(applicationContext.filesDir, "$file.json").readText(Charsets.UTF_8)
        val jsonObj = JSONObject(jsonData)
        return jsonObj
    }
}
