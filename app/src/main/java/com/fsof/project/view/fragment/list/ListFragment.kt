package com.fsof.project.view.fragment.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fsof.project.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        val testData = generateTestData() // 테스트 데이터 생성
        val adapter = MyAdapter(testData) // Adapter 생성
        recyclerView.adapter = adapter
    }

    private fun generateTestData(): List<ItemData> {
        val testData = mutableListOf<ItemData>()
        for (i in 1..20) {
            testData.add(ItemData(content = "Item $i"))
        }
        return testData
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
