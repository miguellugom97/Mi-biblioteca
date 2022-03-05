package com.miguellugo.mibiblioteca.ui.view.newbook

import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.miguellugo.mibiblioteca.R
import com.miguellugo.mibiblioteca.data.database.entities.Book
import com.miguellugo.mibiblioteca.databinding.FragmentNewBookBinding
import com.miguellugo.mibiblioteca.ui.viewmodel.BookViewModel
import com.miguellugo.mibiblioteca.ui.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewBookFragment : Fragment() {
    private var _binding: FragmentNewBookBinding? = null
    private val binding get() = _binding!!
    private var image: Bitmap? = null
    private val bookViewModel: BookViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var isNewBook = true
    private var bookId = 0
    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            // use the returned uri
            val uriContent = result.uriContent
            val uriFilePath = result.getUriFilePath(requireContext()) // optional usage
            image = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uriContent)
            binding.ivBook.load(image)
        } else {
            // an error occurred
            val exception = result.error
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {

        sharedViewModel.isNew.observe(viewLifecycleOwner) { isNew ->
            isNewBook = isNew
            if (!isNew) {
                sharedViewModel.book.observe(viewLifecycleOwner) { book ->
                    setBook(book)
                }
            }
        }
        binding.ivBook.setOnClickListener {
            cropImage.launch(
                options {
                    setGuidelines(CropImageView.Guidelines.ON)
                }
            )
        }
        binding.btnSaveContact.setOnClickListener {
            createBook()
        }
    }

    private fun setBook(book: Book) {
        image = book.image
        bookId = book.id
        binding.apply {
            etTitle.setText(book.title)
            etAuthor.setText(book.author)
            etEditorial.setText(book.editorial)
            etYear.setText(book.year)
            ivBook.load(book.image)
        }
    }

    private fun createBook() {
        val title = binding.etTitle.text.toString()
        val author = binding.etAuthor.text.toString()
        val editorial = binding.etEditorial.text.toString()
        val year = binding.etYear.text.toString()

        if (isNewBook){
            val book = Book(title = title, author = author, editorial = editorial, year = year, image = image!!)
            saveBook(book)
        }
        else {
            val book = Book(id = bookId, title = title, author = author, editorial = editorial, year = year, image = image!!)
            updateBook(book)
        }
    }

    private fun saveBook(book: Book) {
        bookViewModel.addBook(book)
        Toast.makeText(context, getString(R.string.msg_save_book), Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()
    }

    private fun updateBook(book: Book) {
        bookViewModel.upBook(book)
        Toast.makeText(context, R.string.msg_updated_book, Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}