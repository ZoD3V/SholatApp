package yayasan.idn.sholatapp

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_quran.*
import org.json.JSONObject
import yayasan.idn.sholatapp.alquran.ListQuranActivity
import yayasan.idn.sholatapp.dzikir.DzikirHomeActivity
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        val lat = intent.getDoubleExtra("lat", 0.0)
        val long = intent.getDoubleExtra("long",0.0)
        val el = intent.getStringExtra("el")
        val calendar = Calendar.getInstance()
        val timeOfDay = calendar.get(Calendar.HOUR_OF_DAY)

        val geocoder = Geocoder(this, Locale.getDefault())
        var addresses: List<Address>? = null
        try {
            addresses = geocoder.getFromLocation(lat, long, 1)
            val cityName = addresses?.get(0)?.subLocality

            getWeather(lat.toString(),long.toString(),cityName)
            getSholat(lat.toString(),long.toString(),el)
            welcomeText(timeOfDay)
            automatedTheme(timeOfDay)
        } catch (ignored: Exception) {
        }

        if (addresses == null || addresses.isEmpty()) {
            Toast.makeText(this,"No address found for the location",Toast.LENGTH_SHORT)
                .show()
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

    private fun getSholat(lat: String?,long: String?,el:String?){
        val urlSholat = "https://api.pray.zone/v2/times/today.json?longitude=${long}&latitude=${lat}&elevation=${el}"
        val queue = Volley.newRequestQueue(this)
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, urlSholat,null,
            { response ->
                setValuesSholat(response)
            },
            { Toast.makeText(this,"Failed Show Data Sholat",Toast.LENGTH_LONG).show() })
        queue.cache.clear()
        queue.add(jsonRequest)
    }

    private fun setValuesSholat(response: JSONObject) {
        shubuh.text = response.getJSONObject("results").getJSONArray("datetime")
            .getJSONObject(0).getJSONObject("times").getString("Fajr")

        dhuha.text = response.getJSONObject("results").getJSONArray("datetime")
            .getJSONObject(0).getJSONObject("times").getString("Sunrise")

        dzuhur.text = response.getJSONObject("results").getJSONArray("datetime")
            .getJSONObject(0).getJSONObject("times").getString("Dhuhr")

        ashar.text = response.getJSONObject("results").getJSONArray("datetime")
            .getJSONObject(0).getJSONObject("times").getString("Asr")

        maghrib.text = response.getJSONObject("results").getJSONArray("datetime")
            .getJSONObject(0).getJSONObject("times").getString("Maghrib")

        isya.text = response.getJSONObject("results").getJSONArray("datetime")
            .getJSONObject(0).getJSONObject("times").getString("Isha")
    }

    private fun getWeather(lat: String?, long: String?,city:String?) {
        val apiKey = "732059b1c20f74eee6738e66b90b1997"
        val queue = Volley.newRequestQueue(this)
        val urlWeather = "https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${long}&appid=${apiKey}&lang=id"
        // Request a string response from the provided URL.
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, urlWeather,null,
            { response ->
                setValuesWeather(response,city)
            },
            { Toast.makeText(this,"Failed Show Data Weather",Toast.LENGTH_LONG).show() })
        queue.cache.clear()
        queue.add(jsonRequest)
    }

    @SuppressLint("SetTextI18n")
    private fun setValuesWeather(response: JSONObject,cityName:String?) {
        city.text = cityName
//        city.text = response.getString("name")
        weather.text=response.getJSONArray("weather").getJSONObject(0).getString("description")
        var tempr=response.getJSONObject("main").getString("temp")
        tempr=((((tempr).toFloat()-273.15)).toInt()).toString()
        temp.text= "${tempr}°"
        val iconWeather = response.getJSONArray("weather").getJSONObject(0).getString("icon")
//        val iconUrl = "https://openweathermap.org/img/wn/$iconWeather@2x.png"
//        Picasso.get().load(iconUrl).into(icn_weather)
    }
}