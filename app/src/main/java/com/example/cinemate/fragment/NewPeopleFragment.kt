package com.example.cinemate.fragment

import android.content.Context
import android.content.Intent
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
import com.example.cinemate.ApplicationClass
import com.example.cinemate.R
import com.example.cinemate.account.LoginActivity
import com.example.cinemate.adapter.PeopleAdapter
import com.example.cinemate.databinding.FragmentNewPeopleBinding
import com.example.cinemate.peoplepage.ConnectionRequest
import com.example.cinemate.peoplepage.ConnectionResponse
import com.example.cinemate.peoplepage.connectPostData

class NewPeopleFragment : Fragment() {


    private var _binding: FragmentNewPeopleBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewPeopleBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Toolbar
        val toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar22)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        // 로그아웃 버튼 선택
        val logoutBtn: ImageButton = toolbar.findViewById(R.id.toolbar_logout_btn)
        logoutBtn.setOnClickListener {
            showLogoutDialog()
        }

        binding.newPeopleBtn.setOnClickListener {

            // 키보드 숨기기
            hideKeyboard()

            val title = binding.newPeopleTitle.text.toString()
            val address = binding.newPeopleAddress.text.toString()
            val theater = binding.newPeopleTheater.text.toString()
            val date = binding.newPeopleDate.text.toString()
            val content = binding.newPeopleContent.text.toString()

            if (title != null && address != null && theater != null && date != null && content != null) {

                connectPostData(
                    requireContext(),
                    ConnectionRequest(title, address, theater, date, content),
                    checkComplete = { checkPost(it) })
            }


        }


    }

    private fun checkPost(it: Boolean) {
        if (it) {
            // FragmentManager를 통해 Fragment 스택에서 현재 Fragment를 pop하고 이전 Fragment로 돌아감
            fragmentManager?.popBackStack()
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
