package com.example.ungdungtracnghiem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.String

class ShareViewModel : ViewModel() {

    private val _choiceAnswerQuestion: MutableLiveData<HashMap<Int, String>> by lazy { MutableLiveData() }

    val choiceAnswer: LiveData<HashMap<Int, String>> = _choiceAnswerQuestion

    val hashUserChoice = hashMapOf<Int, String>()
    private val _actionScreen: MutableLiveData<Action> by lazy { MutableLiveData(Action.None) }

    val actionScreen: LiveData<Action> = _actionScreen


    fun setChoiceAnswer(data: Int?, ans: String?) {
        data?.let {
            hashUserChoice[data] = ans ?: ""
        }
    }

    fun setAction(action: Action) {
        _actionScreen.value = action
    }
}

sealed class Action(var data: Int? = null, var ans: String? = null) {
    class Next(data: Int?) : Action(data = data)
    object Back : Action()
    object None : Action()
    class Answer(data: Int?, ans: String?) : Action(data, ans)
    object Finish : Action()
}