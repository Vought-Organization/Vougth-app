package com.example.vought.category.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vought.R

import com.example.vought.model.Event
import kotlinx.android.synthetic.main.fragment_category.view.*
import kotlinx.android.synthetic.main.fragment_myevents.view.nameEvent

class CategoryAdapter (val context: Context, val listaEvento: List<Event>): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private val categoriesIcons = mapOf(
        "show" to "festas",
        "palestra" to "congresso",
        "teatro" to "teatro",
        "passeios" to "brincadeira",
        "congressos" to "computador",
        "infantil" to "brincadeira",
        "standup" to "standup"
    )
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var nameEvent: TextView
        var description: TextView
        var imagemEvento: ImageView

        init {
            nameEvent = itemView.nameEvent
            description = itemView.description
            imagemEvento = itemView.imagemEvento
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.fragment_category, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        val evento = listaEvento[position]

        holder.nameEvent.text = listaEvento[position].nameEvent
        holder.description.text = listaEvento[position].description

        val categoria = evento.category

        if (categoriesIcons.containsKey(categoria)) {
            val iconFileName = categoriesIcons[categoria]
            val iconResourceId = context.resources.getIdentifier(iconFileName, "drawable", context.packageName)
            if (iconResourceId != 0) {
                holder.imagemEvento.setImageResource(iconResourceId)
                Log.d("CategoryAdapter", "Resource not found for category: $categoria")
            } else {
                holder.imagemEvento.setImageResource(R.drawable.congresso)
                Log.d("CategoryAdapter", "Resource not found for category: $categoria") // Imagem padrão em caso de falha em encontrar a imagem específica
            }
        } else {
            holder.imagemEvento.setImageResource(R.drawable.congresso)
            Log.d("CategoryAdapter", "Erro not found for category: $categoria") // Imagem padrão se a categoria não estiver no mapa
        }
    }

    override fun getItemCount(): Int {
        return listaEvento.size
    }

}