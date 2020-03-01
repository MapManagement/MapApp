package com.example.mapapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
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

        val FAB: FloatingActionButton = findViewById(R.id.floating_point)

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

        FAB.setOnClickListener {startEditorActivity()}
    }

    //starts FileActivity for parsing json data
    private fun startFileActivity(category: String) {
        chosenFile = category
        val intent = Intent(this, FileActivity::class.java).apply {
        }
        startActivity(intent)
    }

    private fun startEditorActivity() {
        val intent = Intent(this, EditorActivity::class.java)
        startActivityForResult(Intent.createChooser(intent, "Select a file!"), 1803)
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
