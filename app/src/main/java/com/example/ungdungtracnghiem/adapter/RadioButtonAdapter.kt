package com.example.ungdungtracnghiem.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ungdungtracnghiem.R
import com.example.ungdungtracnghiem.Question
import com.example.ungdungtracnghiem.ShareViewModel

class RadioButtonAdapter(
    private val onClick: (Int) -> Unit,
) :
    RecyclerView.Adapter<RadioButtonAdapter.RadioButtonViewHolder>() {


    private val question: MutableList<String> = mutableListOf()
    var positionSelected = -1

    fun submitList(it: List<String>) {
        question.clear()
        question.addAll(it)

        notifyItemRangeChanged(0, question.size)
    }

    inner class RadioButtonViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        var tvQuestion: TextView = view.findViewById(R.id.tv_ans_question)
        var rbQuestion: RadioButton = view.findViewById(R.id.rb_check)

        fun binding(data: String, position: Int) {
            tvQuestion.text = data

            rbQuestion.isChecked = (positionSelected == position)

            rbQuestion.setOnCheckedChangeListener { compoundButton, b ->

                if (b) {
                    positionSelected = position
                    onClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RadioButtonViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_radio_button, parent, false)
        return RadioButtonViewHolder(view)

    }

    override fun getItemCount(): Int {

        return question.size
    }

    override fun onBindViewHolder(holder: RadioButtonViewHolder, position: Int) {

        holder.binding(question[position], position)
    }
}