package com.example.cinemate

import android.content.Context

class PreferenceUtil(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("cinemate_app",Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String):String {
        return sharedPreferences.getString(key,defValue).toString()
    }

    fun setString(key: String, str: String) {
        sharedPreferences.edit().putString(key,str).apply()
    }

    fun removeString(key:String) {
        sharedPreferences.edit().remove(key).apply()
    }

    fun clearString() {
        sharedPreferences.edit().clear().apply()
    }
}