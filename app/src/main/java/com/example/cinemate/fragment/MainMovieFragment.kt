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
import com.example.cinemate.adapter.MainMovieMoreAdapter
import com.example.cinemate.databinding.FragmentMainMovieBinding
import com.example.cinemate.homepage.connectMainBoxoffice
import com.example.cinemate.searchpage.MovieResponse


class MainMovieFragment : Fragment() {

    private var _binding: FragmentMainMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvAdapter: MainMovieMoreAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainMovieBinding.inflate(inflater)
        initRecycler()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Toolbar
        val toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        // 로그아웃 버튼 선택
        val logoutBtn: ImageButton = toolbar.findViewById(R.id.toolbar_logout_btn)
        logoutBtn.setOnClickListener {
            showLogoutDialog()
        }

    }

    private fun initRecycler() {
        // 영화 메인 RecyclerView 세팅
        connectMainBoxoffice { successMainMovieDate(it) }
    }

    private fun successMainMovieDate(it: MovieResponse) {
        if (!::rvAdapter.isInitialized) {
            rvAdapter = MainMovieMoreAdapter(it, requireContext())
            binding.mainMoreMovieListRecyclerview.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.mainMoreMovieListRecyclerview.adapter = rvAdapter
        } else {
            rvAdapter.updateData(it) // 기존 어댑터 업데이트 로직
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
                val intent = Intent(context, LoginActivity::class.java)

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