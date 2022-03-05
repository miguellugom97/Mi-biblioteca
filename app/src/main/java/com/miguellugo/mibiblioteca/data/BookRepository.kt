package com.miguellugo.mibiblioteca.data

import com.miguellugo.mibiblioteca.data.database.dao.BookDao
import com.miguellugo.mibiblioteca.data.database.entities.Book
import javax.inject.Inject

class BookRepository @Inject constructor(private val bookDao: BookDao) {

    suspend fun getAllBooks(): List<Book> {
        return bookDao.getAllBooks()
    }

    suspend fun insertBook(book: Book) {
        bookDao.insertBook(book)
    }

    suspend fun updateBook(book: Book) {
        bookDao.updateBook(book)
    }

    suspend fun deleteBook(book: Book) {
        bookDao.deleteBook(book)
    }

    suspend fun searchBook(searchQuery: String): List<Book> {
        return bookDao.searchBook(searchQuery)
    }
}