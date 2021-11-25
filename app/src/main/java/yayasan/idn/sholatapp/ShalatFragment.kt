package yayasan.idn.sholatapp

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi

class ShalatFragment : Fragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shalat, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ShalatFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}