package com.example.vought.model

import com.example.vought.R

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
        fun createdCategorySet() : ArrayList<CategoryEvent>{
            val list = ArrayList<CategoryEvent>()
            list.add(
                CategoryEvent(
                    "Evento 1 ",
                    R.drawable.festas
                )
            )
            list.add(
                CategoryEvent(
                    "Evento 1 ",
                    R.drawable.festas
                )
            )
            list.add(
                CategoryEvent(
                    "Evento 1 ",
                    R.drawable.festas
                )
            )
            list.add(
                CategoryEvent(
                    "Evento 1 ",
                    R.drawable.festas
                )
            )
            list.add(
                CategoryEvent(
                    "Evento 1 ",
                    R.drawable.festas
                )
            )
            list.add(
                CategoryEvent(
                    "Evento 1 ",
                    R.drawable.festas
                )
            )
            return list
        }
    }
}