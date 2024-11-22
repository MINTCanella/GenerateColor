package com.example.generatecolor

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ColorAdapter(
    private val colors: MutableList<Pair<String, Int>>,     // Пары hex-цвета и его значения
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

    inner class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val colorSquare: View = itemView.findViewById(R.id.colorSquare)
        private val colorName: TextView = itemView.findViewById(R.id.colorName)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)

        fun bind(color: Pair<String, Int>, position: Int) {
            colorName.text = color.first
            colorSquare.setBackgroundColor(color.second)

            colorName.setOnClickListener {      // Копируемый элемент
                copyToClipboard(itemView.context, color.first)
            }

            deleteButton.setOnClickListener {   // Удаление цвета
                try {
                    onDelete(position)
                } catch (e: Exception) {
                    e.printStackTrace()
                    android.widget.Toast.makeText(itemView.context, "Error: ${e.message}", android.widget.Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun copyToClipboard(context: Context, text: String) {   // Копирование hex-а
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Color Name", text)
            clipboard.setPrimaryClip(clip)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_color, parent, false)
        return ColorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(colors[position], position)
    }

    override fun getItemCount(): Int {
        return colors.size
    }
}