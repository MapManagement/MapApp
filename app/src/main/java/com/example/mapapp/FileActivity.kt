package com.example.mapapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.InputStream

var currentCategory: String? = ""
const val  SUB_CATEGORY_NAME = "com.example.mappapp.SUB_CATEGORY_NAME"

class FileActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file)

        val categoryName = intent.getStringExtra(CATEGORY_NAME)
        currentCategory = categoryName

        val json = parseJSON("data.json")
        val gridView: GridView = findViewById(R.id.file_gridview)
        val rawJSOn = json.getJSONObject("data")
        val jsonSubCategory = rawJSOn.getJSONObject(currentCategory.toString())
        val subCategories = jsonSubCategory.keys()
        var subCategoriesArray: ArrayList<String> = ArrayList()
        subCategories.forEach { subCategoriesArray.add(it) }

        gridView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, subCategoriesArray)

        gridView.setOnItemClickListener { parent, view, position, id ->
            openSubCategory(subCategoriesArray[position])
        }
    }

    private fun openSubCategory(subCategoryName: String) {
       val intent = Intent(this, GameActivity::class.java).apply {
           putExtra(SUB_CATEGORY_NAME, subCategoryName)
       }

        currentSolution = ""
        currentQuestion = ""
        currentSubCategory = ""
        availableQuestions = JSONObject("""{"empty": "empty"}""")
        startActivity(intent)
    }

    fun parseJSON(file: String): JSONObject {
        val inputStream: InputStream = assets.open(file)
        val json = inputStream.bufferedReader().use { it.readText() }
        val jsonObj = JSONObject(json)
        return jsonObj
    }
}