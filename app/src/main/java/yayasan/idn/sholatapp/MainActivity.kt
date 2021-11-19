package yayasan.idn.sholatapp


import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val lat = intent.getStringExtra("lat")
        val long = intent.getStringExtra("long")
        val el = intent.getStringExtra("el")
        getWeather(lat,long)
        getSholat(lat,long,el)
    }

    private fun getSholat(lat: String?,long: String?,el:String?){
        val urlSholat = "https://api.pray.zone/v2/times/today.json?longitude=${long}&latitude=${lat}&elevation=${el}"
        val queue = Volley.newRequestQueue(this)
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, urlSholat,null,
            { response ->
                println(response)
//                setValuesSholat(response)
            },
            { Toast.makeText(this,"ERROR",Toast.LENGTH_LONG).show() })
        queue.cache.clear()
        queue.add(jsonRequest)
    }

//    private fun setValuesSholat(response: JSONObject) {
//        shubuh.text = response.getJSONObject("results").getJSONArray("datetime").getJSONObject(0).getJSONObject("times").getString("Fajr")
//
//    }

    private fun getWeather(lat: String?, long: String?) {
        val APIKEY = "732059b1c20f74eee6738e66b90b1997"
        val queue = Volley.newRequestQueue(this)
        val urlWeather = "https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${long}&appid=${APIKEY}"
        // Request a string response from the provided URL.
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, urlWeather,null,
            { response ->
                println(response)
                setValuesWeather(response)
            },
            { Toast.makeText(this,"ERROR",Toast.LENGTH_LONG).show() })
        queue.cache.clear()
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