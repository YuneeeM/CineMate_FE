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
import com.example.cinemate.databinding.SearchMovieListItemBinding
import com.example.cinemate.searchpage.Item
import com.example.cinemate.searchpage.MovieResponse

class MainMovieMoreAdapter(var mainMovieData: MovieResponse, var context: Context) :
    RecyclerView.Adapter<MainMovieMoreAdapter.MainMovieMoreViewHolder>() {

    private lateinit var viewBinding: SearchMovieListItemBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainMovieMoreViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.search_movie_list_item, parent, false)
        viewBinding = SearchMovieListItemBinding.bind(view)

        return MainMovieMoreViewHolder(SearchMovieListItemBinding.bind(view))
    }

    override fun getItemCount(): Int {
        return mainMovieData.result.size
    }

    override fun onBindViewHolder(
        holder: MainMovieMoreViewHolder,
        position: Int
    ) {
        when (holder) {
            is RecyclerView.ViewHolder -> holder.bind(mainMovieData.result[position])
        }
    }

    inner class MainMovieMoreViewHolder(private val binding: SearchMovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Item) {
            // Glide 사용하여 이미지 로드
            Glide.with(context).load(data.image)
                .apply(RequestOptions().override(120, 180))
                .error(R.drawable.cinemate_logo) // 로드 실패 시 표시할 이미지 지정
                .into(binding.mainMovieListImg)

            // 텍스트뷰에 데이터 설정
            binding.searchMovieListTitle.text = data.title
            binding.searchMovieListActor.text = "출연 :  ${data.actor}"
            binding.searchMovieListDirector.text = "감독 : ${data.director}"
            binding.searchMovieListRatings.text = "평점 : ${data.usrRating}"
            binding.searchMovieListPubdate.text = "개봉일 : ${data.pubDate}"

            // 클릭시 웹사이트 연결
            binding.mainMovieListImg.setOnClickListener {
                val webpage = Uri.parse("${data.link}")
                val webIntent = Intent(Intent.ACTION_VIEW, webpage)
                binding.root.context.startActivity(webIntent)
            }
        }
    }

    fun updateData(newMovieResponse: MovieResponse) {
        mainMovieData = newMovieResponse
        notifyDataSetChanged()
    }
}