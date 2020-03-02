package com.example.mapapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.json.JSONObject
import java.io.File
import java.lang.Exception


class EntryEditorActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry_editor)

        //elements in xml file
        val entryName: TextView = findViewById(R.id.entry_editor_filename)
        val questionEdit: EditText = findViewById(R.id.entry_question_edittext)
        val answerEdit: EditText = findViewById(R.id.entry_answer_edittext)
        val changeButton: Button = findViewById(R.id.entry_save_changes_button)
        val deleteButton: Button = findViewById(R.id.entry_delete_button)

        val newQuestion = "New Question"
        val newAnswer = "New Answer"

        //sets entry name and question/answer
        entryName.text = chosenFile
        if (chosenEntryKey != "newEntryKey") {
            questionEdit.setText(chosenEntryKey)
            answerEdit.setText(parseJSON(chosenFile).getString(chosenEntryKey).toString())
        }
        else {
            questionEdit.setText(newQuestion)
            answerEdit.setText(newAnswer)
        }
            //save changes to file (listener)
            changeButton.setOnClickListener { saveChangesToFile(questionEdit.text.toString(), answerEdit.text.toString()) }

            //deletes entry
            deleteButton.setOnClickListener { deleteEntry() }
    }

    fun saveChangesToFile(newQuestion: String, newAnswer: String) {
        val jsonObj = parseJSON(chosenFile)
        if (newQuestion != "New Question" && newQuestion != "") {
            jsonObj.remove(chosenEntryKey)
        }
        else {
            Toast.makeText(this, "Not possible to use empty string!", Toast.LENGTH_SHORT).show()
            return
        }
        try {
            jsonObj.put(newQuestion, newAnswer)
        }
        catch (e: Exception) {Toast.makeText(this, "Entry already exists!", Toast.LENGTH_SHORT).show()}
        val file: File = File(applicationContext.filesDir, "$chosenFile.json")
        file.writeText(jsonObj.toString())
        val intent = Intent(this, FileEditorActivity::class.java)
        startActivity(intent)

    }

    fun deleteEntry() {
        val jsonObj = parseJSON(chosenFile)
        jsonObj.remove(chosenEntryKey)
        val file: File = File(applicationContext.filesDir, "$chosenFile.json")
        file.writeText(jsonObj.toString())
        val intent = Intent(this, FileEditorActivity::class.java)
        startActivity(intent)
    }

    fun parseJSON(file: String): JSONObject {
        val jsonData = File(applicationContext.filesDir, "$file.json").readText(Charsets.UTF_8)
        val jsonObj = JSONObject(jsonData)
        return jsonObj
    }

    override fun onBackPressed() {
        val intent = Intent(this, FileEditorActivity::class.java)
        startActivity(intent)
    }
}
