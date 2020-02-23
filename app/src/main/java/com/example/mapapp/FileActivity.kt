package com.example.mapapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.InputStream

var currentJSONEntries = JSONObject()

class FileActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file)

        val json = parseJSON("$FILE_NAME.json")
        val entries = json.getJSONObject("data")
        currentJSONEntries = entries
        openFile()
    }

    private fun openFile() {
       val intent = Intent(this, GameActivity::class.java).apply {
       }

        currentSolution = ""
        currentQuestion = ""
        currentSubCategory = ""
        availableQuestions = JSONObject("""{"empty": "empty"}""")
        startActivity(intent)
    }

    fun parseJSON(file: String): JSONObject {
        val inputStream: InputStream = assets.open(file)
        val json = inputStream.bufferedReader().use { it.readText() }
        val jsonObj = JSONObject(json)
        return jsonObj
    }
}