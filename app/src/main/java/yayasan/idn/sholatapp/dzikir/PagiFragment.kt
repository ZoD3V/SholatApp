package yayasan.idn.sholatapp.dzikir

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import yayasan.idn.sholatapp.R


class PagiFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pagi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //loading

        val back = view.findViewById<ImageView>(R.id.backtohdz)
        back.setOnClickListener{
            requireActivity().run{
                startActivity(Intent(this, DzikirHomeActivity::class.java))
                finish()
            }
        }


    }
}