package com.example.mapapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.InputStream

class EditorActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
    }
}