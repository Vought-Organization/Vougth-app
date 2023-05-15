package com.example.vought.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vought.R
import com.example.vought.databinding.FragmentHomeBinding
import com.example.vought.home.NewEvent.EventAdpter
import com.example.vought.model.DataSource
import com.example.vought.rest.Api
import com.example.vought.rest.RetrofitService
import kotlinx.android.synthetic.main.fragment_home.recyclerview

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var eventAdapter: EventAdpter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        initRecyclerView() // <- movido para cá
        addDataSource()
        return binding.root
    }
    private fun addDataSource(){
        val dataSource = DataSource.createdDataSet()
        this.eventAdapter.setDataSet(dataSource)
//
//        val service = Api.createService(RetrofitService::class.java)
//        val request = service.getAllUsers()
    }

    private fun initRecyclerView(){
        this.eventAdapter = EventAdpter()
        binding.recyclerview.layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
        binding.recyclerview.adapter = this.eventAdapter
    }
}
