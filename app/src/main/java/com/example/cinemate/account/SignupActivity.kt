package com.example.cinemate.account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.cinemate.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    //viewBing 설정
    private var _binding: ActivitySignupBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //viewBinding
        _binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activitySignupBtnSignup.setOnClickListener(View.OnClickListener {

            // 키보드 숨기기
            hideKeyboard()

            val email = binding.activitySignupEtEmail.text.toString()
            val password = binding.activitySignupEtPassword.text.toString()
            val nickname = binding.activitySignupEtNickname.text.toString()
            val phone = binding.activitySignupEtPhone.text.toString()
            val genre = binding.activitySignupEtGenre.text.toString()

            connectSignup(this, SignupRequest(email!!, password!!, nickname!!, phone!!, genre!!),
                checkComplete = {
                    checkSignup(it)
                }
            )

        })

    }

    private fun checkSignup(isComplete: Boolean) {
        if (isComplete) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }


    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}