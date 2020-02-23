package com.example.mapapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.File

//json entries of chosen file
var currentJSONEntries = JSONObject()

class FileActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file)

        //storing json file data in different variables
        val fileName = intent.getStringExtra(FILE_NAME)
        val json = parseJSON(fileName.toString())
        val entries = json.getJSONObject("data")
        currentJSONEntries = entries

        //starts game
        openFile()
    }

    private fun openFile() {
       val intent = Intent(this, GameActivity::class.java).apply {
       }
        //clearing data of previous played file
        currentSolution = ""
        currentQuestion = ""
        currentSubCategory = ""
        availableQuestions = JSONObject("""{"empty": "empty"}""")
        startActivity(intent)
    }

    //creates json object of file text
    fun parseJSON(file: String): JSONObject {
        val jsonData = File(applicationContext.filesDir, "$file.json").readText(Charsets.UTF_8)
        val jsonObj = JSONObject(jsonData)
        return jsonObj
    }
}