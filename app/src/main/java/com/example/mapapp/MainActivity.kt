package com.example.mapapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast


const val  CATEGORY_NAME = "com.example.mappapp.CATEGORY_NAME"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView: ListView = findViewById(R.id.main_listview)
        val subjects = arrayOf("biology", "math", "physics")
        listView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, subjects)

        listView.setOnItemClickListener { adapterview, view, i, l ->
            Toast.makeText(applicationContext, subjects[i], Toast.LENGTH_SHORT).show()
            showSubCategories(subjects[i])
        }
    }

    private fun showSubCategories(category: String) {
        val intent = Intent(this, FileActivity::class.java).apply {
            putExtra(CATEGORY_NAME, category)
        }
        startActivity(intent)
    }
}
