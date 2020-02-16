package com.example.mapapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

val categories = GameActivity().parseJSON("data.json")

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView: ListView = findViewById(R.id.main_listview)
        val subjects = arrayOf("Biology", "Math", "Physics")
        listView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, subjects)

        listView.setOnItemClickListener { adapterview, view, i, l ->
            Toast.makeText(applicationContext, "Deine Antwort lautet " + subjects[i], Toast.LENGTH_SHORT).show()
        }
    }
}
