package com.example.cinemate.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cinemate.R
import com.example.cinemate.databinding.PeopleListItemBinding
import com.example.cinemate.fragment.PeopleMoreFragment
import com.example.cinemate.peoplepage.ConnectionResponse
import com.example.cinemate.peoplepage.ConnectionResult

class PeopleAdapter(var peopleDate: ConnectionResponse, var context: Context) :
    RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder>() {
    private lateinit var viewBinding: PeopleListItemBinding

    override fun getItemCount(): Int {
        return peopleDate.result.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PeopleViewHolder {
        viewBinding =
            PeopleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PeopleViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: PeopleAdapter.PeopleViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> holder.bind(peopleDate.result[position])
        }
    }

    inner class PeopleViewHolder(private val binding: PeopleListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ConnectionResult) {

            Glide.with(context).load(data.image)
                .apply(RequestOptions().override(120, 180))
                .error(R.drawable.cinemate_logo) // 로드 실패 시 표시할 이미지 지정
                .into(binding.peopleMovieListImg)

            binding.peopleMovieListTitle.text = data.title
            binding.peopleMovieListTown.text = data.address
            binding.peopleMovieListTheater.text=data.theather
            binding.peopleMovieListPubdate.text ="관람날 : ${data.meatDate}"
            binding.peopleMovieListContent.text = data.sentence

            // 해당 connectResult를 가지고 peopleMoreFragment 이동
            binding.root.setOnClickListener {
                val fragment = PeopleMoreFragment.newInstance(data.connectionId) // data는 ConnectionResult 객체입니다.
                val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                transaction.replace(R.id.f_people, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }




        }
    }

    fun updateData(newConnectionResponse: ConnectionResponse) {
        peopleDate = newConnectionResponse
        notifyDataSetChanged()
    }
}