package com.fsof.project.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.fsof.project.view.fragment.list.ListFragment
import com.fsof.project.view.fragment.mypage.MypageFragment
import com.fsof.project.R
import com.fsof.project.view.fragment.recommend.RecommendFragment
import com.fsof.project.databinding.ActivityMainBinding
import com.fsof.project.view.fragment.camera.CameraFragment

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater, null, false) } // private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root) // setContentView(R.layout.activity_main)

        setBottomNavigationView()
        // 기본 프래그먼트 설정
        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.fragment_list
        }
    }

    private fun setBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment_camera -> {
                    replaceFragment(CameraFragment())
                    true
                }

                R.id.fragment_list -> {
                    replaceFragment(ListFragment())
                    true
                }

                R.id.fragment_mypage -> {
                    replaceFragment(MypageFragment())
                    true
                }

                R.id.fragment_recommend -> {
                    replaceFragment(RecommendFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(
            R.id.main_frame,
            fragment
        ).commit()
    }
}
