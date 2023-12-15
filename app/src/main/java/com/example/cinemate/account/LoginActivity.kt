package com.example.cinemate.account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.cinemate.ApplicationClass
import com.example.cinemate.MainActivity
import com.example.cinemate.databinding.ActivityLoginBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class LoginActivity : AppCompatActivity() {

    //viewBing 설정
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //viewBinding
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //이전 로그인
        var jwt : String = ApplicationClass.sharedPreferences.getString("jwt","")

        if(jwt.length>0){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.activityLoginTvSignup.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
        })

        binding.activityLoginBtnLogin.setOnClickListener(View.OnClickListener {

            // 키보드 숨기기
            hideKeyboard()

            //3. call 객체 생성
            val email = binding.activityLoginEtEmail.text.toString()
            val password = binding.activityLoginEtPassword.text.toString()


            if(email.length > 0 && password.length > 0){
                connectLogin(this,LoginRequest(email,password),
                checkComplete = { successLogin(it) })
            }
        })

        binding.activityLoginBtnKakao.setOnClickListener(View.OnClickListener {
            kakaoLogin()
        })
    }

    private fun kakaoLogin() {
        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("LOGIN", "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i("LOGIN", "카카오계정으로 로그인 성공 ${token.accessToken}")
                connectKakao(this,KakaoSignupRequest(token.accessToken), checkComplete = {
                    successKakao(it)
                })

                //여기에서 엑세스 토큰을 받았는데, jwt가 없으면, 이 엑세스 토큰을 서버에 넘겨주는 과정 필요
                //이 엑세스 토큰을 서버에 넘겨주면, response로 jwt를 받는다. jwt가 있으면 자동 로그인시킨다.
                //카카오용 jwt를 따로 만들어서 저장하고, jwt 검사해서 만약 없으면, 회원가입 창으로 넘어가고,
                //회원가입 창에서는 유저 정보를 입력해서 넘겨준다. - 그냥 회원 가입 하기
                //있으면 자동으로 로그인 시킨다.
                //저장되어있는 jwt가 없으면 추가정보 입력 화면으로 이동

                if(ApplicationClass.sharedPreferences.getString("jwt","")==""){
                    Log.e("로그인 jwt 없음", "jwt 없음")
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                //저장되어있는 jwt가 있으면, 바로 자동 로그인
                else{
                    connectAuto(this,checkComplete = {successAuto(it)})
                }
            }
        }


        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@LoginActivity)) {
            UserApiClient.instance.loginWithKakaoTalk(this@LoginActivity) { token, error ->
                if (error != null) {
                    Log.e("LOGIN", "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this@LoginActivity, callback = callback)
                } else if (token != null) {
                    Log.i("LOGIN", "카카오톡으로 로그인 성공 ${token.accessToken}")
                    connectKakao(this,KakaoSignupRequest(token.accessToken), checkComplete = {
                        successKakao(it)
                    })

                    //여기에서 엑세스 토큰을 받았는데, jwt가 없으면, 이 엑세스 토큰을 서버에 넘겨주는 과정 필요
                    //이 엑세스 토큰을 서버에 넘겨주면, response로 jwt를 받는다. jwt가 있으면 자동 로그인시킨다.
                    //카카오용 jwt를 따로 만들어서 저장하고, jwt 검사해서 만약 없으면, 회원가입 창으로 넘어가고,
                    //회원가입 창에서는 유저 정보를 입력해서 넘겨준다. - 그냥 회원 가입 하기
                    //있으면 자동으로 로그인 시킨다.
                    //저장되어있는 jwt가 없으면 추가정보 입력 화면으로 이동

                    if(ApplicationClass.sharedPreferences.getString("jwt","")==""){
                        Log.e("로그인 jwt 없음", "jwt 없음")
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                    //저장되어있는 jwt가 있으면, 바로 자동 로그인
                    else{
                        connectAuto(this,checkComplete = {successAuto(it)})
                    }
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
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

    //카카오 성공시
    private fun successKakao(token: KakaoResult){
        var jwtToken = token.jwt.toString()

        ApplicationClass.sharedPreferences.setString("jwt",jwtToken)

        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    //자동 로그인 성공시
    private fun successAuto(token: AutoLoginResult){
        var jwtToken = token.jwt.toString()

        ApplicationClass.sharedPreferences.setString("jwt",jwtToken)

        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
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