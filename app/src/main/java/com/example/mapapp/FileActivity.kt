package com.example.mapapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.File


class FileActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file)

        //array containing all information about chosenFile
        val file = File(applicationContext.filesDir, "$chosenFile.json")
        val fileInformationArray =
            arrayListOf("Bytes: " + file.length().toString(), "Path: " + file.path,"Extension: " + file.extension)

        //declare variables for elements in xml file
        val fileNameTextView: TextView = findViewById(R.id.filename_textview)
        val startButton: Button = findViewById(R.id.start_game_button)
        val editButton: Button = findViewById(R.id.edit_file_button)
        val fileInformationListView: ListView = findViewById(R.id.file_information_listview)

        //creating ListView
        fileInformationListView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fileInformationArray)

        //listeners
        startButton.setOnClickListener { openFile() }
        editButton.setOnClickListener { editFile() }


        //set textview text
        fileNameTextView.text = chosenFile

    }

    private fun openFile() {
       val intent = Intent(this, GameActivity::class.java).apply {
       }
        //clearing data of previous played file
        currentSolution = ""
        currentQuestion = ""
        availableQuestions = JSONObject("""{"empty": "empty"}""")
        startActivity(intent)
    }

    private fun editFile() {
        val intent = Intent(this, FileEditorActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}