package com.example.mapapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File

//global varibale for getting chosen file name
const val  FILE_NAME = "com.example.mappapp.FILE_NAME"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //prerequisites for creating ListView
        val categoriesArray: ArrayList<String> = ArrayList()
        val trimmer = arrayListOf(".json", "/")
        val jsonDirectory = applicationContext.filesDir.toString()

        val FAB: FloatingActionButton = findViewById(R.id.floating_point)

        //using all stored json files for ListView items
        File(applicationContext.filesDir.toString()).walk().forEach {
            println(it)
            if (it.toString() != jsonDirectory)
            categoriesArray.add(it.toString().split(trimmer[0], trimmer[1])[6])
        }

        //creating ListView
        val listView: ListView = findViewById(R.id.main_listview)
        listView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categoriesArray)

        //creating listeners for ListView items
        listView.setOnItemClickListener { adapterview, view, i, l ->
            Toast.makeText(applicationContext, categoriesArray[i], Toast.LENGTH_SHORT).show()
            startFileActivity(categoriesArray[i])
        }

        FAB.setOnClickListener {startEditorActivity()}
    }

    //starts FileActivity for parsing json data
    private fun startFileActivity(category: String) {
        val intent = Intent(this, FileActivity::class.java).apply {
            putExtra(FILE_NAME, category)
        }
        startActivity(intent)
    }

    private fun startEditorActivity() {
        val intent = Intent(this, EditorActivity::class.java)
        startActivity(intent)
    }
}
