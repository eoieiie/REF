package com.fsof.project.view.activity

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.fsof.project.controller.alarm.AlarmReceiver
import com.fsof.project.databinding.ActivityAlarmBinding
import com.fsof.project.model.datasource.IngredientDatabase
import java.text.SimpleDateFormat
import java.util.*

class AlarmActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAlarmBinding.inflate(layoutInflater, null, false) }

    private lateinit var alarmManager: AlarmManager
    private lateinit var ingredientDatabase: IngredientDatabase
    private lateinit var sharedPreferences: SharedPreferences

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
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        ingredientDatabase = IngredientDatabase.getInstance(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        binding.toggleAlarm.isChecked = getAlarmState()

        binding.toggleAlarm.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkAndSetAlarm()
                saveAlarmState(true)
            } else {
                cancelAllAlarms()
                saveAlarmState(false)
            }
        }

        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"))
        binding.timePicker.setIs24HourView(true)
        binding.timePicker.hour = calendar.get(Calendar.HOUR_OF_DAY)
        binding.timePicker.minute = calendar.get(Calendar.MINUTE)

        binding.timePicker.setOnTimeChangedListener { _, _, _ ->
            if (binding.toggleAlarm.isChecked) {
                cancelAllAlarms()
                checkAndSetAlarm()
            }
        }
    }

    private fun checkAndSetAlarm() {
        val ingredientList = ingredientDatabase.ingredientsDao().selectAll()
        val groupedByDate = ingredientList.groupBy { it.expiration_date }
        Log.d("Alarm", groupedByDate.toString())
        groupedByDate.forEach { (expirationDate, ingredients) ->
            val ingredientNames = ingredients.map { it.name }
            setAlarm(expirationDate, ingredientNames)
        }
    }

    private fun setAlarm(expirationDate: String, ingredientNames: List<String>) {
        val dateFormat = SimpleDateFormat("yy-MM-dd", Locale.getDefault())
        val date = dateFormat.parse(expirationDate)

        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul")).apply {
            time = date as Date
            set(Calendar.HOUR_OF_DAY, binding.timePicker.hour)
            set(Calendar.MINUTE, binding.timePicker.minute)
            set(Calendar.SECOND, 0)
            // 소비기한 알람을 D-3 으로 설정
            add(Calendar.DAY_OF_MONTH, -3)
        }

        val intent = Intent(this, AlarmReceiver::class.java).apply {
            putExtra("expirationDate", expirationDate)
            putStringArrayListExtra("ingredients", ArrayList(ingredientNames))
        }

        val pendingIntent = PendingIntent.getBroadcast(
            this, expirationDate.hashCode(), intent,
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )

        Log.d("Alarm", "${expirationDate}에 알람이 예약되었습니다. 알람 시간: ${calendar.time}")
    }

    private fun cancelAllAlarms() {
        val ingredientList = ingredientDatabase.ingredientsDao().selectAll().map { it.expiration_date }.distinct()
        ingredientList.forEach { expirationDate ->
            cancelAlarm(expirationDate)
        }
    }

    private fun cancelAlarm(expirationDate: String) {
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this, expirationDate.hashCode(), intent,
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.cancel(pendingIntent)
        Log.d("Alarm", "${expirationDate}에 알람이 취소되었습니다.")
    }

    private fun saveAlarmState(isEnabled: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("alarm_enabled", isEnabled)
        editor.apply()
    }

    private fun getAlarmState(): Boolean {
        return sharedPreferences.getBoolean("alarm_enabled", false)
    }
}