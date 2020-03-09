package com.example.mapapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

//global variable for getting chosen file name
var chosenFile: String = ""

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //prerequisites for creating ListView
        val categoriesArray: ArrayList<String> = ArrayList()
        val trimmer = arrayListOf(".json", "/")
        val jsonDirectory = applicationContext.filesDir.toString()
        println("my dir $jsonDirectory")

        val openFAB: FloatingActionButton = findViewById(R.id.floating_point)
        val createFAB: FloatingActionButton = findViewById(R.id.floating_point_create)
        val reloadFAB: FloatingActionButton = findViewById(R.id.floating_point_reload)

        //using all stored json files for ListView items
        File(applicationContext.filesDir.toString()).walk().forEach {
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
        //FAB listeners
        openFAB.setOnClickListener {showFABS()}
        reloadFAB.setOnClickListener { reloadActivity()}
        createFAB.setOnClickListener { startEditorActivity() }
    }

    //starts FileActivity for parsing json data
    private fun startFileActivity(category: String) {
        chosenFile = category
        val intent = Intent(this, FileActivity::class.java).apply {
        }
        startActivity(intent)
    }

    //when clicking on root FAB new ones will open
    @SuppressLint("RestrictedApi")
    private fun showFABS() {
        if(floating_point_create.visibility != View.VISIBLE) {
            floating_point_create.visibility = View.VISIBLE
            floating_point_reload.visibility = View.VISIBLE
        }
        else {
            floating_point_create.visibility = View.INVISIBLE
            floating_point_reload.visibility = View.INVISIBLE
        }
    }

    @SuppressLint("RestrictedApi")
    private fun reloadActivity() {
        val categoriesArray: ArrayList<String> = ArrayList()
        val trimmer = arrayListOf(".json", "/")
        val jsonDirectory = applicationContext.filesDir.toString()
        println("my dir $jsonDirectory")

        val openFAB: FloatingActionButton = findViewById(R.id.floating_point)
        val createFAB: FloatingActionButton = findViewById(R.id.floating_point_create)
        val reloadFAB: FloatingActionButton = findViewById(R.id.floating_point_reload)

        //using all stored json files for ListView items
        File(applicationContext.filesDir.toString()).walk().forEach {
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

        floating_point_create.visibility = View.INVISIBLE
        floating_point_reload.visibility = View.INVISIBLE
    }

    //directs to new activity of chosen file
    private fun startEditorActivity() {
        val intent = Intent(this, EditorActivity::class.java)
        startActivity(intent)
    }
}
