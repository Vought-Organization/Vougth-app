package com.example.vought.myevents

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vought.R
import com.example.vought.model.Event
import com.example.vought.myevents.adapter.myEventsAdapter
import com.example.vought.rest.Api
import com.example.vought.rest.RetrofitService
import kotlinx.android.synthetic.main.activity_my_events.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyEventsActivity : AppCompatActivity() {

    lateinit var myEventsAdapter: myEventsAdapter
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?)   {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_events)

        recyclerview_events.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerview_events.layoutManager = linearLayoutManager

        getMyData()
    }

    private fun getMyData() {
        val service = Api.createService(RetrofitService::class.java)
        val request = service.getEvent()

        request.enqueue(object : Callback<List<Event>?> {
            override fun onResponse(call: Call<List<Event>?>, response: Response<List<Event>?>) {
                val responseBody = response.body()!!

                myEventsAdapter = myEventsAdapter(baseContext, responseBody.toMutableList())
                myEventsAdapter.notifyDataSetChanged()
                recyclerview_events.adapter = myEventsAdapter

            }

            override fun onFailure(call: Call<List<Event>?>, t: Throwable) {
                d("MyEventsActivity", "onFailure: " + t.message)
            }
        })
    }
}