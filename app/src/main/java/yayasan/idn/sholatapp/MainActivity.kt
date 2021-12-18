package yayasan.idn.sholatapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*
import yayasan.idn.sholatapp.alquran.ListQuranActivity
import yayasan.idn.sholatapp.databinding.ActivityMainBinding
import yayasan.idn.sholatapp.dzikir.DzikirHomeActivity
import yayasan.idn.sholatapp.util.LoadingDialog
import yayasan.idn.sholatapp.util.Location
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class MainActivity : AppCompatActivity() {
    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    companion object{
        const val REQUEST_PERMISSION_REQUEST_CODE = 2020
    }
    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val animation = AnimationUtils.loadAnimation(this, R.anim.explosion_animation).apply {
            duration = 700
            interpolator = AccelerateDecelerateInterpolator()
        }

        binding.fab.setOnClickListener{

            binding.fab.isVisible = false
            binding.circle.isVisible = true
            binding.circle.startAnimation(animation) {
                // display your fragment
//                binding.root.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_700))
                binding.circle.isVisible = false
                startActivity(Intent(this,WeatherActivity::class.java))
            }
        }

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
        val location = Location(this)
        try {
            location.getCurrentLocation()
        } catch (ignored: Exception) {
        }

        kiblat_btn.setOnClickListener{
            startActivity(Intent(this,KiblatActivity::class.java))
        }

        //show day
        val day = LocalDate.now().dayOfWeek.name
        val month = LocalDate.now().month.name
        val date = LocalDate.now().dayOfMonth
        val calendar = Calendar.getInstance()
        val timenow = calendar.get(Calendar.HOUR_OF_DAY)

        tv_datetoshow.text = "$day, $date $month"

        val currentTime = LocalDateTime.now()
        val timeNow = currentTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
            .removeSuffix(" AM").removeSuffix(" PM")

        tv_time.text = timeNow

        prayerTimeText(timenow)


    }

    @SuppressLint("SetTextI18n")
    private fun prayerTimeText(time:Int){
        if (time in 3..5) {
            timetosholat.text = "Subuh"
        } else if (time in 11..12) {
            timetosholat.text = "Dzuhur"
        } else if (time in 14..15) {
            timetosholat.text = "Ashar"
        } else if (time in 17..18) {
            timetosholat.text = "Maghrib"
        } else if (time == 19){
            timetosholat.text = "Isya"
        }
    }

}