package com.example.cinemate

import android.app.Application
import android.content.SharedPreferences
import retrofit2.Retrofit

//앱 실행될때 1번만 실행되게 함
class ApplicationClass : Application() {
  companion object {
      lateinit var sharedPreferences : PreferenceUtil
          private set
  }

    override fun onCreate() {
        super.onCreate()

        sharedPreferences = PreferenceUtil(applicationContext)
    }
}