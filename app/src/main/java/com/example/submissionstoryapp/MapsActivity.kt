package com.example.submissionstoryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.submissionstoryapp.data.UserPreference
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.submissionstoryapp.databinding.ActivityMapsBinding
import com.example.submissionstoryapp.model.MapViewModel
import com.google.android.gms.maps.model.MapStyleOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var viewModel: MapViewModel
    private lateinit var sharedPref: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = UserPreference(this)
        var token = "Bearer ${sharedPref.fetchToken()}"

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MapViewModel::class.java)
        viewModel.setLoc(token)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        styleMap()

        viewModel.getLoc().observe(this, {
            for (i in viewModel.getLoc().value?.indices!!){
                val dataLocation = LatLng(
                    viewModel.getLoc().value?.get(i)?.lat!!,
                    viewModel.getLoc().value?.get(i)?.lon!!
                )
                mMap.addMarker(MarkerOptions().position(dataLocation).title(viewModel.getLoc().value?.get(i)?.name))
                mMap.moveCamera(CameraUpdateFactory.newLatLng(dataLocation))
            }
        })
    }

    private fun styleMap(){
        try {
            val style = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
        }catch (exception: Exception){
            Log.e(TAG, "Can't find style. Error: ", exception)
        }
    }
    companion object{
        const val TAG = "MapsActivity"
    }
}