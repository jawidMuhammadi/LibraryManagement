package com.example.library.book_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.library.R
import com.example.library.data.BookRepository
import com.example.library.utils.BOOK_ID
import kotlinx.android.synthetic.main.activity_book_details.*

class BookDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: BookDetailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)
        setSupportActionBar(toolbar)

        val bookId = intent?.extras?.getLong(BOOK_ID, 0L)
        bookId?.let {
            val factory =
                BookDatailsViewModleFactory(BookRepository.getInstance(application), bookId)
            viewModel = ViewModelProviders.of(this, factory).get(BookDetailsViewModel::class.java)
        }
        subscribeUI()

        floatingActionButton.setOnClickListener {
            bookId?.let { it1 -> viewModel.onFabClick(it1) }
        }
    }

    private fun subscribeUI() {
        viewModel.book.observe(this, Observer {
            it?.let {
                tv_book_name.text = it.bookName
                tv_book_author.text = it.author
                viewModel.bookToDelete = it
            }
        })

        viewModel.onFabClick.observe(this, Observer {
            it.getContentIfNotHandled()?.let { bookId ->
                finish()
                viewModel.deleteBook(bookId)

            }
        })
    }
}
