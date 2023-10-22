package com.example.ungdungtracnghiem

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.ungdungtracnghiem.adapter.ItemQuestionAdapter
import com.example.ungdungtracnghiem.adapter.RadioButtonAdapter
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var danhsachCauHoi: RecyclerView? = null

    var doduLieu: ItemQuestionAdapter? = null

    var tvData: TextView? = null;

    var danhsachCH: ArrayList<Question> = arrayListOf()

    private val shareViewModel : ShareViewModel by viewModels()


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        doduLieu = ItemQuestionAdapter(danhsachCH) { cauhoi ->
            hienThiNoiDungCauHoi(cauhoi)
        }
        tvData = findViewById(R.id.tv_test)
        danhsachCauHoi = findViewById(R.id.danhsachCauHoi)
        danhsachCauHoi?.adapter = doduLieu

// Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "http://10.0.2.2/Server/server..php"

// Request a string response from the provided URL.

        shareViewModel.actionScreen.observe(this) {
            println("action=$it")
            when(it) {
                is Action.Next -> {
                    println("next=${it.data!!}")
                    hienThiNoiDungCauHoi(danhsachCH[it.data!!])

                }
                is Action.Back -> {

                }
                is Action.None -> {

                }
                is Action.Answer -> {
                    shareViewModel.setChoiceAnswer(it.data, it.ans)
                    shareViewModel.setAction(Action.Next(it.data!!))
                }
                is Action.Finish -> {

                }
            }
        }

        val questionRequest = JsonArrayRequest(url,
            { response ->
                // Display the first 500 characters of the response string.

                for (i in 0 until response.length()) {

                    val objJson = response.getJSONObject(i)
                    val cauhoi = objJson.getString("cauhoi")
                    val dapanA = objJson.getString("dapanA")
                    val dapanB = objJson.getString("dapanB")
                    val dapanC = objJson.getString("dapanC")
                    val dapanD = objJson.getString("dapanD")
                    danhsachCH.add(
                        Question(
                            idCauhoi = "Câu ${i + 1}",
                            i + 1,
                            cauhoi,
                            dapanA,
                            dapanB,
                            dapanC,
                            dapanD
                        )
                    )
                    Log.d("DevLog", "$cauhoi \n $dapanA \n $dapanB \n $dapanC \n $dapanD")
                }

                for (i in response.length()..60) {
                    val index = Random.nextInt(danhsachCH.size - 1)
                    danhsachCH.add(
                        Question(
                            idCauhoi = "Câu ${i}", i + 1, danhsachCH[index].cauHoi,
                            danhsachCH[index].ansA,
                            danhsachCH[index].ansB,
                            danhsachCH[index].ansC,
                            danhsachCH[index].ansD
                        )
                    )
                }
                doduLieu?.notifyDataSetChanged()
                Log.d("DevLog", "${response.length()}")
            },
            { e -> Log.d("DevLog", "Error ${e.message}") })

// Add the request to the RequestQueue.
        queue.add(questionRequest)


    }

    private fun hienThiNoiDungCauHoi(cauhoi: Question) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        var fragmentCauHoi = CauHoiFragment()

        val bundle = Bundle()

        bundle.putSerializable("data", cauhoi);

        fragmentCauHoi.arguments = bundle

        fragmentTransaction.replace(R.id.chuanoidungcauhoi, fragmentCauHoi)
        fragmentTransaction.commit()
    }


}