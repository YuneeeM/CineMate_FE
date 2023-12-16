package com.example.cinemate.homepage

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.cinemate.ApplicationClass
import com.example.cinemate.retrofitUtil
import com.example.cinemate.searchpage.MovieResponse
import com.example.cinemate.searchpage.SearchService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

//api 호출
//1. retrofit 객체 생성
private val retrofit : Retrofit = retrofitUtil.getInstance()

private var jwtTokenValue : String ?= null

fun setJwt() {
    jwtTokenValue = ApplicationClass.sharedPreferences.getString("jwt","")
}

fun connectMainBoxoffice (context: Context, checkComplete: (it: MovieResponse) -> Unit){
    //2. service 객체 생성
    retrofit.create(HomeService::class.java)
        .mainMovie()
        //4. 네트워크 통신
        .enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                Log.d(ContentValues.TAG, "영화 순위 결과 -------------------------------------------")
                Log.d(ContentValues.TAG, "onResponse: ${response.body().toString()}")

                if(response.body() != null) {
                    if (response.body()!!.success){
                        val result = response.body() as MovieResponse
                        checkComplete(result)
                    }  else {
                        AlertDialog.Builder(context)
                            .setTitle("영화 순위 실패")
                            .setMessage(response.message())
                            .setPositiveButton("확인") { dialog, _ ->
                                // '확인'를 클릭했을 때는 아무런 동작도 하지 않고 다이얼로그를 닫습니다.
                                dialog.dismiss()
                            }
                            .show()
                    }

                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d(ContentValues.TAG, "영화 순위 결과 실패 -------------------------------------------")
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
}


fun connectGenre (context: Context, checkComplete: (it: MovieResponse) -> Unit){
    setJwt()
    //2. service 객체 생성
    retrofit.create(HomeService::class.java)
        .getGenreConnection(jwt = jwtTokenValue!!)
        //4. 네트워크 통신
        .enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                Log.d(ContentValues.TAG, "장르 결과 -------------------------------------------")
                Log.d(ContentValues.TAG, "onResponse: ${response.body().toString()}")

                if(response.body() != null) {
                    if (response.body()!!.success){
                        val result = response.body() as MovieResponse
                        checkComplete(result)
                    }  else {
                        AlertDialog.Builder(context)
                            .setTitle("장르 실패")
                            .setMessage(response.message())
                            .setPositiveButton("확인") { dialog, _ ->
                                // '확인'를 클릭했을 때는 아무런 동작도 하지 않고 다이얼로그를 닫습니다.
                                dialog.dismiss()
                            }
                            .show()
                    }

                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d(ContentValues.TAG, "장르 결과 실패 -------------------------------------------")
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
}