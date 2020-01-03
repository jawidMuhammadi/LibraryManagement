package com.example.library.student_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.library.data.StudentAndAllBooks
import com.example.library.data.StudentRepository
import com.example.library.utils.Event

class StudentListViewModel(val studentRepository: StudentRepository) : ViewModel() {

    val studentAndBookList: LiveData<PagedList<StudentAndAllBooks>> =
        studentRepository.getAllStudentsAndAllBooks()

    private var _onStudentItemClick = MutableLiveData<Event<Long>>()
    val onStudentItemClick: LiveData<Event<Long>>
        get() = _onStudentItemClick

    fun onStudentItemClick(studentId: Long) {
        _onStudentItemClick.value = Event(studentId)
    }
}