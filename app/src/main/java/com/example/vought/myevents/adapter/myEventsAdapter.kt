package com.example.vought.myevents.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vought.R
import com.example.vought.model.Event
import kotlinx.android.synthetic.main.fragment_myevents.view.*

class myEventsAdapter (val context: Context, val listaEvento: List<Event>): RecyclerView.Adapter<myEventsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var idEvent: TextView
        var nameEvent: TextView

        init {
            idEvent = itemView.idEvent
            nameEvent = itemView.nameEvent
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myEventsAdapter.ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.fragment_myevents, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: myEventsAdapter.ViewHolder, position: Int) {
        holder.idEvent.text = listaEvento[position].idEvent.toString()
        holder.nameEvent.text = listaEvento[position].nameEvent
    }

    override fun getItemCount(): Int {
        return listaEvento.size
    }

}