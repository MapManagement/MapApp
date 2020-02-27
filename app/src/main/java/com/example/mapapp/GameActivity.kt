package com.example.mapapp

import android.content.Intent
import org.json.JSONObject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_game.*
import org.json.JSONException
import java.io.File
import java.io.InputStream

var currentJSONEntries = JSONObject()
var currentSolution: String = ""
var currentQuestion: String = ""
var availableQuestions: JSONObject = JSONObject("""{"empty": "empty"}""")

class GameActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        //storing json file data in different variables
        val json = parseJSON(chosenFile)
        currentJSONEntries = json
        println(currentJSONEntries)

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
        println("Next Button $jsonQAS")
        val length = jsonQAS.length()
        if (length != 0) {
            val randInt = (0 until length).random()
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
        println(availableQuestions)
        currentQuestion = getString(R.string.endOfCategory)
        currentSolution = getString(R.string.endOfCategory)
    }

    //creates json object of file text
    fun parseJSON(file: String): JSONObject {
        val jsonData = File(applicationContext.filesDir, "$file.json").readText(Charsets.UTF_8)
        val jsonObj = JSONObject(jsonData)
        return jsonObj
    }

    override fun onBackPressed() {
        val intent = Intent(this, FileActivity::class.java)
        startActivity(intent)
    }

}
