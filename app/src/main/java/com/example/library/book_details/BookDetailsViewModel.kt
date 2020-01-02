package com.example.library.book_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.library.data.Book
import com.example.library.data.BookRepository
import com.example.library.utils.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookDetailsViewModel(
    private val bookRepository: BookRepository,
    private val bookId: Long
) : ViewModel() {
    val book: LiveData<Book> = bookRepository.getBookById(bookId)
    var bookToDelete: Book? = null


    private var _onFabClick = MutableLiveData<Event<Long>>()
    val onFabClick: LiveData<Event<Long>>
        get() = _onFabClick

    fun onFabClick(bookId: Long) {
        _onFabClick.value = Event(bookId)
    }

    fun deleteBook(bookId: Long) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                bookRepository.deleteBook(bookToDelete)
            }
        }
    }
}