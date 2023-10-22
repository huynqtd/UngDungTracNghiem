package com.example.ungdungtracnghiem

import java.io.Serializable
import kotlin.String

data class Question(
    var idCauhoi: String,
    var questionNumber: Int,
    var cauHoi: String,
    var ansA: String?= null,
    var ansB: String?= null,
    var ansC: String?= null,
    var ansD: String?= null,
    var isAnswerUser: Boolean = false // Người thi chưa chọn thì bằng false, người thi chọn rồi sẽ là true
) : Serializable
