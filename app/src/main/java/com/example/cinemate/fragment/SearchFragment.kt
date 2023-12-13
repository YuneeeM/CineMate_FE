package com.example.cinemate.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinemate.ApplicationClass
import com.example.cinemate.R
import com.example.cinemate.adapter.SearchAdapter
import com.example.cinemate.databinding.FragmentSearchBinding
import com.example.cinemate.searchpage.SearchData
import com.google.gson.GsonBuilder
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.URL
import java.net.URLEncoder


class SearchFragment : Fragment() {

    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!

    val clientId = ""
    val clientSecret = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Toolbar
        val toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar) //커스텀한 toolbar를 액션바로 사용

        //로그아웃 버튼 선택
        val logoutBtn : ImageButton = toolbar.findViewById(R.id.toolbar_logout_btn)
        logoutBtn.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("로그아웃")
                .setMessage("정말 로그아웃 하시겠습니까?")
                .setPositiveButton("네") { _, _ ->
                    // SharedPreferences에서 "jwt" 키를 삭제합니다.
                    ApplicationClass.sharedPreferences.removeString("jwt")
                }
                .setNegativeButton("아니요") { dialog, _ ->
                    // '아니요'를 클릭했을 때는 아무런 동작도 하지 않고 다이얼로그를 닫습니다.
                    dialog.dismiss()
                }
                .show()
        }

        binding.searchMovieBtn.setOnClickListener {
            if(binding.searchMovieEdit.text.isEmpty()){
                return@setOnClickListener
            }

            binding.searchMovieListRecyclerview.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL, false)
            binding.searchMovieListRecyclerview.setHasFixedSize(true)

            fetchJson(binding.searchMovieEdit.text.toString())

        }

    }

    fun fetchJson(vararg p0: String){
        val text = URLEncoder.encode("${p0[0]}", "UTF-8")
        val url = URL("https://openapi.naver.com/v1/search/movie.json?query=${text}&display=10&start=1&genre=")
        val formBody = FormBody.Builder()
            .add("query","${text}")
            .add("display","10")
            .add("start","1")
            .add("genre","1")
            .build()

        val request = Request.Builder()
            .url(url)
            .addHeader("X-Naver-Client-Id", clientId)
            .addHeader("X-Naver-Client-Secret", clientSecret)
            .method("GET", null)
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()

                val gson = GsonBuilder().create()
                val searchData = gson.fromJson(body, SearchData::class.java)

                activity?.runOnUiThread {
                    binding.searchMovieListRecyclerview.adapter = SearchAdapter(searchData)
                    binding.searchMovieEdit.setText("")
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}