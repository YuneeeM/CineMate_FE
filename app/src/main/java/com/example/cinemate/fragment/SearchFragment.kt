package com.example.cinemate.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinemate.ApplicationClass
import com.example.cinemate.R
import com.example.cinemate.adapter.SearchAdapter
import com.example.cinemate.databinding.FragmentSearchBinding
import com.example.cinemate.searchpage.MovieResponse
import com.example.cinemate.searchpage.connectSearchData


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater)
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

        binding.searchMovieBtn.setOnClickListener {
            if (binding.searchMovieEdit.text.isNotEmpty()) {
                connectSearchData(binding.searchMovieEdit.text.toString()) { successSearchDate(it) }
            }

            // 키보드 숨기기
            hideKeyboard()
        }
    }

    private fun showLogoutDialog() {
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

    private fun successSearchDate(it: MovieResponse) {
        if (!::rvAdapter.isInitialized) {
            rvAdapter = SearchAdapter(it, requireContext())
            binding.searchMovieListRecyclerview.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.searchMovieListRecyclerview.adapter = rvAdapter
        } else {
            rvAdapter.updateData(it)
        }
    }

    //키보드 숨기기
    private fun hideKeyboard() {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            requireView().windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}