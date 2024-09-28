package com.example.findoraapi

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.common.api.Api.Client
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.Manifest
import android.app.LocaleManager
import android.location.LocationManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import java.security.Permissions

class GoogleMapPage : AppCompatActivity(),OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationProviderClient:FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_google_map_page)


        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationProviderClient =LocationServices.getFusedLocationProviderClient(this)


    }

    override fun onMapReady(googleMap: GoogleMap) {
        map=googleMap
        googleMap.addMarker(
            MarkerOptions()
                .position(LatLng(0.0, 0.0))
                .title("Marker")
        )

        //Enables permissions
        getCurrentLocation()

        //getCurrentLocation2()
    }

    private fun getCurrentLocation(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED)

        {
            checkLocation()
        }

        else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),100)
        }

    }
    private fun getCurrentLocation2(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            map.isMyLocationEnabled=true
            run {
                fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                    if (it != null) {
                        val latLng = LatLng(it.latitude,it.longitude)
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
                        map.addMarker(MarkerOptions().position(latLng).title("We are at Varsity"))
                    }
                }
            }
        }
    }

    private fun checkLocation() {
        val locationManager=getSystemService(LOCATION_SERVICE)as LocationManager
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            getCurrentLocation2()
        }

        else{
            showAlertDialog()
        }

    }

    private fun showAlertDialog() {
        AlertDialog.Builder(this).setTitle("Enable Location Services").setMessage("Location services require")
            .setPositiveButton("Settings"){_,_->}.setNegativeButton("Cancel",null).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==1000) {
            if (grantResults.isEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                checkLocation()
        }
        else{Toast.makeText(this,"Permission Denied", Toast.LENGTH_LONG).show()}

    }
}


