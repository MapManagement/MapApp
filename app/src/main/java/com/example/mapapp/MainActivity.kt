package com.example.mapapp

import org.json.JSONObject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import java.io.InputStream

var currentSolution: String = ""
var currentQuestion: String = ""
var availableQuestions: JSONObject = JSONObject("""{"empty": "empty"}""")

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        last_button.setOnClickListener {parseJSON("data.json")}
        next_button.setOnClickListener {nextQuestion()}
        solution_button.setOnClickListener {showSolution()}
        restart_button.setOnClickListener{startingGame()}
        leave_button.setOnClickListener{}
    }

    private fun startingGame() {
        last_button.visibility = View.VISIBLE
        next_button.visibility = View.VISIBLE
        solution_button.visibility = View.VISIBLE
        mytext.text= getString(R.string.toFirstQuestion)
    }

    private fun nextQuestion() {
        val jsonString = parseJSON("data.json")
        if ( currentQuestion.isEmpty()) {
            val category = getQuestions(jsonString, "biology", "cell_to_organ")
            chooseQuestionAndSolution(category)
        }
        else {
            chooseQuestionAndSolution()
        }

        mytext.text = currentQuestion
    }

    private fun showSolution() {
        if (currentSolution.isNotEmpty()) {
            mytext.text = currentSolution
            deleteEntries()
        }
    }

    fun parseJSON(file: String): JSONObject {
        val inputStream: InputStream = assets.open(file)
        val json = inputStream.bufferedReader().use { it.readText() }
        val jsonObj = JSONObject(json)
        return jsonObj
    }

    private fun getQuestions(json: JSONObject, subject: String, category: String): JSONObject {
        val jsonData = json.getJSONObject("data")
        val jsonSubject = jsonData.getJSONObject(subject)
        val jsonCategory = jsonSubject.getJSONObject(category)
        return jsonCategory
    }

    private fun chooseQuestionAndSolution(jsonQAS: JSONObject= availableQuestions) {
        val length = jsonQAS.length()
        println(length)
        if (length != 1) {
            val randInt = (0 until length - 1).random()
            val questions = jsonQAS.keys()
            var chosenQuestion = "Error"
            var i = 0

            for (element in questions) {
                if (i == randInt && element != "Error") {
                    chosenQuestion = element
                    break
                }
                else {
                    i += 1
                }
            }

            val chosenSolution = jsonQAS.getString(chosenQuestion)
            currentQuestion = chosenQuestion
            currentSolution = chosenSolution

            try {
                availableQuestions.getString("empty")
                availableQuestions = jsonQAS
            } catch (e: JSONException) { }
        }

        else {
            endOfCategory()
        }
    }

    private fun deleteEntries() {
        println(availableQuestions)
        availableQuestions.remove(currentQuestion)
        println(availableQuestions)
    }

    private fun endOfCategory() {
        restart_button.visibility = View.VISIBLE
        leave_button.visibility = View.VISIBLE
        currentQuestion = getString(R.string.endOfCategory)
        currentSolution = getString(R.string.endOfCategory)
    }

}
