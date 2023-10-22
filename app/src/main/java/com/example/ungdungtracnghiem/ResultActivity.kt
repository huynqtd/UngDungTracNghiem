package com.example.ungdungtracnghiem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ResultActivity : AppCompatActivity() {

    var tvResult : TextView ?= null
    var tvCorrect : TextView ?= null

    var socaudalam: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        tvResult = findViewById(R.id.tv_result)
        tvCorrect = findViewById(R.id.tv_correct)


        val data = intent.getIntExtra("socaudalam",-1)



        tvResult?.text = "Số câu đã trả lời: $data/60"
        tvCorrect?.text = "Số câu trả lời đúng: "
    }
}