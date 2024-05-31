package com.fsof.project.view.fragment.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.fsof.project.databinding.FragmentMypageBinding
import com.fsof.project.R
import com.fsof.project.view.activity.AlarmActivity

class MypageFragment : Fragment() {

    private var _binding: FragmentMypageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMypageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGo0.setOnClickListener {
            val intent = Intent(activity, AlarmActivity::class.java)
            startActivity(intent)
        }

        binding.btnGo1.setOnClickListener {
            // 여기서 ChangeInfoFragment() 인스턴스를 생성하여 replaceFragment 메서드에 전달합니다.
            replaceFragment(ChangeInfoFragment())
        }
        binding.btnGo2.setOnClickListener {
            // 여기서 HowToUseFragment() 인스턴스를 생성하여 replaceFragment 메서드에 전달합니다.
            replaceFragment(HowToUseFragment())
        }
        binding.btnGo3.setOnClickListener {
            // 여기서 QnaFragment() 인스턴스를 생성하여 replaceFragment 메서드에 전달합니다.
            replaceFragment(QnaFragment())
        }
        binding.btnGo4.setOnClickListener {
            // 여기서 ShareAppFragment() 인스턴스를 생성하여 replaceFragment 메서드에 전달합니다.
            replaceFragment(ShareAppFragment())
        }
        binding.btnGo5.setOnClickListener {
            // 여기서 SendOpinionFragment() 인스턴스를 생성하여 replaceFragment 메서드에 전달합니다.
            replaceFragment(SendOpinionFragment())
        }
        binding.btnGo6.setOnClickListener {
            // 여기서 SupportFragment() 인스턴스를 생성하여 replaceFragment 메서드에 전달합니다.
            replaceFragment(SupportFragment())
        }
    }


    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.commit {
            // 'main_frame'은 프래그먼트를 교체할 뷰의 ID입니다.
            // 이 ID는 fragment_mypage.xml 내에 정의된 특정 레이아웃의 ID여야 합니다.
            replace(R.id.mobile_app_main, fragment)
            addToBackStack(null)  // 선택사항: 트랜잭션을 백 스택에 추가합니다.
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null  // Clear binding when view is destroyed
    }
}