package com.fsof.project.view.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
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
import android.util.Log // import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.fsof.project.controller.alarm.AlarmReceiver
import com.fsof.project.model.datasource.IngredientDatabase
import com.fsof.project.utils.Alarm.NOTIFICATION_ID
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater, null, false) }

    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    private lateinit var ingredientDatabase: IngredientDatabase

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.d("Alarm", "Permission Accepted")
        } else {
            Log.d("Alarm", "Permission Denied")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root) // setContentView(R.layout.activity_main)

        ingredientDatabase = IngredientDatabase.getInstance(this)

        // Request Permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(
            this, NOTIFICATION_ID, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        checkAndSetAlarm()

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

    private fun checkAndSetAlarm() {
        val ingredientList = ingredientDatabase.ingredientsDao().selectAll().map { it.expiration_date }.distinct()
        Log.d("Alarm", ingredientList.toString())
        ingredientList.forEach { expirationDate ->
            setAlarm(expirationDate)
        }
    }

    private fun setAlarm(expirationDate: String) {
        val dateFormat = SimpleDateFormat("yy-MM-dd", Locale.getDefault())
        val date = dateFormat.parse(expirationDate)

        val calendar = Calendar.getInstance().apply {
            time = date as Date // as Date TypeCasting
            set(Calendar.HOUR_OF_DAY, 10)
            set(Calendar.MINUTE, 15)
            set(Calendar.SECOND, 0)
        }

        val intent = Intent(this, AlarmReceiver::class.java).apply {
            putExtra("expirationDate", expirationDate)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            this, expirationDate.hashCode(), intent,
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )

        Log.d("Alarm", "${expirationDate}에 알람이 예약되었습니다.") // Toast.makeText(this, "${expirationDate}에 알람이 예약되었습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun cancelAlarm(expirationDate: String) {
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this, expirationDate.hashCode(), intent,
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.cancel(pendingIntent)
        Log.d("Alarm", "${expirationDate}에 알람이 취소되었습니다..") // Toast.makeText(this, "${expirationDate}에 알람이 취소되었습니다.", Toast.LENGTH_SHORT).show()
    }
}