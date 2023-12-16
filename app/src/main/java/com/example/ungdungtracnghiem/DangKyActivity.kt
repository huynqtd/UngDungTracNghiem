package com.example.ungdungtracnghiem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONObject

class DangKyActivity : AppCompatActivity() {
    lateinit var layoutTenDangNhap: TextInputLayout
    lateinit var editTextTenDangNhap: TextInputEditText

    lateinit var layoutMatKhau: TextInputLayout
    lateinit var editTextMatKhau: TextInputEditText

    lateinit var layoutDienThoai: TextInputLayout
    lateinit var editTextDienThoai: TextInputEditText

    lateinit var buttonDangKy: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dang_ky)

        layoutTenDangNhap = findViewById(R.id.layout_ten_dang_nhap);
        editTextTenDangNhap = findViewById(R.id.edit_ten_dang_nhap)
        layoutMatKhau = findViewById(R.id.layout_mat_khau)
        editTextMatKhau = findViewById(R.id.edit_mat_khau)
        layoutDienThoai = findViewById(R.id.layout_dien_thoai)
        editTextDienThoai = findViewById(R.id.edit_dien_thoai)

        buttonDangKy = findViewById(R.id.buttonDangnhap)


        buttonDangKy.setOnClickListener {
            val tenDangNhap = editTextTenDangNhap.text.toString().trim()
            val matKhau = editTextMatKhau.text.toString().trim()
            val soDienThoai = editTextDienThoai.text.toString().trim()

            if (matKhau.length < 6 ){
                Toast.makeText(this,"Mật khẩu yêu cầu phải từ 6 ký tự trở lên",Toast.LENGTH_SHORT).show()
            }
            else if (tenDangNhap.isEmpty()) {
                Toast.makeText(this,"Tên đăng nhập không hợp lệ",Toast.LENGTH_SHORT).show()
            }
            else {
                val account = Account(tenDangNhap, matKhau, soDienThoai)

                val queue = Volley.newRequestQueue(this)
                val url = "http://10.0.2.2/Server/DangKy.php"

                val request = object : StringRequest(Request.Method.POST,url, {

                    val jsonObject = runCatching { JSONObject(it) }.onFailure {
                        Toast.makeText(this,"${it.message}", Toast.LENGTH_SHORT).show()
                    }.getOrNull()

                    val isSuccess = jsonObject?.optBoolean("success")

                    if (isSuccess == true) {
                        Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }else {
                        val data = jsonObject?.optString("message")
                        Toast.makeText(this, data ?: "Đăng ký thất bại", Toast.LENGTH_SHORT).show()

                    }

                }, {
                    Toast.makeText(this,"Đăng kí thất bại", Toast.LENGTH_SHORT).show()
                }){

                    override fun getParams(): MutableMap<String, String>? {
                        val params = mutableMapOf<String, String>()

                        params["tenDangNhap"] = account.tenDangNhap
                        params["matKhau"] = account.matKhau
                        params["soDienThoai"] = account.sodienthoai

                        return params
                    }
                }
                queue.add(request)

            }
        }

    }
}