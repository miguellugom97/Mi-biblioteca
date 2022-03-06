package com.miguellugo.mibiblioteca.ui.view.home

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.miguellugo.mibiblioteca.R
import com.miguellugo.mibiblioteca.data.database.entities.Book
import com.miguellugo.mibiblioteca.databinding.FragmentHomeBinding
import com.miguellugo.mibiblioteca.ui.view.adapters.ListBookAdapter
import com.miguellugo.mibiblioteca.ui.viewmodel.BookViewModel
import com.miguellugo.mibiblioteca.ui.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), ListBookAdapter.OnItemClickListener, android.widget.SearchView.OnQueryTextListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var listBookAdapter: ListBookAdapter
    private val bookViewModel: BookViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        setUI()
        bookViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }
        bookViewModel.books.observe(viewLifecycleOwner) { books ->
            listBookAdapter.setData(books)
        }
        getAllBooks()

        binding.svBooks.setOnQueryTextListener(this)

        binding.ivAddBook.setOnClickListener {
            sharedViewModel.saveBoolean(true)
            findNavController().navigate(R.id.action_homeFragment_to_newBookFragment)
        }
    }

    private fun getAllBooks() {
        bookViewModel.getListBooks()
    }

    private fun setUI() {
        listBookAdapter = ListBookAdapter(this)
        binding.rvBooks.adapter = listBookAdapter
        binding.rvBooks.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onBookClick(book: Book) {
        sharedViewModel.saveBook(book)
        sharedViewModel.saveBoolean(false)
        findNavController().navigate(R.id.action_homeFragment_to_newBookFragment)
    }

    override fun onDeleteBook(book: Book) {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.msg_alert))
            .setMessage(getString(R.string.msg_alert_delete_book))
            .setNegativeButton(getString(R.string.msg_alert_false)) { dialog, i ->
                dialog.dismiss()
            }.setPositiveButton(getString(R.string.msg_alert_true)) { dialog, i->
                bookViewModel.delBook(book).apply {
                    getAllBooks()
                }
            }
            .create()
            .show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchBook(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchBook(query)
        }
        return true
    }

    private fun searchBook(query: String) {
        val searchQuery = "%$query%"

        bookViewModel.searchBook(searchQuery)
        bookViewModel.books.observe(viewLifecycleOwner) { books ->
            books.let {
                listBookAdapter.setData(it)
            }
        }
    }
}