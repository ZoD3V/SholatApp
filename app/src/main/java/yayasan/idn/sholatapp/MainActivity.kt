package yayasan.idn.sholatapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


class MainActivity : AppCompatActivity() {
    private var APIKEY = "732059b1c20f74eee6738e66b90b1997"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val lat = intent.getStringExtra("lat")
        val long = intent.getStringExtra("long")
        getJsonData(lat,long)
    }

    private fun getJsonData(lat: String?, long: String?) {
        val queue = Volley.newRequestQueue(this)
        val url = "api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${long}&appid=${APIKEY}"

        // Request a string response from the provided URL.
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url,null
            Response.Listener { response ->

            },
            Response.ErrorListener{})

        // Add the request to the RequestQueue.
        queue.add(jsonRequest)
    }
}