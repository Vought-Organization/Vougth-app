package com.example.vought.home

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.vought.R
import com.example.vought.home.adapter.EventAdpter
import com.example.vought.home.map.BitmapHelper
import com.example.vought.model.Event
import com.example.vought.rest.Api
import com.example.vought.rest.RetrofitService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationEventFragment : Fragment() {
    private var items: List<Event> = ArrayList()
    private lateinit var eventAdapter: EventAdpter
    private val LOCATION_PERMISSION_REQUEST_CODE = 100
    private lateinit var fusedLocationClient: FusedLocationProviderClient

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
            fetchEventsAndAddMarkers(googleMap)
            addMarkers(googleMap)
            googleMap.setOnMapLoadedCallback {
                val bounds = LatLngBounds.builder()

                items.forEach {
                    bounds.include(LatLng(it.latitude.toDouble(), it.longitude.toDouble()))
                }

//                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 100))
            }
            moveCameraToCurrentLocation(googleMap)
        }
    }

    private fun fetchEventsAndAddMarkers(googleMap: GoogleMap) {
        val service = Api.createService(RetrofitService::class.java)
        val request = service.getEvent()

        request.enqueue(object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                if (response.isSuccessful) {
                    val events = response.body()
                    if (events != null) {
                        setDataSet(events)
                        addMarkers(googleMap)
                    }
                } else {
                    Toast.makeText(context, "Evento não encontrado", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                // Handle network error
            }
        })
    }

    private fun setDataSet(events: List<Event>) {
        this.items = events
    }

    private fun addMarkers(googleMap: GoogleMap) {
        items.forEach { item ->
            val marker = googleMap.addMarker(
                MarkerOptions()
                    .title(item.nameEvent)
                    .snippet(item.addressEvent)
                    .position(LatLng(item.latitude.toDouble(), item.longitude.toDouble()))
                    .icon(
                        BitmapHelper.vectorToBitmap(
                            requireContext(),
                            R.drawable.star_event,
                            ContextCompat.getColor(requireContext(), R.color.color_vought)
                        )
                    )
            )
        }
    }

    private fun moveCameraToCurrentLocation(googleMap: GoogleMap) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 14f))
                }
            }
        } else {
            // Solicitar permissão de localização se ainda não estiver concedida
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val mapFragment =
                    childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
                mapFragment?.getMapAsync { googleMap ->
                    moveCameraToCurrentLocation(googleMap)
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Permissão de localização não concedida",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
