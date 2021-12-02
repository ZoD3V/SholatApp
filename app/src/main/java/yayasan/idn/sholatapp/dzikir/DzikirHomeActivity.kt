package yayasan.idn.sholatapp.dzikir

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.home_dzikir.*
import yayasan.idn.sholatapp.R
import yayasan.idn.sholatapp.alquran.ListQuranActivity

class DzikirHomeActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_dzikir)
        dzikirpagi.setOnClickListener(this)
        dzikirpetang.setOnClickListener(this)
        dzikirsholat.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        val intent = Intent(this,HomePrayerActivity::class.java)
        if (v != null) {
            if (v.id == R.id.dzikirpagi) {
                startActivity(intent)
            } else if (v.id == R.id.dzikirpetang) {
                startActivity(intent)
            } else if (v.id == R.id.dzikirsholat) {
                startActivity(intent)
            }
        }
    }


}