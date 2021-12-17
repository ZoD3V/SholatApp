package yayasan.idn.sholatapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import java.text.DecimalFormat
import java.util.*


@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    private var myRequestCode = 1010
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        getLastLocation()

        window
            .setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (CheckPermission()){
            if (LocationEnabled()){
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                },1000)
            }else{
                Toast.makeText(this,"Please Turn On Your GPS LOCATION",Toast.LENGTH_SHORT).show()
            }
        }else{
            RequestPermission()
        }
    }

    private fun LocationEnabled(): Boolean {
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun RequestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION),myRequestCode)
    }

    private fun CheckPermission(): Boolean {
        if (
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ){
            return true
        }
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == myRequestCode){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation()
            }
        }
    }
}