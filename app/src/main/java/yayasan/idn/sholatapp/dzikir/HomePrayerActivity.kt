package yayasan.idn.sholatapp.dzikir

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_home_prayer.*
import yayasan.idn.sholatapp.R

class HomePrayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_prayer)

        view_pager2.adapter = StateAdapter(this)

        val tabList = arrayOf("Dzikir Pagi","Dzikir Petang","Dzikir Shalat")
        TabLayoutMediator(tab_layout,view_pager2){tabs,position ->
            tabs.text = tabList[position]
        }.attach()

    }
}