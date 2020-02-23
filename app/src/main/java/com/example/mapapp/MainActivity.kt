package com.example.mapapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import java.io.File


const val  FILE_NAME = "com.example.mappapp.FILE_NAME"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val categoriesArray: ArrayList<String> = ArrayList()
        val trimmer = arrayListOf(".json", "/")
        val jsonDirectory = applicationContext.filesDir.toString()

        File(applicationContext.filesDir.toString()).walk().forEach {
            println(it)
            if (it.toString() != jsonDirectory)
            categoriesArray.add(it.toString().split(trimmer[0], trimmer[1])[6])
        }

        val listView: ListView = findViewById(R.id.main_listview)
        listView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categoriesArray)

        listView.setOnItemClickListener { adapterview, view, i, l ->
            Toast.makeText(applicationContext, categoriesArray[i], Toast.LENGTH_SHORT).show()
            showSubCategories(categoriesArray[i])
        }
    }

    private fun showSubCategories(category: String) {
        val intent = Intent(this, FileActivity::class.java).apply {
            putExtra(FILE_NAME, category)
        }
        startActivity(intent)
    }
}
