package com.example.vought.model

class DataSource {

    companion object{
        fun createdDataSet() : ArrayList<Event>{
            val list = ArrayList<Event>()
            list.add(
                Event(
                    "02915000",
                    "Evento teste",
                    "Trap",
                    "Hip hop",
                    "Rua Travis Scott, 21",
                    "São Paulo",
                    "SP",
                )
            )
            return list
        }
    }
}