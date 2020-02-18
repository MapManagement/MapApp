package com.example.mapapp

import org.json.JSONObject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_game.*
import org.json.JSONException
import java.io.InputStream

var currentSubCategory: String? = ""
var currentSolution: String = ""
var currentQuestion: String = ""
var availableQuestions: JSONObject = JSONObject("""{"empty": "empty"}""")

class GameActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val fileName = intent.getStringExtra(SUB_CATEGORY_NAME)
        currentSubCategory = fileName

        last_button.setOnClickListener {}
        next_button.setOnClickListener {nextQuestion()}
        solution_button.setOnClickListener {showSolution()}
        restart_button.setOnClickListener{}
        leave_button.setOnClickListener{}
    }

    private fun nextQuestion() {
        val jsonString = parseJSON("data.json")
        if ( currentQuestion.isEmpty()) {
            val category = getQuestions(jsonString, currentSubCategory.toString(), currentCategory.toString())
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

    private fun getQuestions(json: JSONObject, category: String, subject: String): JSONObject {
        val jsonData = json.getJSONObject("data")
        val jsonSubject = jsonData.getJSONObject(subject)
        val jsonCategory = jsonSubject.getJSONObject(category)
        return jsonCategory
    }

    private fun chooseQuestionAndSolution(jsonQAS: JSONObject= availableQuestions) {
        val length = jsonQAS.length()
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
        availableQuestions.remove(currentQuestion)
    }

    private fun endOfCategory() {
        restart_button.visibility = View.VISIBLE
        leave_button.visibility = View.VISIBLE
        currentQuestion = getString(R.string.endOfCategory)
        currentSolution = getString(R.string.endOfCategory)
    }

}
