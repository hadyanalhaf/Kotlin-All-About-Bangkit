package com.dicoding.courseschedule.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.data.Course
import com.dicoding.courseschedule.data.DataRepository
import com.dicoding.courseschedule.ui.home.HomeActivity
import com.dicoding.courseschedule.util.ID_REPEATING
import com.dicoding.courseschedule.util.NOTIFICATION_CHANNEL_ID
import com.dicoding.courseschedule.util.NOTIFICATION_CHANNEL_NAME
import com.dicoding.courseschedule.util.NOTIFICATION_ID
import com.dicoding.courseschedule.util.executeThread
import java.util.Calendar

class DailyReminder : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        executeThread {
            val repository = DataRepository.getInstance(context)
            val courses = repository?.getTodaySchedule()

            courses?.let {
                if (it.isNotEmpty()) showNotification(context, it)
            }
        }
    }

    //TODO 12 : Implement daily reminder for every 06.00 a.m using AlarmManager
    fun setDailyReminder(context: Context) {

        val alarms = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context,DailyReminder::class.java)

        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING,intent,PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        val calendar = Calendar.getInstance()

        calendar.set(Calendar.HOUR_OF_DAY,6)
        calendar.set(Calendar.MINUTE,0)
        calendar.set(Calendar.SECOND,0)

        alarms.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    fun cancelAlarm(context: Context) {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context,DailyReminder::class.java)

        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING,intent,PendingIntent.FLAG_IMMUTABLE)
        alarmManager.cancel(pendingIntent)

    }

    private fun showNotification(context: Context, content: List<Course>) {
        //TODO 13 : Show today schedules in inbox style notification & open HomeActivity when notification tapped

        val notification = NotificationCompat.InboxStyle()

        val timeString = context.resources.getString(R.string.notification_message_format)

        content.forEach {
            val courseData = String.format(timeString, it.startTime, it.endTime, it.courseName)
            notification.addLine(courseData)
        }

        val intent = Intent(context,HomeActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        val notifManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Course Akan Segera Dimulai :D"
            }
            notifManager.createNotificationChannel(channel)
        }

        val NotifBuild = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(context.getString(R.string.title_activity_list))
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_notifications)
            .setStyle(notification)
            .build()

        notifManager.notify(NOTIFICATION_ID, NotifBuild)
    }
}