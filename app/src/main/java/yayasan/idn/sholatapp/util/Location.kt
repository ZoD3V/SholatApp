package yayasan.idn.sholatapp.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import yayasan.idn.sholatapp.MainActivity
import java.util.*

class Location(val aL:Activity):AppCompatActivity() {

    companion object{
        private const val REQUEST_PERMISSION_REQUEST_CODE = 2020
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation() {
        val locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        //getting address from latitude and longitude
        val geocoder = Geocoder(aL, Locale.getDefault())
        var addresses: List<Address>
        var address = ""
        var subaddresses: List<Address>
        var subaddress = ""

        LocationServices.getFusedLocationProviderClient(aL)
            .requestLocationUpdates(locationRequest, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    super.onLocationResult(locationResult)
                    LocationServices.getFusedLocationProviderClient(aL)
                        .removeLocationUpdates(this)
                    if (locationResult != null && locationResult.locations.size > 0) {
                        val locIndex = locationResult.locations.size - 1
                        val latitude = locationResult.locations[locIndex].latitude
                        val longitude = locationResult.locations[locIndex].longitude
                        val altitude = locationResult.locations[locIndex].altitude
                        try {
                            addresses = geocoder.getFromLocation(latitude, longitude, 3)
                            address = addresses[0].subLocality
                            subaddresses = geocoder.getFromLocation(latitude, longitude, 3)
                            subaddress = subaddresses[0].adminArea
                        }catch (ignored:RuntimeException){
                        }
                        val prayer = PrayerHelper(aL)
                        prayer.getWeather(latitude.toString(),longitude.toString(),address,subaddress)
                        prayer.getSholat(latitude.toString(),longitude.toString(),altitude.toString())
                    }
                }
            }, Looper.getMainLooper())
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MainActivity.REQUEST_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty()){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation()
            }else{
                Toast.makeText(aL,"Permission Denied!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}