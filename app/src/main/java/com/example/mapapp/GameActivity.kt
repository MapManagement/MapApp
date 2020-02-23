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

        //creating listeners
        last_button.setOnClickListener {}
        next_button.setOnClickListener {nextQuestion()}
        solution_button.setOnClickListener {showSolution()}
        restart_button.setOnClickListener{}
        leave_button.setOnClickListener{}
    }

    //decides whether a new game was started
    private fun nextQuestion() {
        if (currentQuestion.isEmpty()) {
            chooseQuestionAndSolution(currentJSONEntries)
        }
        else {
            chooseQuestionAndSolution()
        }

        mytext.text = currentQuestion
    }

    //changes text of text view when solution button is pressed
    private fun showSolution() {
        if (currentSolution.isNotEmpty()) {
            mytext.text = currentSolution
            deleteEntries()
        }
    }

    //chooses question/answer of still available entries and stores them in public variables
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

    //pops answered question out of available ones
    private fun deleteEntries() {
        availableQuestions.remove(currentQuestion)
    }

    //sets endscreen of game
    private fun endOfCategory() {
        restart_button.visibility = View.VISIBLE
        leave_button.visibility = View.VISIBLE
        currentQuestion = getString(R.string.endOfCategory)
        currentSolution = getString(R.string.endOfCategory)
    }

}
