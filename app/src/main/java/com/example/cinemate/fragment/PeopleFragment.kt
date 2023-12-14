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
import com.example.cinemate.ApplicationClass
import com.example.cinemate.R
import com.example.cinemate.databinding.FragmentPeopleBinding
import com.example.cinemate.searchpage.connectSearchData


class PeopleFragment : Fragment() {

    private var _binding:FragmentPeopleBinding? = null
    private val binding get() = _binding!!



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPeopleBinding.inflate(inflater)
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



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}