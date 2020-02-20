package com.example.mapapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ExpandableListView
import android.widget.TextView
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_editor.*


class EditorActivity: AppCompatActivity() {

    val fileMap: MutableMap<String, String> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_editor)

        val questionInput: EditText = findViewById(R.id.setQuestionName)
        val answerInput: EditText = findViewById(R.id.setAnswerName)
        val addButton: Button = findViewById(R.id.addButton)
        val valueDisplay: TextView = findViewById(R.id.addedValuesText)


        addButton.setOnClickListener {
            saveInput(questionInput.text.toString(), answerInput.text.toString())
            emptyInputs(questionInput, answerInput)
            val valueString: String = showAddedValue(fileMap)
            valueDisplay.text = valueString}
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

    private fun showAddedValue(value: Map<String, String>): String {
        var text = ""
        value.forEach { (t, u) ->
            text += t
            text = "$text | $u \n"
        }
        return text
    }
}