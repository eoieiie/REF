package com.fsof.project

import CameraFragment
import ListFragment
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.fsof.project.databinding.ActivityMainBinding
import java.util.Stack

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding  //변수이름을 ViewBinding -> binding으로 수정
    private val fragmentBackStack: Stack<Fragment> = Stack()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root) // setContentView(R.layout.activity_main)
        setBottomNavigationView()

        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.fragment_list //기본화면을 리스트로 설정함!
        }
    }
    private fun setBottomNavigationView() {
        binding.bottomNavigationView.setOnItemReselectedListener { item ->
            when (item.itemId) {

                R.id.fragment_camera -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.main_frame,
                        CameraFragment()
                    ).commit()
                    true
                }

                R.id.fragment_list -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.main_frame,
                        ListFragment()
                    ).commit()
                    true
                }

                R.id.fragment_mypage -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.main_frame,
                        MypageFragment()
                    ).commit()
                    true
                }

                R.id.fragment_recommend -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.main_frame,
                        RecommendFragment()
                    ).commit()
                    true
                }
                else -> false
            }
        }
    }
}