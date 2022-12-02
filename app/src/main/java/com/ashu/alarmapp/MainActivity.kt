package com.ashu.alarmapp

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService
import com.ashu.alarmapp.databinding.ActivityMainBinding
import com.ashu.alarmapp.reciever.reciever
import com.google.android.material.timepicker.MaterialTimePicker
import java.util.Calendar

private  lateinit var binding: ActivityMainBinding
lateinit var alarmManager: AlarmManager
lateinit var pendingIntent: PendingIntent
private lateinit var calendar: Calendar


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notificationChannelAdd()
        binding.setAlarmBtn.setOnClickListener {
            openTimePicker()
        }

        binding.isActive.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.cgDaysChips.visibility = View.VISIBLE
        }


    }

    private fun notificationChannelAdd() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "ALARM"
            val channel =
                NotificationChannel(
                    "Alarm App", name,
                    NotificationManager.IMPORTANCE_HIGH
                )

          val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }


    fun openTimePicker() {
      val picker = MaterialTimePicker
            .Builder()
            .setTitleText("Select a time")
            .build()
        picker.show(supportFragmentManager,"TIME_PICKER")
        picker.addOnPositiveButtonClickListener {
            binding.apply {
                textView2.visibility = View.VISIBLE
                alarmLl.visibility = View.VISIBLE
                alarmLl2.visibility = View.VISIBLE
            }


            var am_pm:String? = null
            var hour = picker.hour
            var minute = picker.minute
            var hourString =hour.toString()
            var minuteString = minute.toString()
            if (hour > 12){
              hourString = (hour - 12).toString()
                am_pm = "PM"
            } else am_pm= "AM"

            if (minute < 10){
                minuteString = "0$minute"
            }
            val formatedTime = "$hourString:$minuteString $am_pm"
            binding.alarmTv.text = formatedTime

            calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = hour
            calendar[Calendar.MINUTE] = minute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0

            setAlarm()

            Toast.makeText(this, "Alarm Set Successfully!", Toast.LENGTH_SHORT).show()

        }

    }

    private fun setAlarm() {
        val intent = Intent(this, reciever::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this,0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }


}