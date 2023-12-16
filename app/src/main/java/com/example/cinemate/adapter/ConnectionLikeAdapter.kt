package com.example.cinemate.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cinemate.R
import com.example.cinemate.databinding.MainMovieListItemBinding
import com.example.cinemate.mypage.CResponse
import com.example.cinemate.mypage.CResult

class ConnectionLikeAdapter(var clikesData: CResponse, var context: Context) :
    RecyclerView.Adapter<ConnectionLikeAdapter.ConnectionLikeViewHolder>() {
    private lateinit var viewBinding: MainMovieListItemBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConnectionLikeViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.main_movie_list_item, parent, false)
        viewBinding = MainMovieListItemBinding.bind(view)

        return ConnectionLikeViewHolder(MainMovieListItemBinding.bind(view))
    }

    override fun getItemCount(): Int {
        return clikesData.result.size
    }

    override fun onBindViewHolder(
        holder: ConnectionLikeViewHolder,
        position: Int
    ) {
        when (holder) {
            is RecyclerView.ViewHolder -> holder.bind(clikesData.result[position])
        }
    }

    inner class ConnectionLikeViewHolder(private val binding: MainMovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: CResult) {

            // Glide 사용하여 이미지 로드
            Glide.with(context).load(data.image)
                .apply(RequestOptions().override(120, 180))
                .error(R.drawable.cinemate_logo) // 로드 실패 시 표시할 이미지 지정
                .into(binding.mainMovieListImg)
            binding.mainMovieListTitle.text = data.title

        }

    }

    fun updateData(newMovieResponse: CResponse) {
        clikesData = newMovieResponse
        notifyDataSetChanged()
    }
}