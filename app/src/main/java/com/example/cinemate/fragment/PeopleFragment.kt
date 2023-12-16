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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinemate.ApplicationClass
import com.example.cinemate.R
import com.example.cinemate.account.LoginActivity
import com.example.cinemate.adapter.PeopleAdapter
import com.example.cinemate.databinding.FragmentPeopleBinding
import com.example.cinemate.peoplepage.ConnectionResponse
import com.example.cinemate.peoplepage.connectPeopleData


class PeopleFragment : Fragment() {

    private var _binding:FragmentPeopleBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvAdapter: PeopleAdapter


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
        val toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar02)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        // 로그아웃 버튼 선택
        val logoutBtn: ImageButton = toolbar.findViewById(R.id.toolbar_logout_btn)
        logoutBtn.setOnClickListener {
            showLogoutDialog()
        }

        binding.peopleMakeBtn.setOnClickListener{
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.f_people, NewPeopleFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.peopleSearchBtn.setOnClickListener{
            if(binding.peopleMovieEdit.text.isNotEmpty() &&
                binding.peopleTownEdit.text.isNotEmpty()){
                connectPeopleData(requireContext(),binding.peopleMovieEdit.text.toString(),
                    binding.peopleTownEdit.text.toString(),
                    binding.peopleDateEdit.text.toString(), checkComplete ={
                        successPeopleData(it)
                    }
                )
            }

            // 키보드 숨기기
            hideKeyboard()
        }


    }

    private fun successPeopleData(it: ConnectionResponse) {
        if (!::rvAdapter.isInitialized) {
            rvAdapter = PeopleAdapter(it, requireContext())
            binding.peopleListRecyclerview.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.peopleListRecyclerview.adapter = rvAdapter
        } else {
            rvAdapter.updateData(it)
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