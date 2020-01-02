package com.example.library.book_register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.library.data.Book
import com.example.library.data.BookRepository
import com.example.library.utils.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class BookRegisterViewModel(private val bookRepository: BookRepository) : ViewModel() {

    private val uiScope = CoroutineScope(Dispatchers.Main)

    val borrowedDate: Calendar = Calendar.getInstance()
    private val returnDate = Calendar.getInstance()
    private var studentId = 0L

    val studentsName: LiveData<List<String>>
    private var studentsId = ArrayList<Long>()

    private var _onSaveMenuClick = MutableLiveData<Event<Boolean>>()
    val onSaveMenuClick: LiveData<Event<Boolean>>
        get() = _onSaveMenuClick

    init {
        studentsName = Transformations.map(bookRepository.getAllStudents()) {
            val nameList = ArrayList<String>()
            it.forEach { student ->
                nameList.add(student.name)
                studentsId.add(student.studentId)
            }
            nameList
        }
    }

    fun saveBook(bookTitle: String, author: String) {
        val book = Book(
            bookName = bookTitle,
            borrowDate = borrowedDate,
            returnDate = returnDate,
            author = author,
            studentId = studentId
        )

        uiScope.launch {
            withContext(Dispatchers.IO) {
                bookRepository.insertBook(book)
            }
        }
    }


    fun setTime(hour: Int, minute: Int) {
        returnDate.set(Calendar.HOUR_OF_DAY, hour)
        returnDate.set(Calendar.MINUTE, minute)
    }

    fun setDate(year: Int, month: Int, day: Int) {
        returnDate.set(Calendar.MONTH, month)
        returnDate.set(Calendar.YEAR, year)
        returnDate.set(Calendar.DAY_OF_YEAR, day)
    }

    fun setStudentId(position: Int) {
        studentId = studentsId[position]
    }

    fun onSaveMenuClick() {
        _onSaveMenuClick.value = Event(true)
    }

    companion object {
        val simpleDateFormat = SimpleDateFormat("MMM d, hh:mm:ss", Locale.US)
    }
}