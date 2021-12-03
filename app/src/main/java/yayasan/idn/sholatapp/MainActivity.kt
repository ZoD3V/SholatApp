package yayasan.idn.sholatapp

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*
import yayasan.idn.sholatapp.alquran.ListQuranActivity
import yayasan.idn.sholatapp.dzikir.DzikirHomeActivity
import yayasan.idn.sholatapp.util.LoadingDialog
import yayasan.idn.sholatapp.util.Location
import yayasan.idn.sholatapp.util.PrayerHelper
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity() {
    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    companion object{
        const val REQUEST_PERMISSION_REQUEST_CODE = 2020
    }
    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //call loading item
        val loading = LoadingDialog(this)
        loading.startLoading()
        val handler = Handler()
        handler.postDelayed({ loading.isDismiss() },2000)

        navBar.background = null
        navBar.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_quran -> {
                    startActivity(Intent(this,ListQuranActivity::class.java))
                }
                R.id.nav_dzikir -> {
                    startActivity(Intent(this,DzikirHomeActivity::class.java))
                }
            }
            true
        }

        //hide status bar
        window
            .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val calendar = Calendar.getInstance()
        val timeOfDay = calendar.get(Calendar.HOUR_OF_DAY)

        val location = Location(this)

        try {
            location.getCurrentLocation()
            welcomeText(timeOfDay)
            automatedTheme(timeOfDay)
        } catch (ignored: Exception) {
        }

        kiblat_btn.setOnClickListener{
            startActivity(Intent(this,KiblatActivity::class.java))
        }

        //show day
        val day = LocalDate.now().dayOfWeek.name
        val month = LocalDate.now().month.name
        val year = LocalDate.now().year
        tanggal.text = "$day, $month $year"
    }

    private fun automatedTheme(timeTheme:Int){
        if(timeTheme < 18){
            frameLayout.setBackgroundResource(R.drawable.weather)
            rounded_home.setBackgroundResource(R.drawable.rounded_home)
            list_shalat1.setBackgroundResource(R.drawable.list_shalat)
            list_shalat2.setBackgroundResource(R.drawable.list_shalat)
            list_shalat3.setBackgroundResource(R.drawable.list_shalat)
            list_shalat4.setBackgroundResource(R.drawable.list_shalat)
            list_shalat5.setBackgroundResource(R.drawable.list_shalat)
            list_shalat6.setBackgroundResource(R.drawable.list_shalat)
        }else{
            frameLayout.setBackgroundResource(R.drawable.weather_rain)
            rounded_home.setBackgroundResource(R.drawable.rounded_dark)
            list_shalat1.setBackgroundResource(R.drawable.list_shalat_dark)
            list_shalat2.setBackgroundResource(R.drawable.list_shalat_dark)
            list_shalat3.setBackgroundResource(R.drawable.list_shalat_dark)
            list_shalat4.setBackgroundResource(R.drawable.list_shalat_dark)
            list_shalat5.setBackgroundResource(R.drawable.list_shalat_dark)
            list_shalat6.setBackgroundResource(R.drawable.list_shalat_dark)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun welcomeText(time:Int){
        if (time in 0..11) {
            weltext.text = "Assalamualaikum, sudah sholat Subuh?"
        } else if (time in 12..14) {
            weltext.text = "Assalamualaikum, sudah sholat Dzuhur?"
        } else if (time in 15..17) {
            weltext.text = "Assalamualaikum, sudah sholat Ashar?"
        } else if (time in 18..18) {
            weltext.text = "Assalamualaikum, sudah sholat Maghrib?"
        } else if (time in 19..23){
            weltext.text = "Assalamualaikum, sudah sholat Isya?"
        }
    }
}
