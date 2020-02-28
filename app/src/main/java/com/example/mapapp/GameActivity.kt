package com.example.mapapp

import android.content.Intent
import org.json.JSONObject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.view.MotionEventCompat
import kotlinx.android.synthetic.main.activity_game.*
import org.json.JSONException
import java.io.File
import java.io.InputStream
import kotlin.math.abs

var currentJSONEntries = JSONObject()
var currentSolution: String = ""
var currentQuestion: String = ""
var availableQuestions: JSONObject = JSONObject("""{"empty": "empty"}""")

class GameActivity: AppCompatActivity(), GestureDetector.OnGestureListener {

    lateinit var gestureListener: GestureDetector
    var x_start: Float = 0.0f
    var x_end: Float = 0.0f

    companion object{
        const val MIN_DISTANCE = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        //storing json file data in different variables
        val json = parseJSON(chosenFile)
        currentJSONEntries = json

        //creating listeners
        gestureListener = GestureDetector(this, this)
        solution_button.setOnClickListener {showSolution()}
        restart_button.setOnClickListener{}
        leave_button.setOnClickListener{}
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureListener.onTouchEvent(event)

        when (event?.action) {
            0 ->
            {
                x_start = event.x
            }
            1 ->
            {
                x_end = event.x
                val distanceFloat: Float = x_end- x_start
                if (abs(distanceFloat) > MIN_DISTANCE) {
                    if (x_end > x_start){
                        println("Right")
                    }
                    else {
                        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_out)
                        mytext.startAnimation(animation)
                        nextQuestion()
                    }

                }
            }

        }
        return super.onTouchEvent(event)
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

    override fun onShowPress(e: MotionEvent?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return false
    }

    override fun onDown(e: MotionEvent?): Boolean {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return false
    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return false
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return false
    }

    override fun onLongPress(e: MotionEvent?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
