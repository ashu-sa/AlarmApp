package com.ashu.alarmapp.reciever

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ashu.alarmapp.Destination
import com.ashu.alarmapp.MainActivity
import com.ashu.alarmapp.R
import com.ashu.alarmapp.pendingIntent
import java.util.*

class reciever:BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        val intent=Intent(context, Destination::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val pendingIntent=PendingIntent.getActivity(context,
            0,intent,0)



        val builder= NotificationCompat.Builder(context!!,"Alarm App")
            .setContentText("Your Alarm")
            .setSmallIcon(R.drawable.alarm)
            .setColor(Color.YELLOW)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setOngoing (true)
//            .addAction(R.drawable.ic_stop,"Stop",stopAlarmTone(context))
//            .addAction(R.drawable.ic_baseline_snooze_24,"Snooze",snoozeAlarm(context))

       val notificationManager = NotificationManagerCompat.from(context)
           notificationManager.notify(123,builder.build())

    }

//    private fun stopAlarmTone(context: Context): PendingIntent? {
//    }
//    private fun snoozeAlarm(context: Context): PendingIntent? {
//
//    }


}