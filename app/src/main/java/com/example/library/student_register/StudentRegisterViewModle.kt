package com.example.library.student_register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.library.data.Student
import com.example.library.data.StudentRepository
import com.example.library.utils.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StudentRegisterViewModle(private val studentRepository: StudentRepository) : ViewModel() {

    private val uiScope = CoroutineScope(Dispatchers.Main)
    private var _onSaveMenuClick = MutableLiveData<Event<Boolean>>()
    val onSaveMenuClick: LiveData<Event<Boolean>>
        get() = _onSaveMenuClick

    fun saveStudent(name: String, className: String) {
        val student = Student(
            name = name,
            className = className
        )

        uiScope.launch {
            withContext(Dispatchers.IO) {
                studentRepository.insertStudent(student)
            }
        }
    }

    fun onSaveMenuClick() {
        _onSaveMenuClick.value = Event(true)
    }
}