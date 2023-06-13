package com.example.vought.myevents.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.vought.R
import com.example.vought.edit.EditActivity
import com.example.vought.model.Event
import com.example.vought.rest.Api
import com.example.vought.rest.RetrofitService
import kotlinx.android.synthetic.main.fragment_myevents.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class myEventsAdapter (val context: Context, val listaEvento: MutableList<Event>) : RecyclerView.Adapter<myEventsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var idEvent: TextView
        var nameEvent: TextView
        var deleteImg: ImageView
        var editImg: ImageView

        init {
            idEvent = itemView.idEvent
            nameEvent = itemView.nameEvent
            deleteImg = itemView.DeletarEvento
            editImg = itemView.EditarEvento

            deleteImg.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val adapter = getParentAdapter() as myEventsAdapter
                    adapter.deleteEvent(position)
                }
            }


        }

        private fun getParentAdapter(): RecyclerView.Adapter<*>? {
            var parent: ViewParent? = itemView.parent
            while (parent != null && parent !is RecyclerView) {
                parent = parent.parent
            }
            return if (parent is RecyclerView) {
                parent.adapter
            } else {
                null
            }
        }
    }

    fun deleteEvent(position: Int) {
        val event = listaEvento[position].idEvent
        listaEvento.removeAt(position)
        notifyItemRemoved(position)
        Log.d("MyEventsActivity", "Failed to delete event. Error: $position")

        val service = Api.createService(RetrofitService::class.java)
        val request = service.deleteEvent(event)

        request.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    val message = "Evento deletado com sucesso!"
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    Log.d("MyEventsActivity", "Deu certo")
                } else {
                    Log.d("MyEventsActivity", "Failed to delete event. Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("MyEventsActivity", "Failed to delete event. Error: ${t.message}")
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.fragment_myevents, parent, false)
        return ViewHolder(itemView)
    }



    override fun onBindViewHolder(holder: myEventsAdapter.ViewHolder, position: Int) {
        holder.nameEvent.text = listaEvento[position].nameEvent

        holder.editImg.setOnClickListener {
            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val event = listaEvento[position].idEvent

                val intent = Intent(context, EditActivity::class.java)
                intent.putExtra("eventId", event)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return listaEvento.size
    }

}