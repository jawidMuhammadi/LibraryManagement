package com.example.library.student_list


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.library.R
import com.example.library.adapter.OnStudentItemClickListener
import com.example.library.adapter.StudentsListAdapter
import com.example.library.data.StudentRepository
import com.example.library.student_details.StudentDetailsActivity
import com.example.library.utils.EventObserver
import com.example.library.utils.STUDENT_ID
import kotlinx.android.synthetic.main.fragment_student_list.view.*

/**
 * A simple [Fragment] subclass.
 */
class StudentListFragment : Fragment() {

    private lateinit var viewModel: StudentListViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_student_list, container, false)
        val factory =
            StudentListViewModelFactory(StudentRepository.getInstance(requireActivity().application))
        viewModel = ViewModelProviders.of(this, factory).get(StudentListViewModel::class.java)

        val adapter = StudentsListAdapter(OnStudentItemClickListener {
            viewModel.onStudentItemClick(it)
        })

        view.student_list.adapter = adapter

        subscribeUI(adapter)

        return view
    }

    private fun subscribeUI(adapter: StudentsListAdapter) {
        viewModel.studentAndBookList.observe(this, Observer {
            adapter.submitList(it)
        })

        viewModel.onStudentItemClick.observe(this, EventObserver {
            val intent = Intent(context, StudentDetailsActivity::class.java).apply {
                putExtra(STUDENT_ID, it)
            }
            startActivity(intent)

        })

    }
}
