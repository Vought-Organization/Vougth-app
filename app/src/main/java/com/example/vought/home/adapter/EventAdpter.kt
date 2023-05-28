package com.example.vought.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vought.R
import com.example.vought.model.Event
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.resr_item_event.view.*

class EventAdpter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var items : List<Event> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EventViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.resr_item_event, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is EventViewHolder ->{
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setDataSet(events: List<Event>){
        this.items = events
    }

    class EventViewHolder constructor(
        itemView : View
    ): RecyclerView.ViewHolder(itemView){
        private val eventTitle = itemView.txt_name_event
        private val eventDescription = itemView.txt_descripition_event
        private val eventImage = itemView.iv_item_event


        fun bind(event: Event){
            eventTitle.text = event.nameEvent
            eventDescription.text = event.description
            Picasso.get().load(event.photoProfile).into(eventImage)
        }
    }

}