package com.example.vought.model

import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.vought.R
import com.example.vought.home.adapter.EventAdpter
import com.example.vought.rest.Api
import com.example.vought.rest.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DataSource {

    val service: RetrofitService = Api.createService(RetrofitService::class.java)

    fun populateDataSet(
        dataSet: MutableList<Event>,
        adapter: EventAdpter,
        activity: FragmentActivity?
    ) {
        service.getEvent().enqueue(object : Callback<List<Event>>{
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
               if (response.isSuccessful && response.body()!!.isNotEmpty()) {
                    dataSet.clear()
                    dataSet.addAll(response.body()!!)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(activity!!.baseContext, "Deu ruim", Toast.LENGTH_SHORT).show()
            }

        })
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
                R.drawable.teatro
            )
        )
        list.add(
            CategoryEvent(
                "Evento 1 ",
                R.drawable.standup
            )
        )
        list.add(
            CategoryEvent(
                "Evento 1 ",
                R.drawable.congresso
            )
        )
        list.add(
            CategoryEvent(
                "Evento 1 ",
                R.drawable.computador
            )
        )
        list.add(
            CategoryEvent(
                "Evento 1 ",
                R.drawable.brincadeira
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