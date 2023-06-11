package com.example.vought.category.adapter

import android.content.Context
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
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var nameEvent: TextView
        var description: TextView

        init {
            nameEvent = itemView.nameEvent
            description = itemView.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.fragment_category, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        holder.nameEvent.text = listaEvento[position].nameEvent
        holder.description.text = listaEvento[position].description
    }

    override fun getItemCount(): Int {
        return listaEvento.size
    }

}