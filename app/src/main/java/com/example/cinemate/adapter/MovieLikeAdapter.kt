package com.example.cinemate.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cinemate.R
import com.example.cinemate.databinding.MainMovieListItemBinding
import com.example.cinemate.mypage.MovieLikeResponse
import com.example.cinemate.mypage.MovieLikeResult

class MovieLikeAdapter(var movieLikeData: MovieLikeResponse, var context: Context) :
    RecyclerView.Adapter<MovieLikeAdapter.MovieLikeViewHolder>() {
    private lateinit var viewBinding: MainMovieListItemBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieLikeViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.main_movie_list_item, parent, false)
        viewBinding = MainMovieListItemBinding.bind(view)

        return MovieLikeViewHolder(MainMovieListItemBinding.bind(view))
    }

    override fun getItemCount(): Int {
        return movieLikeData.result.size
    }

    override fun onBindViewHolder(holder: MovieLikeViewHolder, position: Int) {
        when (holder) {
            is RecyclerView.ViewHolder -> holder.bind(movieLikeData.result[position])
        }
    }

    inner class MovieLikeViewHolder(private val binding: MainMovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: MovieLikeResult) {

            // Glide 사용하여 이미지 로드
            Glide.with(context).load(data.image)
                .apply(RequestOptions().override(120, 180))
                .error(R.drawable.cinemate_logo) // 로드 실패 시 표시할 이미지 지정
                .into(binding.mainMovieListImg)
            binding.mainMovieListTitle.text = data.title

//                    // 클릭시 웹사이트 연결
//                    binding.mainMovieListImg.setOnClickListener {
//                        val webpage = Uri.parse("${data.link}")
//                        val webIntent = Intent(Intent.ACTION_VIEW, webpage)
//                        binding.root.context.startActivity(webIntent)
//                    }

        }
    }

    fun updateData(newMovieResponse: MovieLikeResponse) {
        movieLikeData = newMovieResponse
        notifyDataSetChanged()
    }
}