package yayasan.idn.sholatapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class QuranFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quran, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            QuranFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}