package com.miguellugo.mibiblioteca.domain.usecases

import com.miguellugo.mibiblioteca.data.BookRepository
import com.miguellugo.mibiblioteca.data.database.entities.Book
import javax.inject.Inject

class GetAllBooks @Inject constructor(private val bookRepository: BookRepository) {
    suspend operator fun invoke(): List<Book> {
        return bookRepository.getAllBooks()
    }
}