package com.mabdelhafz.googlemapsintegration

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.TileOverlayOptions
import com.google.maps.android.heatmaps.Gradient
import com.google.maps.android.heatmaps.HeatmapTileProvider
import com.mabdelhafz.googlemapsintegration.misc.CameraAndViewport
import com.mabdelhafz.googlemapsintegration.misc.Overlays
import com.mabdelhafz.googlemapsintegration.misc.Shapes
import com.mabdelhafz.googlemapsintegration.misc.TypeAndStyle

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    private val losAngeles = LatLng(34.04692127928215, -118.24748421830992)

    private val typeAndStyle by lazy { TypeAndStyle() }
    private val cameraAndViewport by lazy { CameraAndViewport() }
    private val shapes by lazy { Shapes() }
    private val overlays by lazy { Overlays() }

    private val locationList = listOf(
        LatLng(33.987459169366396, -117.43514666922263),
        LatLng(34.02844168496524, -116.80892595820784),
        LatLng(33.987459169366396, -116.01791032324176),
        LatLng(33.78681578842077, -115.11153826995461),
        LatLng(33.850708044143765, -114.2546046745436),
        LatLng(33.58112491982409, -112.71651872998432),
        LatLng(33.51245201506937, -110.99166521283668),
        LatLng(33.549084352008315, -110.1621974436902),
        LatLng(33.484967570542835, -108.95919451960859),
        LatLng(32.50364494635834, -106.82235368711534),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.map_types_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        typeAndStyle.setMapType(item, map)
        return true
    }

    override fun onMapReady(googleMap: GoogleMap) {
        Log.d("MapsActivity", "Ready")
        map = googleMap
        // Add a marker in Los Angeles and move the camera
        val losAngelesMarker =
            map.addMarker(
                MarkerOptions()
                    .position(losAngeles)
                    .title("Marker in Los Angeles")
                    .snippet("Some random text")
            )

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(losAngeles, 10f))
        map.uiSettings.apply {
            isZoomControlsEnabled = true
            isMyLocationButtonEnabled = true
        }

        typeAndStyle.setMapStyle(map, this)

        addHeatMap()

    }

    private fun addHeatMap(){

        val colors = intArrayOf(
            Color.rgb(0, 128, 255), // Blue
            Color.rgb(204,0,204), // Pink
            Color.rgb(255, 255, 51) // Yellow
        )

        val startPoints = floatArrayOf(0.2f, 0.5f, 0.8f)
        val gradient = Gradient(colors, startPoints)

        val provider = HeatmapTileProvider.Builder()
            .data(locationList)
//            .gradient(gradient)
//            .opacity(0.30)
            .radius(50)
            .build()
        val overlay = map.addTileOverlay(TileOverlayOptions().tileProvider(provider))

//        lifecycleScope.launch {
//            delay(5000)
//            overlay.clearTileCache()
//            overlay.remove()
//        }
    }

}