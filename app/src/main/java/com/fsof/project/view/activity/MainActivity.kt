package com.fsof.project.view.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.fsof.project.view.fragment.list.ListFragment
import com.fsof.project.view.fragment.mypage.MypageFragment
import com.fsof.project.view.fragment.recommend.RecommendFragment
import com.fsof.project.databinding.ActivityMainBinding

import com.fsof.project.R
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.fsof.project.controller.alarm.AlarmReceiver
import com.fsof.project.utils.Alarm.ALARM_TIME
import com.fsof.project.utils.Alarm.NOTIFICATION_ID
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater, null, false) }

    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    private val alarmTimes = listOf("24-05-29", "24-05-30") // 알람이 울릴 날짜들

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // 권한이 허용되었을 때의 동작
        } else {
            // 권한이 거부되었을 때의 동작
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root) // setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // 권한 요청
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(
            this, NOTIFICATION_ID, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // 알람을 울릴 날짜인지 확인하고 울릴 경우 알람 예약
        checkAndSetAlarm()

        setAlarm()

        setBottomNavigationView()

        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.fragment_list
        }
    }

    private fun setBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment_camera -> {
                    startActivity(Intent(this@MainActivity, CameraActivity::class.java)) // replaceFragment(CameraFragment())
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

    // 알람을 울릴 날짜인지 확인하고 울릴 경우 알람 예약
    private fun checkAndSetAlarm() {
        val currentDate = SimpleDateFormat("yy-MM-dd", Locale.getDefault()).format(Date())
        if (currentDate in alarmTimes) {
            setAlarm()
        }
    }

    // 알람 예약
    private fun setAlarm() {
        val triggerTime = (SystemClock.elapsedRealtime() + ALARM_TIME * 1000) // 예약 시간을 현재로부터 10초 후로 설정
        alarmManager.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            triggerTime,
            pendingIntent
        )
        Toast.makeText(this, "알람이 예약되었습니다.", Toast.LENGTH_SHORT).show()
    }

    // 알람 취소
    private fun cancelAlarm() {
        alarmManager.cancel(pendingIntent)
//        Toast.makeText(this, "알람이 취소되었습니다.", Toast.LENGTH_SHORT).show()
    }
}