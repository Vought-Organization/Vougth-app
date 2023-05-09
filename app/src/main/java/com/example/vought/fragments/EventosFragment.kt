package com.example.vought.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vought.R
import com.example.vought.model.Evento

class EventosFragment : Fragment(R.layout.fragment_eventos) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity?.baseContext)
        val listaDeEventos = listOf(
            Evento(
                "EVENTO 1", "https://www.petz.com.br/blog/wp-content/uploads/2020/04/meu-primeiro-gato.jpg", "Hadock Lobo 595", 0.3
            )
        )
        recyclerView.adapter = EventosAdapter(listaDeEventos)
    }

    inner class EventosAdapter(
        private val list: List<Evento>
    ) : RecyclerView.Adapter<EventosAdapter.EventosHolder>() {

        inner class EventosHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(evento: Evento) {
                itemView.findViewById<TextView>(R.id.evento_titulo).text = evento.titulo

                Glide
                    .with(activity!!.baseContext)
                    .load(evento.imagem)
                    .into(itemView.findViewById(R.id.evento_imagem))
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventosHolder {
            val card =
                LayoutInflater.from(parent.context).inflate(R.layout.res_evento_card, parent, false)
            return EventosHolder(card)
        }

        override fun getItemCount(): Int = list.size

        override fun onBindViewHolder(holder: EventosHolder, position: Int) {
            holder.bind(list[position])
        }

    }

}