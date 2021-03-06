package com.example.mapapp

import android.content.Intent
import org.json.JSONObject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_game.*
import org.json.JSONException
import java.io.File
import kotlin.math.abs

var currentJSONEntries = JSONObject()
var currentSolution: String = ""
var currentQuestion: String = ""
var lastSolution: String = ""
var lastQuestion: String = ""

var availableQuestions: JSONObject = JSONObject("""{"empty": "empty"}""")

class GameActivity: AppCompatActivity(), GestureDetector.OnGestureListener {

    lateinit var gestureListener: GestureDetector
    var x_start: Float = 0.0f
    var x_end: Float = 0.0f
    var y_start: Float = 0.0f
    var y_end: Float = 0.0f

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
        restart_button.setOnClickListener{restartGame()}
        leave_button.setOnClickListener{leaveGame()}
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureListener.onTouchEvent(event)

        when (event?.action) {
            0 ->
            {
                x_start = event.x
                y_start = event.y
            }
            1 ->
            {
                x_end = event.x
                y_end = event.y

                val distanceFloatX: Float = x_end- x_start
                val distanceFloatY: Float = y_end - y_start

                if (abs(distanceFloatX) > MIN_DISTANCE) {
                    if (x_end <= x_start){
                        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_out_right)
                        mytext.startAnimation(animation)
                        nextQuestion()
                    }
                    else if (x_end > x_start){
                        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_out_left)
                        mytext.startAnimation(animation)
                        lastQuestion()
                    }
                }

                else if (abs(distanceFloatY) > MIN_DISTANCE) {
                    if (y_end > y_start) {
                        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_out_from_top)
                        mytext.startAnimation(animation)
                        showSolution()
                    }
                }
            }

        }
        return super.onTouchEvent(event)
    }

    //shows previous question/solution (can only go back by one step)
    private fun lastQuestion() {
        if (currentQuestion.isEmpty()) {
            Toast.makeText(this, "Not possible now!", Toast.LENGTH_SHORT).show()
        }
        else {
            mytext.text = lastQuestion
        }
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
            if (mytext.text == lastQuestion || mytext.text == lastSolution)
            {
                mytext.text = lastSolution
                deleteEntries(lastQuestion)
            }
            else {
                mytext.text = currentSolution
                deleteEntries(currentQuestion)
            }
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
            lastQuestion = currentQuestion
            lastSolution = currentSolution
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
    private fun deleteEntries(question: String) {
        availableQuestions.remove(question)
    }

    //sets endscreen of game
    private fun endOfCategory() {
        restart_button.visibility = View.VISIBLE
        leave_button.visibility = View.VISIBLE
        currentQuestion = getString(R.string.endOfCategory)
        currentSolution = getString(R.string.endOfCategory)
    }

    private fun restartGame() {
        currentJSONEntries = JSONObject()
        currentSolution = ""
        currentQuestion = ""
        availableQuestions = JSONObject("""{"empty": "empty"}""")

        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    private fun leaveGame() {
        val intent = Intent(this, FileActivity::class.java)
        startActivity(intent)
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
