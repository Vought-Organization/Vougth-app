package com.example.vought.category

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vought.R
import com.example.vought.category.adapter.CategoryAdapter
import com.example.vought.model.Event
import com.example.vought.myevents.adapter.myEventsAdapter
import com.example.vought.rest.Api
import com.example.vought.rest.RetrofitService
import kotlinx.android.synthetic.main.activity_my_events.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryActivity : AppCompatActivity() {
    lateinit var categoryAdapter: CategoryAdapter
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        recyclerview_events.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerview_events.layoutManager = linearLayoutManager

        getMyData()
    }

    private fun getMyData() {
        val categoryTitle = intent.getStringExtra("categoryTitle")
        val service = Api.createService(RetrofitService::class.java)
        if (categoryTitle.equals("Shows")) {
            val request = service.getEventShows()
            request.enqueue(object : Callback<List<Event>?> {
                override fun onResponse(call: Call<List<Event>?>, response: Response<List<Event>?>) {
                    val responseBody = response.body()!!

                    categoryAdapter = CategoryAdapter(baseContext, responseBody)
                    categoryAdapter.notifyDataSetChanged()
                    recyclerview_events.adapter = categoryAdapter

                }

                override fun onFailure(call: Call<List<Event>?>, t: Throwable) {
                    Log.d("MyEventsActivity", "onFailure: " + t.message)
                }
            })
        } else if (categoryTitle.equals("Palestras")) {
            val request = service.getEventPalestras()
            request.enqueue(object : Callback<List<Event>?> {
                override fun onResponse(call: Call<List<Event>?>, response: Response<List<Event>?>) {
                    val responseBody = response.body()!!
                    categoryAdapter = CategoryAdapter(baseContext, responseBody)
                    categoryAdapter.notifyDataSetChanged()
                    recyclerview_events.adapter = categoryAdapter

                }

                override fun onFailure(call: Call<List<Event>?>, t: Throwable) {
                    Log.d("MyEventsActivity", "onFailure: " + t.message)
                }
            })
        } else if (categoryTitle.equals("Teatro")) {
            val request = service.getEventTeatros()
            request.enqueue(object : Callback<List<Event>?> {
                override fun onResponse(call: Call<List<Event>?>, response: Response<List<Event>?>) {
                    val responseBody = response.body()!!

                    categoryAdapter = CategoryAdapter(baseContext, responseBody)
                    categoryAdapter.notifyDataSetChanged()
                    recyclerview_events.adapter = categoryAdapter

                }

                override fun onFailure(call: Call<List<Event>?>, t: Throwable) {
                    Log.d("MyEventsActivity", "onFailure: " + t.message)
                }
            })
        }

        else if (categoryTitle.equals("Passeios")) {
            val request = service.getEventPasseios()
            request.enqueue(object : Callback<List<Event>?> {
                override fun onResponse(call: Call<List<Event>?>, response: Response<List<Event>?>) {
                    val responseBody = response.body()!!

                    categoryAdapter = CategoryAdapter(baseContext, responseBody)
                    categoryAdapter.notifyDataSetChanged()
                    recyclerview_events.adapter = categoryAdapter

                }

                override fun onFailure(call: Call<List<Event>?>, t: Throwable) {
                    Log.d("MyEventsActivity", "onFailure: " + t.message)
                }
            })
        }

        else if (categoryTitle.equals("Congressos")) {
            val request = service.getEventCongressos()
            request.enqueue(object : Callback<List<Event>?> {
                override fun onResponse(call: Call<List<Event>?>, response: Response<List<Event>?>) {
                    val responseBody = response.body()!!

                    categoryAdapter = CategoryAdapter(baseContext, responseBody)
                    categoryAdapter.notifyDataSetChanged()
                    recyclerview_events.adapter = categoryAdapter

                }

                override fun onFailure(call: Call<List<Event>?>, t: Throwable) {
                    Log.d("MyEventsActivity", "onFailure: " + t.message)
                }
            })
        }

        else if (categoryTitle.equals("Infantil")) {
            val request = service.getEventInfantil()
            request.enqueue(object : Callback<List<Event>?> {
                override fun onResponse(call: Call<List<Event>?>, response: Response<List<Event>?>) {
                    val responseBody = response.body()!!

                    categoryAdapter = CategoryAdapter(baseContext, responseBody)
                    categoryAdapter.notifyDataSetChanged()
                    recyclerview_events.adapter = categoryAdapter

                }

                override fun onFailure(call: Call<List<Event>?>, t: Throwable) {
                    Log.d("MyEventsActivity", "onFailure: " + t.message)
                }
            })
        }

        else if (categoryTitle.equals("Standup")) {
            val request = service.getEventStandup()
            request.enqueue(object : Callback<List<Event>?> {
                override fun onResponse(call: Call<List<Event>?>, response: Response<List<Event>?>) {
                    val responseBody = response.body()!!

                    categoryAdapter = CategoryAdapter(baseContext, responseBody)
                    categoryAdapter.notifyDataSetChanged()
                    recyclerview_events.adapter = categoryAdapter

                }

                override fun onFailure(call: Call<List<Event>?>, t: Throwable) {
                    Log.d("MyEventsActivity", "onFailure: " + t.message)
                }
            })
        }



        }
    }