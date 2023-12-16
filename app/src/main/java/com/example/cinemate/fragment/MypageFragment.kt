package com.example.cinemate.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinemate.ApplicationClass
import com.example.cinemate.R
import com.example.cinemate.account.LoginActivity
import com.example.cinemate.adapter.ConnectionUserAdapter
import com.example.cinemate.adapter.MovieLikeAdapter
import com.example.cinemate.databinding.FragmentMypageBinding
import com.example.cinemate.mypage.MovieLikeResponse
import com.example.cinemate.mypage.MypageResult
import com.example.cinemate.mypage.connectConnectionUser
import com.example.cinemate.mypage.connectMovieLike
import com.example.cinemate.mypage.connectMypageData
import com.example.cinemate.peoplepage.ConnectionResponse


class MypageFragment : Fragment() {

    private var _binding: FragmentMypageBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieLikeAdapter: MovieLikeAdapter

    private lateinit var connectionUserAdapter: ConnectionUserAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMypageBinding.inflate(inflater)

        initRecycler()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Toolbar
        val toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar04)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        // 로그아웃 버튼 선택
        val logoutBtn: ImageButton = toolbar.findViewById(R.id.toolbar_logout_btn)
        logoutBtn.setOnClickListener {
            showLogoutDialog()
        }

    }

    private fun initRecycler() {

        connectMypageData(requireContext(), checkComplete = { successMypageData(it) })
        // 영화 메인 RecyclerView 세팅
        connectMovieLike(requireContext(), checkComplete = { successMovieLike(it) })

        connectConnectionUser(requireContext(), checkComplete = {successConnectionUser(it)})
    }

    private fun successMypageData(it: MypageResult) {
        binding.mypageNickname.text = it.nickname
        binding.mypagePhone.text = "전화번호 :  ${it.phone}"
        binding.mypageGenre.text = "좋아하는 장르 :  ${it.genre}"
    }

    private fun successMovieLike(it: MovieLikeResponse) {
        if (!::movieLikeAdapter.isInitialized) {
            movieLikeAdapter = MovieLikeAdapter(it, requireContext())
            binding.mypageMovieListRecyclerview.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.mypageMovieListRecyclerview.adapter = movieLikeAdapter
        } else {
            movieLikeAdapter.updateData(it) // 기존 어댑터 업데이트 로직
        }

    }

    private fun successConnectionUser(it: ConnectionResponse) {

        if (!::connectionUserAdapter.isInitialized) {
            connectionUserAdapter = ConnectionUserAdapter(it, requireContext())
            binding.mypagePeopleListRecyclerview.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.mypagePeopleListRecyclerview.adapter = connectionUserAdapter
        } else {
            connectionUserAdapter.updateData(it) // 기존 어댑터 업데이트 로직
        }

    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("로그아웃")
            .setMessage("정말 로그아웃 하시겠습니까?")
            .setPositiveButton("네") { _, _ ->
                // SharedPreferences에서 "jwt" 키를 삭제합니다.
                ApplicationClass.sharedPreferences.removeString("jwt")

                // LoginActivity로 이동합니다.
                val intent = Intent(requireActivity(), LoginActivity::class.java)

                // 이전 액티비티를 모두 삭제하고 이 액티비티를 새로운 작업의 최상위로 만듭니다.
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                startActivity(intent)
            }
            .setNegativeButton("아니요") { dialog, _ ->
                // '아니요'를 클릭했을 때는 아무런 동작도 하지 않고 다이얼로그를 닫습니다.
                dialog.dismiss()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}