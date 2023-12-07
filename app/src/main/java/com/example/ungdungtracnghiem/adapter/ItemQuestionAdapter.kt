package com.example.ungdungtracnghiem.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ungdungtracnghiem.Question
import com.example.ungdungtracnghiem.R

// Danh sách dữ liệu truyền vào
class ItemQuestionAdapter(
    private val danhsach: ArrayList<Question>,
    private val onClioc: (Question) -> Unit
) : RecyclerView.Adapter<ItemQuestionAdapter.GiaoDienCauHoi>() {

    var hashQuestionAnswer = hashMapOf<Int, Boolean>()
    // Giao diện ánh xạ và đổ dữ liệu lên giao diện
    inner class GiaoDienCauHoi(private val view: View) : RecyclerView.ViewHolder(view) {

        var tvCauHoi: TextView = view.findViewById(R.id.tv_question)

        // Đổ dữ liệu
        fun bind(cauhoi: Question, position: Int) {
            tvCauHoi.text = cauhoi.idCauhoi

//            println("postion = $adapterPosition")

            if (hashQuestionAnswer[position] == true ){
                tvCauHoi.setBackgroundResource(R.drawable.background_answered)
            }else {
                tvCauHoi.setBackgroundResource(R.drawable.background_cauhoi)

            }

            view.setOnClickListener {
                onClioc(cauhoi)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiaoDienCauHoi {


        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.giaodien_nguoidung, parent, false);
        return GiaoDienCauHoi(view)
    }

    override fun getItemCount(): Int { // 50 câu
        println("size=${danhsach.size}")
        return danhsach.size
    }

    override fun onBindViewHolder(holder: GiaoDienCauHoi, position: Int) {
        holder.bind(danhsach[position], position)
    }
}