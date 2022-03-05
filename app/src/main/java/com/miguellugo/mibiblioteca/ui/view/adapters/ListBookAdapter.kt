package com.miguellugo.mibiblioteca.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.miguellugo.mibiblioteca.data.database.entities.Book
import com.miguellugo.mibiblioteca.databinding.ItemListBooksBinding

class ListBookAdapter(val onItemClickListener: OnItemClickListener): RecyclerView.Adapter<ListBookAdapter.MyViewHolder>() {
    var itemList = emptyList<Book>()

    interface OnItemClickListener {
        fun onBookClick(book: Book)
        fun onDeleteBook(book: Book)
    }

    class MyViewHolder(val binding: ItemListBooksBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindView(book: Book) {
            binding.tvNameBook.text = book.title
            binding.ivBook.load(book.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemListBooksBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.bindView(currentItem)
        holder.binding.ivBook.setOnClickListener {
            onItemClickListener.onBookClick(currentItem)
        }
        holder.binding.ivDeleteBook.setOnClickListener {
            onItemClickListener.onDeleteBook(currentItem)
        }
    }

    override fun getItemCount(): Int = itemList.size

    fun setData(itemList: List<Book>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }
}