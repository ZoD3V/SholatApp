//package yayasan.idn.sholatapp.dzikir
//
//import android.os.Bundle
//import android.view.View
//import androidx.appcompat.app.AppCompatActivity
//import kotlinx.android.synthetic.main.activity_dzikir.*
//import kotlinx.android.synthetic.main.activity_main.*
//import kotlinx.android.synthetic.main.home_dzikir.*
//import yayasan.idn.sholatapp.R
//import yayasan.idn.sholatapp.adapter.viewPagerAdapter
//
//class DzikirActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.home_dzikir)
//
//        setUpTabs()
//    }
//
//    private fun setUpTabs(){
//        val adapter = viewPagerAdapter(supportFragmentManager)
//
//        adapter.addFragment(PagiFragment(), "Pagi")
//        adapter.addFragment(PetangFragment(), "Petang")
//        adapter.addFragment(ShalatFragment(), "Shalat")
//
//        viewPager.adapter = adapter
//
//        tabs.setupWithViewPager(viewPager)
//
//        tabs.getTabAt(0)!!.setIcon(R.drawable.shalat)
//        tabs.getTabAt(1)!!.setIcon(R.drawable.dzikir)
//        tabs.getTabAt(2)!!.setIcon(R.drawable.shalat)
//    }
//}
