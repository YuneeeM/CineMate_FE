package com.example.cinemate.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cinemate.databinding.CommentListItemBinding
import com.example.cinemate.peoplepage.CommentResponse
import com.example.cinemate.peoplepage.CommentResult

class CommentAdapter(var commentData: CommentResponse, var context: Context) :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    private lateinit var viewBinding: CommentListItemBinding

    override fun getItemCount(): Int {
        return commentData.result.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentViewHolder {
        viewBinding =
            CommentListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: CommentAdapter.CommentViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> holder.bind(commentData.result[position])
        }
    }

    inner class CommentViewHolder(private val binding: CommentListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CommentResult) {
            binding.commentNickname.text = data.nickname
            binding.commentCreatedate.text = data.createAt
            binding.commentBody.text = data.body
        }
    }

    fun updateData(newMovieResponse: CommentResponse) {
        commentData = newMovieResponse
        notifyDataSetChanged()
    }
}