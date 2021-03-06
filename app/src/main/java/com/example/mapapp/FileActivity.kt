package com.example.mapapp

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
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
        val deleteButton: Button = findViewById(R.id.file_delete_button)
        val fileInformationListView: ListView = findViewById(R.id.file_information_listview)

        //creating ListView
        fileInformationListView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fileInformationArray)

        //listeners
        startButton.setOnClickListener { openFile() }
        editButton.setOnClickListener { editFile() }
        deleteButton.setOnClickListener { deleteFile() }


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

    private fun deleteFile() {
        val alertButton = AlertDialog.Builder(this)
        alertButton.setMessage("Do you really want to delete this file?")
            .setCancelable(false)
            .setPositiveButton("Delete", DialogInterface.OnClickListener { dialog, which ->
                val jsonFile = File(applicationContext.filesDir, "$chosenFile.json")
                jsonFile.delete()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                val intent = Intent(this, FileActivity::class.java)
                startActivity(intent)
            })
        val alert = alertButton.create()
        alert.setTitle("Deleting File")
        alert.show()
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}