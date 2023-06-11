package com.example.vought.home.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vought.R
import com.example.vought.model.Event
import kotlinx.android.synthetic.main.resr_item_event.view.*

class EventAdpter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private var items : List<Event> = ArrayList()

    companion object {
        private val categoriesIcons = mapOf(
            "show" to R.drawable.festas,
            "palestra" to R.drawable.congresso,
            "teatro" to R.drawable.teatro,
            "passeios" to R.drawable.brincadeira,
            "congressos" to R.drawable.computador,
            "infantil" to R.drawable.brincadeira,
            "standup" to R.drawable.standup
        )
    }

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

            val categoria = event.category

            if (EventAdpter.categoriesIcons.containsKey(categoria)) {
                val iconResourceId = EventAdpter.categoriesIcons[categoria] ?: 0
                if (iconResourceId != 0) {
                    eventImage.setImageResource(iconResourceId)
                }
            }

        }
    }

}