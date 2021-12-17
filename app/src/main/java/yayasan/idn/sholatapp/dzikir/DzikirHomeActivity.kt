package yayasan.idn.sholatapp.dzikir

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.home_dzikir.*
import yayasan.idn.sholatapp.R
import yayasan.idn.sholatapp.alquran.ListQuranActivity
import yayasan.idn.sholatapp.MainActivity




class DzikirHomeActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_dzikir)
        dzikirpagi.setOnClickListener(this)
        dzikirpetang.setOnClickListener(this)
        dzikirsholat.setOnClickListener(this)
        window
            .setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        backtohw.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(i)
        }

    }

    override fun onClick(v: View?) {
        if (v != null) {
            if (v.id == R.id.dzikirpagi) {
                val i = Intent(this, HomePrayerActivity::class.java)
                i.putExtra("index", 0)
                startActivity(i)
            } else if (v.id == R.id.dzikirpetang) {
                val i = Intent(this, HomePrayerActivity::class.java)
                i.putExtra("index", 1)
                startActivity(i)
            } else if (v.id == R.id.dzikirsholat) {
                val i = Intent(this, HomePrayerActivity::class.java)
                i.putExtra("index", 2)
                startActivity(i)
            }
        }
    }


}