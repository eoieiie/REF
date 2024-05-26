package com.fsof.project.view.fragment.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fsof.project.databinding.FragmentListBinding
import androidx.recyclerview.widget.GridLayoutManager

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView 설정
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        // 추가하기 버튼 클릭 시 동작
        binding.buttonAdd.setOnClickListener {
            // 추가하기 버튼 클릭 시 동작 구현
            // 새로운 아이템을 리스트에 추가하는 코드를 여기에 작성
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
