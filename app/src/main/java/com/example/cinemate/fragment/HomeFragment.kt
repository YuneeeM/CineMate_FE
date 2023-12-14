package com.example.cinemate.fragment

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
import com.example.cinemate.adapter.MainMovieAdapter
import com.example.cinemate.databinding.FragmentHomeBinding
import com.example.cinemate.homepage.MainMovieDataBitmap
import com.example.cinemate.homepage.connectMainBoxoffice
import com.example.cinemate.searchpage.MovieResponse

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //메인 영화 리스트
    private lateinit var mainMainMovieAdapter: MainMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Toolbar
        val toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar) //커스텀한 toolbar를 액션바로 사용

        //로그아웃 버튼 선택
        val logoutBtn : ImageButton = toolbar.findViewById(R.id.toolbar_logout_btn)
        logoutBtn.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("로그아웃")
                .setMessage("정말 로그아웃 하시겠습니까?")
                .setPositiveButton("네") { _, _ ->
                    // SharedPreferences에서 "jwt" 키를 삭제합니다.
                    ApplicationClass.sharedPreferences.removeString("jwt")
                }
                .setNegativeButton("아니요") { dialog, _ ->
                    // '아니요'를 클릭했을 때는 아무런 동작도 하지 않고 다이얼로그를 닫습니다.
                    dialog.dismiss()
                }
                .show()
        }

        initRecycler()

        //영화 순위 더보기 버튼 선택
        binding.mainMovieListMore.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.menu_home, MainMovieFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    //recyclerview 세팅
    private fun initRecycler() {
        //영화 메인 recyclerview 세팅
       connectMainBoxoffice ( checkComplete={successMainMovieDate(it)} )

    }

    private fun successMainMovieDate(it: MovieResponse) {
        mainMainMovieAdapter = MainMovieAdapter(it,requireContext())
        binding.mainMovieListRecyclerview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.mainMovieListRecyclerview.adapter=mainMainMovieAdapter


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}