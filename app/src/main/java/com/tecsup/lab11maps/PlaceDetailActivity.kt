package com.tecsup.tecsupapp

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class PlaceDetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var placeName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_detail)

        placeName = intent.getStringExtra("placeName") ?: ""

        val placeNameTextView: TextView = findViewById(R.id.placeNameTextView)
        placeNameTextView.text = placeName

        val backIcon: ImageView = findViewById(R.id.backIcon)
        backIcon.setOnClickListener {
            // Navegar de regreso a MapsActivity
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
            finish()
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.fragmentMapDetail) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Establecer el estilo del mapa
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.googlestyle))

        // Habilitar controles adicionales
        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isRotateGesturesEnabled = false

        // Marcador y cámara
        val location = when (placeName) {
            "Machu Picchu" -> LatLng(-13.163031636078097, -72.5453050198952)
            "Chan Chan" -> LatLng(-8.094048748727234, -79.06780966387078)
            "Señor de Sipán" -> LatLng( -6.704882765965383, -79.89910110842484)
            else -> LatLng(0.0, 0.0) // Ubicación predeterminada si el lugar no está definido
        }

        map.addMarker(MarkerOptions().position(location).title(placeName))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 14f))

        // Listener para eventos de clic en el mapa
        map.setOnMapClickListener { latLng ->
            map.clear()
            map.addMarker(MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon))
                .position(latLng)
                .title("Nuevo marcador")
            )

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))

            // Obtener nombre de la calle en la ubicación seleccionada
            getStreetName(latLng.latitude, latLng.longitude)
        }
    }

    private fun getStreetName(lat: Double, lon: Double) {
        val geoCoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses = geoCoder.getFromLocation(lat, lon, 1)
            if (addresses != null && addresses.isNotEmpty()) {
                val address = addresses[0]
                val streetName = address.getAddressLine(0)
                Toast.makeText(this, streetName, Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
