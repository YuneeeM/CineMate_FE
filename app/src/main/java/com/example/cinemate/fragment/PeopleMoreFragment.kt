package com.example.cinemate.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cinemate.ApplicationClass
import com.example.cinemate.R
import com.example.cinemate.account.LoginActivity
import com.example.cinemate.adapter.CommentAdapter
import com.example.cinemate.adapter.MainMovieAdapter
import com.example.cinemate.databinding.FragmentPeopleMoreBinding
import com.example.cinemate.homepage.connectMainBoxoffice
import com.example.cinemate.mypage.connectPostConnectionLike
import com.example.cinemate.peoplepage.CommentResponse
import com.example.cinemate.peoplepage.ConnectionResponse01
import com.example.cinemate.peoplepage.ConnectionResult
import com.example.cinemate.peoplepage.connectComment
import com.example.cinemate.peoplepage.connectId
import com.example.cinemate.peoplepage.connectPostComment


class PeopleMoreFragment : Fragment() {

    private var _binding: FragmentPeopleMoreBinding? = null
    private val binding get() = _binding!!

    private var connectResultId: Long = 0

    private lateinit var commentAdapter: CommentAdapter

    companion object {
        private const val ARG_CONNECTION_ID = "arg_connection_id"

        fun newInstance(connectionId: String): PeopleMoreFragment {
            val fragment = PeopleMoreFragment()
            val args = Bundle()
            args.putString(ARG_CONNECTION_ID, connectionId)
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPeopleMoreBinding.inflate(inflater)

        loadConnectResultDetails()

        initRecycler()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Toolbar
        val toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar32)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        // 로그아웃 버튼 선택
        val logoutBtn: ImageButton = toolbar.findViewById(R.id.toolbar_logout_btn)
        logoutBtn.setOnClickListener {
            showLogoutDialog()
        }

        binding.peopleMoreCommentBtn.setOnClickListener {
            // 키보드 숨기기
            hideKeyboard()

            val body = binding.peopleMoreCommentEdit.text.toString()

            if (body.isNotEmpty()) { // 댓글이 비어 있지 않은지 확인
                connectPostComment(requireContext(), connectResultId, body) { success ->
                    if (success) {
                        // 새로운 댓글을 성공적으로 게시한 후에 업데이트된 댓글 데이터를 가져옵니다.
                        connectComment(requireContext(), connectResultId) { updatedCommentResponse ->
                            successComment(updatedCommentResponse)
                        }
                    }
                }
            }

            binding.peopleMoreCommentEdit.text.clear()
        }


    }

    private fun initRecycler() {

        connectComment(requireContext(),connectResultId, checkComplete = { successComment(it)})

    }

    private fun successComment(it: CommentResponse) {

        if (!::commentAdapter.isInitialized) {
            commentAdapter = CommentAdapter(it, requireContext())
            binding.peopleMoreCommentRecyclerview.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.peopleMoreCommentRecyclerview.adapter = commentAdapter
        } else {
            commentAdapter.updateData(it) // 기존 어댑터 업데이트 로직
        }

    }

    private fun successConnectComment(it: Boolean) {

        if (it) {
            System.out.println("성공")
        }

    }


    private fun loadConnectResultDetails() {
        val connectionIdString: String? = arguments?.getString(ARG_CONNECTION_ID)
        if (connectionIdString != null) {
            try {
                connectResultId = connectionIdString.toLong()

                loadMovieDetails(connectResultId)
            } catch (e: NumberFormatException) {

                e.printStackTrace()
            }
        }
    }


    private fun loadMovieDetails(connectionId: Long) {

        connectId(requireContext(), connectionId, checkComplete = { successConnectId(it) })

    }

    private fun successConnectId(it: ConnectionResponse01) {

        Glide.with(requireContext()).load(it.result.image)
            .apply(RequestOptions().override(150, 200)).error(R.drawable.cinemate_logo)
            .into(binding.peopleMoreMovieListImg)

        binding.peopleMoreMovieListNickname.text = it.result.nickname
        binding.peopleMoreMovieListCreateAt.text = it.result.createAt
        binding.peopleMoreMovieListTitle.text = it.result.title
        binding.peopleMoreMovieListTown.text = it.result.address
        binding.peopleMoreMovieListTheater.text = it.result.theather
        binding.peopleMoreMovieListPubdate.text = it.result.meatDate
        binding.peopleMoreMovieListContent.text = it.result.sentence

        binding.peopleMovieLikeBtn.setOnClickListener {
            val isSelected = !binding.peopleMovieLikeBtn.isSelected
            binding.peopleMovieLikeBtn.isSelected = isSelected

            if (isSelected) {
                val color = ContextCompat.getColorStateList(requireContext(), R.color.colorSelected)
                binding.peopleMovieLikeBtn.imageTintList = color

                connectPostConnectionLike(requireContext(), connectResultId, checkComplete = {
                    checkPostConnectionLike(it)
                })
            } else {
                val color =
                    ContextCompat.getColorStateList(requireContext(), R.color.colorUnselected1)
                binding.peopleMovieLikeBtn.imageTintList = color
            }


        }

    }

    private fun checkPostConnectionLike(it: Boolean) {
        if (it) {
            System.out.println("성공")
        }

    }


    private fun showLogoutDialog() {
        AlertDialog.Builder(requireContext()).setTitle("로그아웃").setMessage("정말 로그아웃 하시겠습니까?")
            .setPositiveButton("네") { _, _ ->
                // SharedPreferences에서 "jwt" 키를 삭제합니다.
                ApplicationClass.sharedPreferences.removeString("jwt")

                // LoginActivity로 이동합니다.
                val intent = Intent(requireActivity(), LoginActivity::class.java)

                // 이전 액티비티를 모두 삭제하고 이 액티비티를 새로운 작업의 최상위로 만듭니다.
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                startActivity(intent)
            }.setNegativeButton("아니요") { dialog, _ ->
                // '아니요'를 클릭했을 때는 아무런 동작도 하지 않고 다이얼로그를 닫습니다.
                dialog.dismiss()
            }.show()
    }

    //키보드 숨기기
    private fun hideKeyboard() {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            requireView().windowToken, InputMethodManager.HIDE_NOT_ALWAYS
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}