package com.example.vought.home

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vought.R
import com.example.vought.home.adapter.EventAdpter
import com.example.vought.model.DataSource
import com.example.vought.model.Event
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LocationEventFragment : Fragment() {
    private var items: List<Event> = ArrayList()
    private lateinit var eventAdapter: EventAdpter

    //    private val places = arrayListOf(
//        Place(
//            "SPTECH",
//            LatLng(-23.5581416, -46.6615821),
//            "Rua Haddock Lobo, 595 - Cerqueira César",
//            4.9f
//        )
//    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_location_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync { googleMap ->
            addMarkers(googleMap)
        }
    }
    fun setDataSet(events: List<Event>){
        this.items = events
    }

    private fun addMarkers(googleMap: GoogleMap) {

        items.forEach { item ->
            val marker = googleMap.addMarker(
                MarkerOptions()
                    .title(item.name_event)
                    .snippet(item.addressEvent)
                    .position(LatLng(item.latitude.toDouble(), item.longitude.toDouble()))
            )
        }
    }
}