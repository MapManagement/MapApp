package com.example.mapapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_file.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.InputStream


const val  CATEGORY_NAME = "com.example.mappapp.CATEGORY_NAME"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val json = parseJSON("data.json")
        val rawJSOn = json.getJSONObject("data")
        val categories = rawJSOn.keys()
        var categoriesArray: ArrayList<String> = ArrayList()
        categories.forEach { categoriesArray.add(it) }

        val listView: ListView = findViewById(R.id.main_listview)
        listView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categoriesArray)

        listView.setOnItemClickListener { adapterview, view, i, l ->
            Toast.makeText(applicationContext, categoriesArray[i], Toast.LENGTH_SHORT).show()
            showSubCategories(categoriesArray[i])
        }

        main_floating_point.setOnClickListener {openFileActivity()}
    }

    private fun showSubCategories(category: String) {
        val intent = Intent(this, FileActivity::class.java).apply {
            putExtra(CATEGORY_NAME, category)
        }
        startActivity(intent)
    }

    private fun openFileActivity() {
        val intent = Intent(this, EditorActivity::class.java)
        startActivity(intent)
    }

    fun parseJSON(file: String): JSONObject {
        val inputStream: InputStream = assets.open(file)
        val json = inputStream.bufferedReader().use { it.readText() }
        val jsonObj = JSONObject(json)
        return jsonObj
    }
}
