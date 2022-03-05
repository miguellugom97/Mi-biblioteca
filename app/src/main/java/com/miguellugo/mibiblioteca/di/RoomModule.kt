package com.miguellugo.mibiblioteca.di

import android.content.Context
import androidx.room.Room
import com.miguellugo.mibiblioteca.data.database.BookDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    private const val CONTACT_DATABASE_NAME = "book_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, BookDatabase::class.java, CONTACT_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideContactDao(db: BookDatabase) = db.getBookDao()
}