package com.example.cinemate.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cinemate.R
import com.example.cinemate.databinding.MainMovieListItemBinding
import com.example.cinemate.peoplepage.ConnectionResponse
import com.example.cinemate.peoplepage.ConnectionResult

class ConnectionUserAdapter(var UserData: ConnectionResponse, var context: Context) :
RecyclerView.Adapter<ConnectionUserAdapter.ConnectionUserViewHolder>(){
    private lateinit var viewBinding: MainMovieListItemBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConnectionUserViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.main_movie_list_item, parent, false)
        viewBinding = MainMovieListItemBinding.bind(view)

        return ConnectionUserViewHolder(MainMovieListItemBinding.bind(view))
    }

    override fun getItemCount(): Int {
        return UserData.result.size
    }

    override fun onBindViewHolder(
        holder: ConnectionUserViewHolder,
        position: Int
    ) {
        when (holder) {
            is RecyclerView.ViewHolder -> holder.bind(UserData.result[position])
        }
    }

    inner class ConnectionUserViewHolder(private val binding: MainMovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ConnectionResult) {

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

    fun updateData(newMovieResponse: ConnectionResponse) {
        UserData = newMovieResponse
        notifyDataSetChanged()
    }
}