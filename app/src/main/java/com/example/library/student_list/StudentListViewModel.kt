package com.example.library.student_list

import android.util.EventLog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.library.data.StudentAndAllBooks
import com.example.library.data.StudentRepository
import com.example.library.utils.Event

class StudentListViewModel(val studentRepository: StudentRepository) : ViewModel() {

    val studentAndBookList: LiveData<List<StudentAndAllBooks>> =
        studentRepository.getAllStudentsAndAllBooks()

    private var _onStudentItemClick = MutableLiveData<Event<Long>>()
    val onStudentItemClick: LiveData<Event<Long>>
        get() = _onStudentItemClick

    fun onStudentItemClick(studentId: Long) {
        _onStudentItemClick.value = Event(studentId)
    }
}