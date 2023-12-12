package com.example.cinemate.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemate.R
import com.example.cinemate.databinding.MainMovieListItemBinding
import com.example.cinemate.homepage.MainMovieDataBitmap

class MainMovieAdapter(private val context: Context) :
    RecyclerView.Adapter<MainMovieAdapter.MainMovieViewHolder>() {

    private lateinit var viewBinding: MainMovieListItemBinding

    var datas = mutableListOf<MainMovieDataBitmap>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) :
            MainMovieViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.main_movie_list_item, parent, false)

        viewBinding = MainMovieListItemBinding.bind(view)

        return MainMovieViewHolder(MainMovieListItemBinding.bind(view))
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: MainMovieViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class MainMovieViewHolder(private val binding: MainMovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MainMovieDataBitmap) {
            if(item.movieImage == null)
                binding.mainMovieListImg.visibility = View.GONE
            else {
                binding.mainMovieListImg.setImageBitmap(item.movieImage) // 글 이미지
                binding.mainMovieListImg.visibility = View.VISIBLE
            }
            binding.mainMovieListTitle.text = item.movieTitle      // 글 제목
        }
    }
}