package com.example.cinemate.account

import android.content.Intent
import android.os.Bundle
import android.view.View
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
            val email = binding.activitySignupEtEmail.text.toString()
            val password = binding.activitySignupEtPassword.text.toString()
            val nickname = binding.activitySignupEtNickname.text.toString()
            val phone = binding.activitySignupEtPhone.text.toString()
            val genre = binding.activitySignupEtGenre.text.toString()

            connectSignup(SignupRequest(email!!,password!!,nickname!!,phone!!,genre!!),
                checkComplete = {
                    checkSignup(it)
                }
            )

        })

    }

    private fun checkSignup(isComplete: Boolean) {
        if(isComplete){
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }


}