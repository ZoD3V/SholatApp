package yayasan.idn.sholatapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class DzikirFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dzikir, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            DzikirFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}