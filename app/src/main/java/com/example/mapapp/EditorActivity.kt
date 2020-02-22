package com.example.mapapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlinx.android.synthetic.main.activity_editor.*
import org.json.JSONObject
import java.io.File
import java.io.FileWriter
import java.io.InputStream
import java.lang.Exception


class EditorActivity: AppCompatActivity() {

    val fileMap: MutableMap<String, String> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_editor)

        val questionInput: EditText = findViewById(R.id.setQuestionName)
        val answerInput: EditText = findViewById(R.id.setAnswerName)
        val titleInput: EditText = findViewById(R.id.setFileTitle)
        val addButton: Button = findViewById(R.id.addButton)
        val valueDisplay: TextView = findViewById(R.id.addedValuesText)
        val submitButton: Button = findViewById(R.id.submitButton)


        addButton.setOnClickListener {
            saveInput(questionInput.text.toString(), answerInput.text.toString())
            emptyInputs(questionInput, answerInput)
            val valueString: String = showAddedValue(fileMap)
            valueDisplay.text = valueString}

        submitButton.setOnClickListener {
            val fullJSONFile =  parseJSON("data.json")
            createJSONFile(fullJSONFile, setFileTitle.text.toString())}
    }

    private fun saveInput(question: String, answer: String) {
        fileMap[question] = answer
        setFileTitle.isEnabled = false
    }

    private fun emptyInputs(vararg inputs: EditText) {
        for (input in inputs) {
            input.setText("")
        }
    }

    private fun showAddedValue(mapEntries: Map<String, String>): String {
        var text = ""
        mapEntries.forEach { (t, u) ->
            text += t
            text = "$text | $u \n"
        }
        return text
    }

    private fun createJSONFile(jsonCategory: JSONObject, fileName: String) {
        File(applicationContext.filesDir, "$fileName.json").writeText(createFileEntries().toString())
        val newFile = File(applicationContext.filesDir, "$fileName.json").readText(Charsets.UTF_8)
        println(newFile)
    }

    private fun createFileEntries(): JSONObject {
        val createdJSONObject = JSONObject()
        fileMap.forEach { (t, u) ->
            createdJSONObject.put(t, u)
        }
        return createdJSONObject
    }

    fun parseJSON(file: String): JSONObject {
        val inputStream: InputStream = assets.open(file)
        val json = inputStream.bufferedReader().use { it.readText() }
        val jsonObj = JSONObject(json)
        return jsonObj
    }
}