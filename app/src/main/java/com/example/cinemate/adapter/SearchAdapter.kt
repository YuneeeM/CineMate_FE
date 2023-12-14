package com.example.cinemate.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cinemate.R
import com.example.cinemate.databinding.SearchMovieListItemBinding
import com.example.cinemate.searchpage.Item
import com.example.cinemate.searchpage.MovieResponse

class SearchAdapter(var searchData: MovieResponse, var context: Context) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private lateinit var viewBinding: SearchMovieListItemBinding

    // 아이템의 갯수
    override fun getItemCount(): Int {
        return searchData.result.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchViewHolder {
        // Use the ViewBinding to inflate the layout
        viewBinding =
            SearchMovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SearchViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: SearchAdapter.SearchViewHolder, position: Int) {
       when(holder){
           is ViewHolder -> holder.bind(searchData.result[position])
       }
    }

    inner class SearchViewHolder(private val binding: SearchMovieListItemBinding) :
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
            binding.root.setOnClickListener {
                val webpage = Uri.parse("${data.link}")
                val webIntent = Intent(Intent.ACTION_VIEW, webpage)
                binding.root.context.startActivity(webIntent)
            }
        }
    }

    fun updateData(newMovieResponse: MovieResponse) {
        searchData = newMovieResponse
        notifyDataSetChanged()
    }
}
