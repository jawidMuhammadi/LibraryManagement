package com.example.library.book_list


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.library.R
import com.example.library.adapter.BookListAdapter
import com.example.library.adapter.OnBookItemClickListener
import com.example.library.book_details.BookDetailsActivity
import com.example.library.data.BookRepository
import com.example.library.utils.BOOK_ID
import kotlinx.android.synthetic.main.fragment_book_list.view.*

/**
 * A simple [Fragment] subclass.
 */
class BookListFragment : Fragment() {

    private lateinit var viewModel: BookListViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_book_list, container, false)

        val factory =
            BookListViewModelFactory(BookRepository.getInstance(requireActivity().application))
        viewModel = ViewModelProviders.of(this, factory).get(BookListViewModel::class.java)

        val adapter = BookListAdapter(onBookItemClickListener = OnBookItemClickListener {
            viewModel.onListItemClick(it)
        })
        view.recyclerViewBookList.adapter = adapter

        subscribeUI(adapter)

        return view
    }

    private fun subscribeUI(adapter: BookListAdapter) {
        viewModel.bookList.observe(this, Observer {
            adapter.submitList(it)
        })

        viewModel.onListItemClick.observe(this, Observer {
            it.getContentIfNotHandled()?.let { bookId ->
                navigateToBookDetailsActivity(bookId)
            }
        })
    }

    private fun navigateToBookDetailsActivity(bookId: Long) {
        val intent =
            Intent(requireContext().applicationContext, BookDetailsActivity::class.java).apply {
                putExtra(BOOK_ID, bookId)
            }
        startActivity(intent)

    }


}
