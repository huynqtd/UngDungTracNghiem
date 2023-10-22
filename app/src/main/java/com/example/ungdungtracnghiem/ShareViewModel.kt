package com.example.ungdungtracnghiem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.String

class ShareViewModel : ViewModel() {

    private val _choiceAnswerQuestion: MutableLiveData<HashMap<Int, String>> by lazy { MutableLiveData() }

    val choiceAnswer: LiveData<HashMap<Int, String>> = _choiceAnswerQuestion

    val hashUserChoice = hashMapOf<Int, Int>()
    private val _actionScreen: MutableLiveData<Action> by lazy { MutableLiveData(Action.None) }

    val actionScreen: LiveData<Action> = _actionScreen


    fun setChoiceAnswer(data: Int?, ans: Int?) {
        data?.let {
            hashUserChoice[data] = ans ?: -1
        }
    }

    fun setAction(action: Action) {
        _actionScreen.postValue(action)
    }
}

sealed class Action(var data: Int? = null, var ans: Int? = null) {
    class Next(data: Int?) : Action(data = data)
    object Back : Action()
    object None : Action()
    class Answer(data: Int?, ans: Int?) : Action(data, ans)
    object Finish : Action()
}