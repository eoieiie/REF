package com.fsof.ref_client.view.fragment.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.fsof.ref_client.databinding.FragmentMypageBinding
import com.fsof.ref_client.R
import com.fsof.ref_client.view.activity.AlarmActivity

class MypageFragment : Fragment() {

    private var _binding: FragmentMypageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using view binding
        _binding = FragmentMypageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup button listeners for each action in the MyPage menu
        binding.btnGo0.setOnClickListener {
            // Start AlarmActivity when the button is clicked
            val intent = Intent(activity, AlarmActivity::class.java)
            startActivity(intent)
        }

        // Setup other buttons to replace the current fragment with other fragments
        binding.btnGo1.setOnClickListener { replaceFragment(ChangeInfoFragment()) }
        binding.btnGo2.setOnClickListener { replaceFragment(HowToUseFragment()) }
        binding.btnGo3.setOnClickListener { replaceFragment(QnaFragment()) }
        binding.btnGo4.setOnClickListener { replaceFragment(ShareAppFragment()) }
        binding.btnGo5.setOnClickListener { replaceFragment(SendOpinionFragment()) }
        binding.btnGo6.setOnClickListener { replaceFragment(SupportFragment()) }
    }

    private fun replaceFragment(fragment: Fragment) {
        // Perform fragment transaction to replace the current fragment
        parentFragmentManager.commit {
            replace(R.id.mobile_app_main, fragment) // Ensure this container ID is correct
            addToBackStack(null) // Optional: add transaction to the back stack
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clean up the binding when view is destroyed to avoid memory leaks
        _binding = null
    }
}
