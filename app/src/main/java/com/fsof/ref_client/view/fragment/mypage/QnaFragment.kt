package com.fsof.ref_client.view.fragment.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fsof.ref_client.databinding.FragmentQnaBinding

class QnaFragment : Fragment() {

    private var _binding: FragmentQnaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View ? {
        _binding = FragmentQnaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // btnBack3 버튼에 대한 클릭 리스너 설정
        binding.btnBack3.setOnClickListener {
            // FragmentManager를 사용하여 현재 프래그먼트 스택에서 이 프래그먼트를 제거하고 이전 프래그먼트로 돌아갑니다.
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null  // Clear binding when view is destroyed
    }
}