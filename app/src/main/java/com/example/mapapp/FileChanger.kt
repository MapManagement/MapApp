package com.example.mapapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONObject
import java.io.File

var chosenEntryKey = ""


class FileEditorActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_editor)

        //elements of xml file
        val fileNameTextView: TextView = findViewById(R.id.file_editor_filename)
        val listView: ListView = findViewById(R.id.file_editor_listview)
        val FAB: FloatingActionButton = findViewById(R.id.editor_floating_point)

        val questionIterator = parseJSON(chosenFile).keys()
        val questionArray = ArrayList<String>()

        questionIterator.forEach {
           questionArray.add(it.toString())
        }

        //sets text of headline
        fileNameTextView.text = chosenFile

        //creating listview items
        listView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, questionArray)
        listView.setOnItemClickListener { adapterview, view, i, l ->
            openEntry(questionArray[i])
        }

        FAB.setOnClickListener { openEntry("newEntryKey") }

    }

    fun openEntry(entryKey: String) {
        chosenEntryKey = entryKey
        val intent = Intent(this, EntryEditorActivity::class.java)
        startActivity(intent)
    }

    fun parseJSON(file: String): JSONObject {
        val jsonData = File(applicationContext.filesDir, "$file.json").readText(Charsets.UTF_8)
        val jsonObj = JSONObject(jsonData)
        return jsonObj
    }

    override fun onBackPressed() {
        val intent = Intent(this, FileActivity::class.java)
        startActivity(intent)
    }
}
