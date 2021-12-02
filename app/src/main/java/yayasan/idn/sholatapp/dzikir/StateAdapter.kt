package yayasan.idn.sholatapp.dzikir

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class StateAdapter(fragmentManager: FragmentActivity): FragmentStateAdapter(fragmentManager) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> PagiFragment()
            1 -> PetangFragment()
            2 -> ShalatFragment()
            else -> Fragment()
        }
    }

}