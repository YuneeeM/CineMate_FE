package com.example.cinemate.peoplepage

import android.content.ContentValues
import android.util.Log
import com.example.cinemate.ApplicationClass
import com.example.cinemate.retrofitUtil
import com.example.cinemate.searchpage.MovieResponse
import com.example.cinemate.searchpage.SearchService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.time.LocalDate

//api 호출
//1. retrofit 객체 생성
private val retrofit : Retrofit = retrofitUtil.getInstance()

private var jwtTokenValue : String ?= null

fun setJwt() {
    jwtTokenValue = ApplicationClass.sharedPreferences.getString("jwt","")
}

fun connectPeopleData (title:String, address:String, meatDate:String, checkComplete: (it: ConnectionResponse) -> Unit){
    //2. service 객체 생성
    retrofit.create(PeopleService::class.java)
        .searchConnection(title,address,meatDate)
        //4. 네트워크 통신
        .enqueue(object : Callback<ConnectionResponse> {
            override fun onResponse(call: Call<ConnectionResponse>, response: Response<ConnectionResponse>) {
                Log.d(ContentValues.TAG, "모임방 검색 결과 -------------------------------------------")
                Log.d(ContentValues.TAG, "onResponse: ${response.body().toString()}")

                if(response.body() != null) {
                    if (response.body()!!.success){
                        val result = response.body() as ConnectionResponse
                        checkComplete(result)
                    }

                }
            }

            override fun onFailure(call: Call<ConnectionResponse>, t: Throwable) {
                Log.d(ContentValues.TAG, "모임방 검색 결과 실패 -------------------------------------------")
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
}
