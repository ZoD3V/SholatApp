package yayasan.idn.sholatapp

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //hide status bar
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.decorView.apply {
            // Hide both the navigation bar and the status bar.
            // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
            // a general rule, you should design your app to hide the status bar whenever you
            // hide the navigation bar.
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }
        val lat = intent.getDoubleExtra("lat", 0.0)
        val long = intent.getDoubleExtra("long",0.0)
        val el = intent.getStringExtra("el")

        getWeather(lat.toString(),long.toString())
        getSholat(lat.toString(),long.toString(),el)
        welcomeText()

        //show day
        val day = LocalDate.now().dayOfWeek.name
        val month = LocalDate.now().month.name
        val year = LocalDate.now().year
        tanggal.text = "$day, $month $year"

        //change theme
        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                frameLayout.setBackgroundResource(R.drawable.weather_rain)
                rounded_home.setBackgroundResource(R.drawable.rounded_home_dark)
                list_shalat1.setBackgroundResource(R.drawable.list_shalat_dark)
                list_shalat2.setBackgroundResource(R.drawable.list_shalat_dark)
                list_shalat3.setBackgroundResource(R.drawable.list_shalat_dark)
                list_shalat4.setBackgroundResource(R.drawable.list_shalat_dark)
                list_shalat5.setBackgroundResource(R.drawable.list_shalat_dark)
                list_shalat6.setBackgroundResource(R.drawable.list_shalat_dark)

            }else{
                frameLayout.setBackgroundResource(R.drawable.weather)
                rounded_home.setBackgroundResource(R.drawable.rounded_home)
                list_shalat1.setBackgroundResource(R.drawable.list_shalat)
                list_shalat2.setBackgroundResource(R.drawable.list_shalat)
                list_shalat3.setBackgroundResource(R.drawable.list_shalat)
                list_shalat4.setBackgroundResource(R.drawable.list_shalat)
                list_shalat5.setBackgroundResource(R.drawable.list_shalat)
                list_shalat6.setBackgroundResource(R.drawable.list_shalat)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun welcomeText(){
        val calendar = Calendar.getInstance()
        val timeOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        if (timeOfDay in 0..11) {
            weltext.text = "Assalamualaikum, sudah sholat Subuh?"
        } else if (timeOfDay in 12..14) {
            weltext.text = "Assalamualaikum, sudah sholat Dzuhur?"
        } else if (timeOfDay in 15..17) {
            weltext.text = "Assalamualaikum, sudah sholat Ashar?"
        } else if (timeOfDay in 18..18) {
            weltext.text = "Assalamualaikum, sudah sholat Maghrib?"
        } else if (timeOfDay in 19..23){
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
            { Toast.makeText(this,"ERROR",Toast.LENGTH_LONG).show() })
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

    private fun getWeather(lat: String?, long: String?) {
        val lang = "id"
        val apiKey = "732059b1c20f74eee6738e66b90b1997"
        val queue = Volley.newRequestQueue(this)
        val urlWeather = "https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${long}&appid=${apiKey}"
        // Request a string response from the provided URL.
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, urlWeather,null,
            { response ->
                setValuesWeather(response)
            },
            { Toast.makeText(this,"ERROR",Toast.LENGTH_LONG).show() })
        queue.add(jsonRequest)
    }

    @SuppressLint("SetTextI18n")
    private fun setValuesWeather(response: JSONObject) {
        city.text = response.getString("name")
        weather.text=response.getJSONArray("weather").getJSONObject(0).getString("main")
        var tempr=response.getJSONObject("main").getString("temp")
        tempr=((((tempr).toFloat()-273.15)).toInt()).toString()
        temp.text= "${tempr}°C"
    }
}