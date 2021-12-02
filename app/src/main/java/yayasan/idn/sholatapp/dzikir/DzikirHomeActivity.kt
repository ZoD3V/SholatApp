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
        if (v != null) {
            if (v.id == R.id.dzikirpagi) {
                supportFragmentManager.beginTransaction().replace(R.id.movefrg, PagiFragment())
                    .commit()
                dzikirpagi.visibility = View.GONE
            } else if (v.id == R.id.dzikirpetang) {
                supportFragmentManager.beginTransaction().replace(R.id.movefrg, PetangFragment())
                    .commit()
                dzikirpetang.visibility = View.GONE
            } else if (v.id == R.id.dzikirsholat) {
                supportFragmentManager.beginTransaction().replace(R.id.movefrg, ShalatFragment())
                    .commit()
                dzikirsholat.visibility = View.GONE
            }
        }
    }


}