package com.example.ungdungtracnghiem

import kotlin.String

data class User(
    var hinhanh: Int,
    var name: String,
    var address : String
)

data class Account(
    val tenDangNhap: String ="",
    val matKhau: String = "",
    val sodienthoai: String = ""
)