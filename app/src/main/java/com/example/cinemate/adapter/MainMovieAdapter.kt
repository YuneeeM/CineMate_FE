package com.example.cinemate.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cinemate.R
import com.example.cinemate.databinding.MainMovieListItemBinding
import com.example.cinemate.searchpage.Item
import com.example.cinemate.searchpage.MovieResponse

class MainMovieAdapter(val mainData: MovieResponse, var context: Context) :
    RecyclerView.Adapter<MainMovieAdapter.MainMovieViewHolder>() {

    private lateinit var viewBinding: MainMovieListItemBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) :
            MainMovieViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.main_movie_list_item, parent, false)

        viewBinding = MainMovieListItemBinding.bind(view)

        return MainMovieViewHolder(MainMovieListItemBinding.bind(view))
    }

    override fun getItemCount(): Int {
        return mainData.result.size
    }

    override fun onBindViewHolder(holder: MainMovieViewHolder, position: Int) {
        when(holder){
            is RecyclerView.ViewHolder -> holder.bind(mainData.result[position])
        }
    }

    inner class MainMovieViewHolder(private val binding: MainMovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Item) {
            // Glide 사용하여 이미지 로드
            Glide.with(context).load(data.image)
                .apply(RequestOptions().override(120, 180))
                .error(R.drawable.cinemate_logo) // 로드 실패 시 표시할 이미지 지정
                .into(binding.mainMovieListImg)
            binding.mainMovieListTitle.text = data.title

            // 클릭시 웹사이트 연결
            binding.root.setOnClickListener {
                val webpage = Uri.parse("${data.link}")
                val webIntent = Intent(Intent.ACTION_VIEW, webpage)
                binding.root.context.startActivity(webIntent)
            }
        }
    }
}