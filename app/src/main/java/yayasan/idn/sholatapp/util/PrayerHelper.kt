package yayasan.idn.sholatapp.util

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import yayasan.idn.sholatapp.R

class PrayerHelper(private val aP:Activity) {
    fun getSholat(lat: String?,long: String?,el:String?){
        val urlSholat = "https://api.pray.zone/v2/times/today.json?longitude=${long}&latitude=${lat}&elevation=${el}"
        val queue = Volley.newRequestQueue(aP)
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, urlSholat,null,
            { response ->
                setValuesSholat(response)
            },
            { Toast.makeText(aP,"Failed Show Data Sholat", Toast.LENGTH_LONG).show() })
        queue.cache.clear()
        queue.add(jsonRequest)
    }

    fun setValuesSholat(response: JSONObject) {
        val shubuh = this.aP.findViewById<TextView>(R.id.shubuh)
        shubuh.text = response.getJSONObject("results").getJSONArray("datetime")
            .getJSONObject(0).getJSONObject("times").getString("Fajr")

        val dhuha = this.aP.findViewById<TextView>(R.id.dhuha)
        dhuha.text = response.getJSONObject("results").getJSONArray("datetime")
            .getJSONObject(0).getJSONObject("times").getString("Sunrise")

        val dzuhur = this.aP.findViewById<TextView>(R.id.dzuhur)
        dzuhur.text = response.getJSONObject("results").getJSONArray("datetime")
            .getJSONObject(0).getJSONObject("times").getString("Dhuhr")

        val ashar = this.aP.findViewById<TextView>(R.id.ashar)
        ashar.text = response.getJSONObject("results").getJSONArray("datetime")
            .getJSONObject(0).getJSONObject("times").getString("Asr")

        val maghrib = this.aP.findViewById<TextView>(R.id.maghrib)
        maghrib.text = response.getJSONObject("results").getJSONArray("datetime")
            .getJSONObject(0).getJSONObject("times").getString("Maghrib")

        val isya = this.aP.findViewById<TextView>(R.id.isya)
        isya.text = response.getJSONObject("results").getJSONArray("datetime")
            .getJSONObject(0).getJSONObject("times").getString("Isha")
    }

    fun getWeather(lat: String?, long: String?,city:String?) {
        val apiKey = "732059b1c20f74eee6738e66b90b1997"
        val queue = Volley.newRequestQueue(aP)
        val urlWeather = "https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${long}&appid=${apiKey}&lang=id"
        // Request a string response from the provided URL.
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, urlWeather,null,
            { response ->
                setValuesWeather(response,city)
            },
            { Toast.makeText(aP,"Failed Show Data Weather",Toast.LENGTH_LONG).show() })
        queue.cache.clear()
        queue.add(jsonRequest)
    }

    @SuppressLint("SetTextI18n")
    fun setValuesWeather(response: JSONObject,cityName:String?) {

        val city = this.aP.findViewById<TextView>(R.id.city)
        city.text = cityName

        val weather = this.aP.findViewById<TextView>(R.id.weather)
        weather.text=response.getJSONArray("weather").getJSONObject(0).getString("description")

        val temp = this.aP.findViewById<TextView>(R.id.temp)
        var tempr=response.getJSONObject("main").getString("temp")
        tempr=((((tempr).toFloat()-273.15)).toInt()).toString()
        temp.text= "${tempr}°"

        val icn_weather = this.aP.findViewById<ImageView>(R.id.icn_weather)
        val iconWeather = response.getJSONArray("weather").getJSONObject(0).getString("icon")
        val iconUrl = "https://openweathermap.org/img/wn/$iconWeather@2x.png"
        Picasso.get().load(iconUrl).into(icn_weather)
    }


}