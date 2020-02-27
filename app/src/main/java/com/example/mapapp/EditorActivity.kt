package com.example.mapapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlinx.android.synthetic.main.activity_editor.*
import org.json.JSONObject
import java.io.File
import java.io.InputStream
import java.util.*


class EditorActivity: AppCompatActivity() {

    private val fileMap: MutableMap<String, String> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)

        //all items used in EditorActivity
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
            valueDisplay.text = valueString
        }

        submitButton.setOnClickListener {
            createJSONFile(titleInput.text.toString())}
    }

    //stores question and answer in MutableMap, disables EditText of file title
    private fun saveInput(question: String, answer: String) {
        fileMap[question] = answer
        setFileTitle.isEnabled = false
    }

    //clears EditTexts
    private fun emptyInputs(vararg inputs: EditText) {
        for (input in inputs) {
            input.setText("")
        }
    }

    //shows already added values in TextView
    private fun showAddedValue(mapEntries: Map<String, String>): String {
        var text = ""
        mapEntries.forEach { (t, u) ->
            text += t
            text = "$text | $u \n"
        }
        return text
    }

    //creates new json file with value of MutableMap
    private fun createJSONFile(fileName: String) {
        val trimmer = arrayListOf(".json", "/")
        var fileExists = false

        //checks if json file already exists
        File(applicationContext.filesDir.toString()).walk().forEach {
            val splittedFilePaths = it.toString().split(trimmer[0], trimmer[1])
            splittedFilePaths.forEach {
                if (it.toLowerCase(Locale.GERMANY) == fileName.toLowerCase(Locale.GERMANY)) {
                    Toast.makeText(this, "File already exists!", Toast.LENGTH_SHORT).show()
                    fileExists = true

                }
                else{
                    fileExists = false
                }
            }
        }
        //creates file, if file not already found
        if(!(fileExists)) {
            File(applicationContext.filesDir, "$fileName.json").writeText(createFileEntries().toString())
        }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    //turns MutableMap into JSONObject
    private fun createFileEntries(): JSONObject {
        val createdJSONObject = JSONObject()
        fileMap.forEach { (t, u) ->
            createdJSONObject.put(t, u)
        }
        return createdJSONObject
    }
}