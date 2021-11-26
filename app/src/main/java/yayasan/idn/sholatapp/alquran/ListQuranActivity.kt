package yayasan.idn.sholatapp.alquran

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_quran.*
import org.json.JSONArray
import org.json.JSONException
import yayasan.idn.sholatapp.R
import yayasan.idn.sholatapp.adapter.SurahAdapter
import yayasan.idn.sholatapp.apiquran.Api
import yayasan.idn.sholatapp.model.ModelSurah

class ListQuranActivity : AppCompatActivity(),SurahAdapter.onSelectData{
    var surahAdapter: SurahAdapter? = null
    var modelSurah: MutableList<ModelSurah> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quran)


        //hide status bar
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.decorView.apply {
            // Hide both the navigation bar and the status bar.
            // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
            // a general rule, you should design your app to hide the status bar whenever you
            // hide the navigation bar.
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }

        rvSurah.isNestedScrollingEnabled = false
        rvSurah.layoutManager = LinearLayoutManager(this)
        rvSurah.setHasFixedSize(true)
        listSurah()
    }

    private fun listSurah() {
        AndroidNetworking.get(Api.URL_LIST_SURAH)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray) {
                    for (i in 0 until response.length()) {
                        try {
                            val dataApi = ModelSurah()
                            val jsonObject = response.getJSONObject(i)
                            dataApi.nomor = jsonObject.getString("nomor")
                            dataApi.nama = jsonObject.getString("nama")
                            dataApi.type = jsonObject.getString("type")
                            dataApi.ayat = jsonObject.getString("ayat")
                            dataApi.asma = jsonObject.getString("asma")
                            dataApi.arti = jsonObject.getString("arti")
                            dataApi.audio = jsonObject.getString("audio")
                            dataApi.keterangan = jsonObject.getString("keterangan")
                            modelSurah.add(dataApi)
                            showListSurah()
                        } catch (e: JSONException) {
                            e.printStackTrace()
                            Toast.makeText(this@ListQuranActivity, "Failed Show Data!",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                override fun onError(anError: ANError) {
                    Toast.makeText(this@ListQuranActivity, "Nothing internet!",
                        Toast.LENGTH_SHORT).show()
                }
            })
    }


    private fun showListSurah() {
        surahAdapter = SurahAdapter(this@ListQuranActivity, modelSurah, this)
        rvSurah!!.adapter = surahAdapter
    }

    override fun onSelected(modelSurah: ModelSurah) {
        val intent = Intent(this@ListQuranActivity, DetailActivity::class.java)
        intent.putExtra("detailSurah", modelSurah)
        startActivity(intent)
    }
}

