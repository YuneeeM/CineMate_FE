package com.example.cinemate.mypage

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.cinemate.ApplicationClass
import com.example.cinemate.peoplepage.ConnectionResponse
import com.example.cinemate.retrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


//api 호출
//1. retrofit 객체 생성
private val retrofit: Retrofit = retrofitUtil.getInstance()

private var jwtTokenValue: String? = null

fun setJwt() {
    jwtTokenValue = ApplicationClass.sharedPreferences.getString("jwt", "")
}

fun connectMypageData(context: Context, checkComplete: (it: MypageResult) -> Unit) {
    setJwt()
    //2. service 객체 생성
    retrofit.create(MypageService::class.java)
        .getMypage(jwt = jwtTokenValue!!)
        //4. 네트워크 통신
        .enqueue(object : Callback<MypageResponse> {
            override fun onResponse(
                call: Call<MypageResponse>,
                response: Response<MypageResponse>
            ) {
                Log.d(ContentValues.TAG, "마이페이지 조회 결과 -------------------------------------------")
                Log.d(ContentValues.TAG, "onResponse: ${response.body().toString()}")

                if (response.body() != null) {
                    if (response.body()!!.success) {
                        val result = response.body()!!.result as MypageResult
                        checkComplete(result)
                    } else {
                        AlertDialog.Builder(context)
                            .setTitle("마이페이지 조회 실패")
                            .setMessage(response.message().toString())
                            .setPositiveButton("확인") { dialog, _ ->
                                // '확인'를 클릭했을 때는 아무런 동작도 하지 않고 다이얼로그를 닫습니다.
                                dialog.dismiss()
                            }
                            .show()
                    }

                }
            }

            override fun onFailure(call: Call<MypageResponse>, t: Throwable) {
                Log.d(
                    ContentValues.TAG,
                    "마이페이지 검색 결과 실패 -------------------------------------------"
                )
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
}


fun connectMovieLike(context: Context, checkComplete: (it: MovieLikeResponse) -> Unit) {
    setJwt()
    //2. service 객체 생성
    retrofit.create(MypageService::class.java)
        .getLikesMovie(jwt = jwtTokenValue!!)
        //4. 네트워크 통신
        .enqueue(object : Callback<MovieLikeResponse> {
            override fun onResponse(
                call: Call<MovieLikeResponse>,
                response: Response<MovieLikeResponse>
            ) {
                Log.d(
                    ContentValues.TAG,
                    "마이페이지 좋아요 조회 결과 -------------------------------------------"
                )
                Log.d(ContentValues.TAG, "onResponse: ${response.body().toString()}")

                if (response.body() != null) {
                    if (response.body()!!.success) {
                        val result = response.body() as MovieLikeResponse
                        checkComplete(result)
                    } else {
                        AlertDialog.Builder(context)
                            .setTitle("마이페이지 좋아요 조회 실패")
                            .setMessage(response.message().toString())
                            .setPositiveButton("확인") { dialog, _ ->
                                // '확인'를 클릭했을 때는 아무런 동작도 하지 않고 다이얼로그를 닫습니다.
                                dialog.dismiss()
                            }
                            .show()
                    }

                }
            }

            override fun onFailure(call: Call<MovieLikeResponse>, t: Throwable) {
                Log.d(
                    ContentValues.TAG,
                    "마이페이지 좋아요 검색 결과 실패 -------------------------------------------"
                )
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
}


fun connectConnectionUser(context: Context, checkComplete: (it: ConnectionResponse) -> Unit) {
    setJwt()
    //2. service 객체 생성
    retrofit.create(MypageService::class.java)
        .getConnectionUser(jwt = jwtTokenValue!!)
        //4. 네트워크 통신
        .enqueue(object : Callback<ConnectionResponse> {
            override fun onResponse(
                call: Call<ConnectionResponse>,
                response: Response<ConnectionResponse>
            ) {
                Log.d(
                    ContentValues.TAG,
                    "마이페이지 모임방 조회 결과 -------------------------------------------"
                )
                Log.d(ContentValues.TAG, "onResponse: ${response.body().toString()}")

                if (response.body() != null) {
                    if (response.body()!!.success) {
                        val result = response.body() as ConnectionResponse
                        checkComplete(result)
                    } else {
                        AlertDialog.Builder(context)
                            .setTitle("마이페이지 모임방 조회 실패")
                            .setMessage(response.message().toString())
                            .setPositiveButton("확인") { dialog, _ ->
                                // '확인'를 클릭했을 때는 아무런 동작도 하지 않고 다이얼로그를 닫습니다.
                                dialog.dismiss()
                            }
                            .show()
                    }

                }
            }

            override fun onFailure(call: Call<ConnectionResponse>, t: Throwable) {
                Log.d(
                    ContentValues.TAG,
                    "마이페이지 모임방 검색 결과 실패 -------------------------------------------"
                )
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
}



fun connectPostMovieLike(context: Context,title:String, checkComplete: (isComplete: Boolean) -> Unit) {
    setJwt()
    //2. service 객체 생성
    retrofit.create(MypageService::class.java)
        .postLikeMovie(jwt = jwtTokenValue!!,title)
        //4. 네트워크 통신
        .enqueue(object : Callback<MovieLikeResponse> {
            override fun onResponse(
                call: Call<MovieLikeResponse>,
                response: Response<MovieLikeResponse>
            ) {
                Log.d(
                    ContentValues.TAG,
                    "좋아요 등록 결과 -------------------------------------------"
                )
                Log.d(ContentValues.TAG, "onResponse: ${response.body().toString()}")

                if (response.body() != null) {
                    if (response.body()!!.success) {
                        checkComplete(true)
                    } else {
                        AlertDialog.Builder(context)
                            .setTitle("좋아요 등록 실패")
                            .setMessage(response.message().toString())
                            .setPositiveButton("확인") { dialog, _ ->
                                // '확인'를 클릭했을 때는 아무런 동작도 하지 않고 다이얼로그를 닫습니다.
                                dialog.dismiss()
                            }
                            .show()
                    }

                }
            }

            override fun onFailure(call: Call<MovieLikeResponse>, t: Throwable) {
                Log.d(
                    ContentValues.TAG,
                    "좋아요 등록 결과 실패 -------------------------------------------"
                )
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
}

