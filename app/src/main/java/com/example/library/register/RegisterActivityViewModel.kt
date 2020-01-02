package com.example.library.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.library.utils.Event

class RegisterActivityViewModel : ViewModel() {

    private var _onStudentRegisterClikced = MutableLiveData<Event<Boolean>>()
    val onStudentRegisterClicked: LiveData<Event<Boolean>>
        get() = _onStudentRegisterClikced

    private var _onBookRegisterClicked = MutableLiveData<Event<Boolean>>()
    val onBookRegisterClicked: LiveData<Event<Boolean>>
        get() = _onBookRegisterClicked

    fun onOnstudentRegisterClick() {
        _onStudentRegisterClikced.value = Event(true)
    }

    fun onBookRegisterClick() {
        _onBookRegisterClicked.value = Event(true)
    }

}