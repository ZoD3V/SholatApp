package yayasan.idn.sholatapp.alquran

import android.annotation.SuppressLint
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import kotlinx.android.synthetic.main.activity_detail_quran.*
import org.json.JSONArray
import org.json.JSONException
import yayasan.idn.sholatapp.R
import yayasan.idn.sholatapp.adapter.AyatAdapter
import yayasan.idn.sholatapp.apiquran.Api
import yayasan.idn.sholatapp.model.ModelAyat
import yayasan.idn.sholatapp.model.ModelSurah
import yayasan.idn.sholatapp.util.LoadingDialog
import java.io.IOException

class DetailActivity : AppCompatActivity() {

    var nomor: String? = null
    var nama: String? = null
    var arti: String? = null
    var type: String? = null
    var ayat: String? = null
    var keterangan: String? = null
    var audio: String? = null
    var modelSurah: ModelSurah? = null
    var ayatAdapter: AyatAdapter? = null
    var modelAyat: MutableList<ModelAyat> = ArrayList()

    @SuppressLint("RestrictedApi", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_quran)

        //loading
        val loading = LoadingDialog(this)
        loading.startLoading()
        val handler = Handler()
        handler.postDelayed({ loading.isDismiss() },2000)

//        //hide status bar
        window
            .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)


//        rvAyat.isNestedScrollingEnabled = false
        
        modelSurah = intent.getSerializableExtra("detailSurah") as ModelSurah?
        if (modelSurah != null) {
            nomor = modelSurah!!.nomor
            nama = modelSurah!!.nama
            arti = modelSurah!!.arti
            type = modelSurah!!.type
            ayat = modelSurah!!.ayat
            audio = modelSurah!!.audio
            keterangan = modelSurah!!.keterangan

//            fabStop.visibility = View.GONE
//            fabPlay.visibility = View.VISIBLE

            //Set text
            tvTitle.text = nama
//            tvSubTitle.text = arti
            tvInfo.text = "$type - $ayat Ayat "

            //get & play Audio
            val mediaPlayer = MediaPlayer()
//            fabPlay.setOnClickListener(View.OnClickListener {
//                try {
//                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
//                    mediaPlayer.setDataSource(audio)
//                    mediaPlayer.prepare()
//                    mediaPlayer.start()
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//                fabPlay.visibility = View.GONE
//                fabStop.visibility = View.VISIBLE
//            })

//            fabStop.setOnClickListener(View.OnClickListener {
//                mediaPlayer.stop()
//                mediaPlayer.reset()
//                fabPlay.visibility = View.VISIBLE
//                fabStop.visibility = View.GONE
//            })
        }
        rvAyat.layoutManager = LinearLayoutManager(this)
        rvAyat.setHasFixedSize(true)

        backtol.setOnClickListener {
            val i = Intent(this, ListQuranActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(i)
        }

        //Methods get data
        listAyat()
    }

    private fun listAyat() {
        AndroidNetworking.get(Api.URL_LIST_AYAT)
            .addPathParameter("nomor", nomor)
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray) {
                    for (i in 0 until response.length()) {
                        try {
                            val dataApi = ModelAyat()
                            val jsonObject = response.getJSONObject(i)
                            dataApi.nomor = jsonObject.getString("nomor")
                            dataApi.arab = jsonObject.getString("ar")
                            dataApi.indo = jsonObject.getString("id")
                            dataApi.terjemahan = jsonObject.getString("tr")
                            modelAyat.add(dataApi)
                            showListAyat()
                        } catch (e: JSONException) {
                            e.printStackTrace()
                            Toast.makeText(this@DetailActivity, "Failed Show Data!",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                override fun onError(anError: ANError) {
                    Toast.makeText(this@DetailActivity, "Nothing Internet",
                        Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun showListAyat() {
        ayatAdapter = AyatAdapter(this@DetailActivity, modelAyat)
        rvAyat!!.adapter = ayatAdapter
    }
}