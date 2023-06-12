package com.example.vought.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vought.R
import com.example.vought.databinding.FragmentHomeBinding
//import com.example.vought.R
import com.example.vought.category.CategoryActivity
//import com.example.vought.databinding.FragmentHomeBinding
import com.example.vought.home.adapter.CategoryAdapter
import com.example.vought.home.adapter.EventAdpter
import com.example.vought.model.DataSource
import com.example.vought.model.Event
import com.example.vought.myevents.MyEventsActivity

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var eventAdapter: EventAdpter
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        initRecyclerView()
        addDataSource()
        initRecyclerViewCategory()
        addDataSourceCategory()
        return binding.root
    }

    private fun addDataSource() {
        val dataSource = mutableListOf<Event>()
        DataSource.populateDataSet(dataSource, this.eventAdapter, this.activity)
        this.eventAdapter.setDataSet(dataSource)
       // this.eventAdapter.notifyDataSetChanged()
//
//        val service = Api.createService(RetrofitService::class.java)
//        val request = service.getAllUsers()
    }

    private fun addDataSourceCategory() {
        val dataSource = DataSource.createdCategorySet()
        this.categoryAdapter.setDataSet(dataSource)
    }

    private fun initRecyclerView() {
        this.eventAdapter = EventAdpter()
        binding.recyclerview.layoutManager =
            LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
        binding.recyclerview.adapter = this.eventAdapter
    }

    private fun initRecyclerViewCategory() {
        this.categoryAdapter = CategoryAdapter()
        val gridLayoutManager = GridLayoutManager(requireContext(), 1
        )
        binding.recyclerviewCategory.layoutManager = gridLayoutManager
        binding.recyclerviewCategory.adapter = this.categoryAdapter

        categoryAdapter.setOnItemClickListener { category ->
            val intent = Intent(activity, CategoryActivity::class.java)
            intent.putExtra("categoryTitle", category.title)
            startActivity(intent)
        }
    }
}
