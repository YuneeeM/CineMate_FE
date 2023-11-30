package com.example.cinemate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cinemate.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    //viewBing 설정
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //viewBinding
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}