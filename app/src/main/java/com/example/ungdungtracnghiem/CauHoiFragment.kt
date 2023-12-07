package com.example.ungdungtracnghiem

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
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

    private var radioAdapter = RadioButtonAdapter(::onClick)
    private var userAnsQuestion = -1

    @SuppressLint("NotifyDataSetChanged")
    private fun onClick(position: Int) {

        userAnsQuestion = position
        rcvQuestion?.post {
            radioAdapter.notifyDataSetChanged()
        }
    }

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

    var btnCauSau: Button ?= null
    var btnCauTraLoi: Button ?= null
    var btnNopBai: Button ?= null


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
        btnNopBai = view.findViewById(R.id.btn_nopbai)

        println("${shareViewModel.hashUserChoice}")


        cauhoi?.let {
            textViewCauHoi?.text = it.cauHoi.trim()
            listQuestion.add(it.ansA!!.trim())
            listQuestion.add(it.ansB!!.trim())
            listQuestion.add(it.ansC!!.trim())
            listQuestion.add(it.ansD!!.trim())

            radioAdapter.submitList(listQuestion)
        }

        shareViewModel.hashUserChoice[cauhoi?.questionNumber]?.let {
            radioAdapter.positionSelected = it
        }

        rcvQuestion?.apply {
            adapter = radioAdapter
            hasFixedSize()
        }

        btnCauTraLoi?.setOnClickListener {
//            shareViewModel.setChoiceAnswer()
            shareViewModel.setAction(Action.Answer(cauhoi?.questionNumber!!, userAnsQuestion))
        }

        btnCauSau?.setOnClickListener {
            shareViewModel.setAction(Action.Next(cauhoi?.questionNumber))
        }

        btnNopBai?.setOnClickListener {

            showDialog("Nộp bài")
        }

        return view
    }

    private fun showDialog(titleId: String) {


        AlertDialog.Builder(requireContext()).apply {
            setTitle(titleId)
            setMessage("Bạn có chắc chắn muốn nộp bài không?")
            setNegativeButton("Có") { dialog, p1 ->

                dialog.dismiss()
                val intent = Intent(requireContext(), ResultActivity::class.java)

                intent.putExtra("socaudalam",shareViewModel.hashUserChoice.size)
                intent.putExtra("socautraloidung",shareViewModel.getCorrectUserAnswer())

                startActivity(intent)
                requireActivity().finish()

            }
            setPositiveButton("Không") { dialog, p1 ->
                dialog.dismiss()
            }
        }.show()
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