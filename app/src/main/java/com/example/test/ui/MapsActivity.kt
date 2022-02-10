package com.example.test.ui

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R
import com.example.test.common.isValidInput
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    lateinit var geocoder: Geocoder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        geocoder= Geocoder(this)
        btnFind.setOnClickListener {
            convertAddress()
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        // Adding a default marker in the map

        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }


    private fun convertAddress() {

        //get Lat Lng from typed address
        if (etCity.isValidInput()||etStreet.isValidInput()||etState.isValidInput()) {
            try {
                val address="${etStreet.text},${etCity.text},${etState.text}"
                val addressList: List<Address>? = geocoder.getFromLocationName(etCity.text.toString(), 1)
                if (addressList != null && addressList.isNotEmpty()) {
                    val lat: Double = addressList[0].latitude
                    val lng: Double = addressList[0].longitude
                    val newPosition = LatLng(lat, lng)

                    //Clearing old marking and setting new one
                    mMap.clear()
                    mMap.addMarker(MarkerOptions().position(newPosition).title(address))
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(newPosition))

                }
            } catch (e: Exception) {
                //Exception
                e.printStackTrace()
            }
        }
    }

}