package com.example.mapapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

//global varibale for getting chosen file name
var chosenFile: String = ""

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //prerequisites for creating ListView
        val categoriesArray: ArrayList<String> = ArrayList()
        val trimmer = arrayListOf(".json", "/")
        val jsonDirectory = applicationContext.filesDir.toString()

        val openFAB: FloatingActionButton = findViewById(R.id.floating_point)
        val createFAB: FloatingActionButton = findViewById(R.id.floating_point_create)
        val importFAB: FloatingActionButton = findViewById(R.id.floating_point_import)

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

        openFAB.setOnClickListener {showFABS()}
        importFAB.setOnClickListener { openFileManager() }
        createFAB.setOnClickListener { startEditorActivity() }
    }

    //starts FileActivity for parsing json dataü
    private fun startFileActivity(category: String) {
        chosenFile = category
        val intent = Intent(this, FileActivity::class.java).apply {
        }
        startActivity(intent)
    }

    @SuppressLint("RestrictedApi")
    private fun showFABS() {
        if(floating_point_create.visibility != View.VISIBLE) {
            floating_point_create.visibility = View.VISIBLE
            floating_point_import.visibility = View.VISIBLE
        }
        else {
            floating_point_create.visibility = View.INVISIBLE
            floating_point_import.visibility = View.INVISIBLE
        }
    }

    private fun startEditorActivity() {
        val intent = Intent(this, EditorActivity::class.java)
        startActivity(intent)
    }

    private fun openFileManager() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("*/*")
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1803 && resultCode == Activity.RESULT_OK) {
            val selectedFile = data?.data
        }
    }
}
