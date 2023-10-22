package com.example.ungdungtracnghiem

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.ungdungtracnghiem.adapter.RadioButtonAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CauHoiFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CauHoiFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: kotlin.String? = null
    private var param2: kotlin.String? = null

    private var cauhoi: Question? = null
    private val shareViewModel : ShareViewModel by activityViewModels()

    private var radioAdapter = RadioButtonAdapter()

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

            cauhoi = it.getSerializable("data", Question::class.java)
        }
    }

    var textViewCauHoi: TextView? = null

    var rcvQuestion: RecyclerView ?= null
    var rb1: RadioButton? = null
    var rb2: RadioButton? = null
    var rb3: RadioButton? = null
    var rb4: RadioButton? = null

    var btnCauSau: Button ?= null
    var btnCauTraLoi: Button ?= null
    var listQuestion = mutableListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cau_hoi, container, false)

        textViewCauHoi = view.findViewById(R.id.cauhoi)

        rcvQuestion    = view.findViewById(R.id.rcv_question)


        btnCauSau = view.findViewById(R.id.btn_causau)
        btnCauTraLoi = view.findViewById(R.id.btn_Traloi)
        shareViewModel.hashUserChoice[cauhoi?.questionNumber]?.let {

        }

        cauhoi?.let {
            textViewCauHoi?.setText(it.cauHoi)
            listQuestion.add(it.ansA!!.trim())
            listQuestion.add(it.ansB!!.trim())
            listQuestion.add(it.ansC!!.trim())
            listQuestion.add(it.ansD!!.trim())

            radioAdapter.submitList(listQuestion)
        }

        rcvQuestion?.apply {
            adapter = radioAdapter
            hasFixedSize()
        }


        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CauHoiFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: kotlin.String, param2: kotlin.String) =
            CauHoiFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}