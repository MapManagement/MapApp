package com.example.mapapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity

var currentSubCategory: String? = ""
const val  FILE_NAME = "com.example.mappapp.FILE_NAME"

class FileActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file)

        val categoryName = intent.getStringExtra(CATEGORY_NAME)
        currentSubCategory = categoryName

        val gridView: GridView = findViewById(R.id.file_gridview)
        val files = arrayOf("Cytologie", "Enzymatik", "Immunbiologie")
        gridView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, files)

        gridView.setOnItemClickListener { parent, view, position, id ->
            openFile(files[position])
        }
    }

    private fun openFile(fileName: String) {
       val intent = Intent(this, GameActivity::class.java).apply {
           putExtra(FILE_NAME, fileName)
       }
        startActivity(intent)
    }
}