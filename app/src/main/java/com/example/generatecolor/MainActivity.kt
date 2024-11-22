package com.example.generatecolor

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var colorAdapter: ColorAdapter
    private val colorsList = mutableListOf<Pair<String, Int>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        colorAdapter = ColorAdapter(colorsList) { position ->
            if (position in colorsList.indices) {
                colorsList.removeAt(position)
                colorAdapter.notifyItemRemoved(position)
                colorAdapter.notifyItemRangeChanged(position, colorsList.size)
            } else {
                Toast.makeText(this, "Error: Invalid position", Toast.LENGTH_SHORT).show()
            }
        }

        recyclerView.adapter = colorAdapter

        val generateButton: Button = findViewById(R.id.generateButton)
        generateButton.setOnClickListener {
            val (hexColor, randomColor) = generateColor()
            colorsList.add(Pair(hexColor, randomColor))
            colorAdapter.notifyItemInserted(colorsList.size - 1)
            recyclerView.scrollToPosition(colorsList.size - 1)      // К концу списка
        }
    }

    private fun generateColor(): Pair<String, Int> {
        val randomColor = Color.rgb(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)) // Рандомим red blue green
        val hexColor = String.format("#%06X", 0xFFFFFF and randomColor)     // Переводим в hex
        return Pair(hexColor, randomColor)
    }
}