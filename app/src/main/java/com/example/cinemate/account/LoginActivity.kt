package com.example.cinemate.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.cinemate.ApplicationClass
import com.example.cinemate.MainActivity
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

        binding.activityLoginBtnLogin.setOnClickListener(View.OnClickListener {
            //3. call 객체 생성
            val email = binding.activityLoginEtEmail.text.toString()
            val password = binding.activityLoginEtPassword.text.toString()



            if(email.length > 0 && password.length > 0){
                connectLogin(LoginRequest(email,password),
                checkComplete = { successLogin(it) })
            }
        })
    }

    //로그인 성공시
    private fun successLogin( token : LoginResult) {
        //jwt 저장
        var jwtToken = token.jwt.toString()

        ApplicationClass.sharedPreferences.setString("jwt",jwtToken)

        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}