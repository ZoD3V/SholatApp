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
    }

    override fun onClick(v: View?) {
        if (v != null) {
            if (v.id == R.id.dzikirpagi){
                supportFragmentManager.beginTransaction().replace(R.layout.home_dzikir,PagiFragment()).commit()
                dzikirpagi.visibility = View.GONE
            }

        }
    }
}