package yayasan.idn.sholatapp


import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
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
        getJsonData(lat,long)
    }

    private fun getJsonData(lat: String?, long: String?) {
        val APIKEY = "732059b1c20f74eee6738e66b90b1997"
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${long}&appid=${APIKEY}"
        // Request a string response from the provided URL.
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            { response ->
                println(response)
                setValues(response)
            },
            { Toast.makeText(this,"ERROR",Toast.LENGTH_LONG).show() })
        queue.cache.clear()
        queue.add(jsonRequest)
    }

    @SuppressLint("SetTextI18n")
    private fun setValues(response: JSONObject) {
        city.text = response.getString("name")
        weather.text=response.getJSONArray("weather").getJSONObject(0).getString("main")
        var tempr=response.getJSONObject("main").getString("temp")
        tempr=((((tempr).toFloat()-273.15)).toInt()).toString()
        temp.text= "${tempr}°C"

    }
}