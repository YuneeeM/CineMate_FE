package com.example.cinemate.peoplepage

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
import java.time.LocalDate

//api 호출
//1. retrofit 객체 생성
private val retrofit : Retrofit = retrofitUtil.getInstance()

private var jwtTokenValue : String ?= null

fun setJwt() {
    jwtTokenValue = ApplicationClass.sharedPreferences.getString("jwt","")
}

fun connectPeopleData (context: Context, title:String, address:String, meatDate:String, checkComplete: (it: ConnectionResponse) -> Unit){
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
                    } else {
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

            override fun onFailure(call: Call<ConnectionResponse>, t: Throwable) {
                Log.d(ContentValues.TAG, "모임방 검색 결과 실패 -------------------------------------------")
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
}


fun connectPostData (context: Context, connectionRequest: ConnectionRequest,  checkComplete: (isComplete: Boolean) -> Unit){
    setJwt()
    //2. service 객체 생성
    retrofit.create(PeopleService::class.java)
        .postConnection(jwt = jwtTokenValue!!,connectionRequest)
        //4. 네트워크 통신
        .enqueue(object : Callback<ConnectionResponse01> {
            override fun onResponse(call: Call<ConnectionResponse01>, response: Response<ConnectionResponse01>) {
                Log.d(ContentValues.TAG, "모임방 등록 결과 -------------------------------------------")
                Log.d(ContentValues.TAG, "onResponse: ${response.body().toString()}")

                if(response.body() != null) {
                    if (response.body()!!.success){
                        checkComplete(true)
                    } else {
                        AlertDialog.Builder(context)
                            .setTitle("모임방 등록 실패")
                            .setMessage(response.message())
                            .setPositiveButton("확인") { dialog, _ ->
                                // '확인'를 클릭했을 때는 아무런 동작도 하지 않고 다이얼로그를 닫습니다.
                                dialog.dismiss()
                            }
                            .show()
                    }

                }
            }

            override fun onFailure(call: Call<ConnectionResponse01>, t: Throwable) {
                Log.d(ContentValues.TAG, "모임방 등록 결과 실패 -------------------------------------------")
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
}



fun connectId (context: Context, connectionId : Long,  checkComplete: (it: ConnectionResponse01) -> Unit){
    setJwt()
    //2. service 객체 생성
    retrofit.create(PeopleService::class.java)
        .getIdConnection(connectionId)
        //4. 네트워크 통신
        .enqueue(object : Callback<ConnectionResponse01> {
            override fun onResponse(call: Call<ConnectionResponse01>, response: Response<ConnectionResponse01>) {
                Log.d(ContentValues.TAG, "모임방 조회 결과 -------------------------------------------")
                Log.d(ContentValues.TAG, "onResponse: ${response.body().toString()}")

                if(response.body() != null) {
                    if (response.body()!!.success){
                        val result = response.body() as ConnectionResponse01
                        checkComplete(result)
                    } else {
                        AlertDialog.Builder(context)
                            .setTitle("모임방 조회 실패")
                            .setMessage(response.message())
                            .setPositiveButton("확인") { dialog, _ ->
                                // '확인'를 클릭했을 때는 아무런 동작도 하지 않고 다이얼로그를 닫습니다.
                                dialog.dismiss()
                            }
                            .show()
                    }

                }
            }

            override fun onFailure(call: Call<ConnectionResponse01>, t: Throwable) {
                Log.d(ContentValues.TAG, "모임방 조회 결과 실패 -------------------------------------------")
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
}


fun connectPostComment (context: Context, connectionId: Long, body:String,  checkComplete: (isComplete: Boolean) -> Unit){
    setJwt()
    //2. service 객체 생성
    retrofit.create(PeopleService::class.java)
        .postComment(jwt = jwtTokenValue!!,connectionId, body)
        //4. 네트워크 통신
        .enqueue(object : Callback<CommentResponse01> {
            override fun onResponse(call: Call<CommentResponse01>, response: Response<CommentResponse01>) {
                Log.d(ContentValues.TAG, "댓글 등록 결과 -------------------------------------------")
                Log.d(ContentValues.TAG, "onResponse: ${response.body().toString()}")

                if(response.body() != null) {
                    if (response.body()!!.success){
                        checkComplete(true)
                    } else {
                        AlertDialog.Builder(context)
                            .setTitle("댓글 등록 실패")
                            .setMessage(response.message())
                            .setPositiveButton("확인") { dialog, _ ->
                                // '확인'를 클릭했을 때는 아무런 동작도 하지 않고 다이얼로그를 닫습니다.
                                dialog.dismiss()
                            }
                            .show()
                    }

                }
            }

            override fun onFailure(call: Call<CommentResponse01>, t: Throwable) {
                Log.d(ContentValues.TAG, "댓글 등록 결과 실패 -------------------------------------------")
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
}


fun connectComment (context: Context, connectionId: Long,checkComplete: (it: CommentResponse) -> Unit){
    //2. service 객체 생성
    retrofit.create(PeopleService::class.java)
        .getIdComment(connectionId)
        //4. 네트워크 통신
        .enqueue(object : Callback<CommentResponse> {
            override fun onResponse(call: Call<CommentResponse>, response: Response<CommentResponse>) {
                Log.d(ContentValues.TAG, "댓글 조회 검색 결과 -------------------------------------------")
                Log.d(ContentValues.TAG, "onResponse: ${response.body().toString()}")

                if(response.body() != null) {
                    if (response.body()!!.success){
                        val result = response.body() as CommentResponse
                        checkComplete(result)
                    } else {
                        AlertDialog.Builder(context)
                            .setTitle("댓글 조회 실패")
                            .setMessage(response.message())
                            .setPositiveButton("확인") { dialog, _ ->
                                // '확인'를 클릭했을 때는 아무런 동작도 하지 않고 다이얼로그를 닫습니다.
                                dialog.dismiss()
                            }
                            .show()
                    }

                }
            }

            override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
                Log.d(ContentValues.TAG, "모임방 검색 결과 실패 -------------------------------------------")
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
}


