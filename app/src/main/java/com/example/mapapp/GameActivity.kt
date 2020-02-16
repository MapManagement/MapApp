package com.example.mapapp

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity

class GameActivity: AppCompatActivity() {

    private val mainMethod = MainActivity()
    val categories = mainMethod.parseJSON("data.json").keys()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_game)



    }
}