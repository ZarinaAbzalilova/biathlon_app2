package com.biathlonapp.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.biathlonapp.MainActivity
import com.biathlonapp.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "New token: $token")
        val prefs = getSharedPreferences("fcm", Context.MODE_PRIVATE)
        prefs.edit().putString("fcm_token", token).apply()
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d("FCM", "========== УВЕДОМЛЕНИЕ ПОЛУЧЕНО ==========")

        // Получаем заголовок и текст
        val title = message.notification?.title ?: "Биатлон"
        val body = message.notification?.body ?: "Новое уведомление"

        // Получаем данные из payload (ВАЖНО: это message.data, не message.notification.data)
        val raceId = message.data["race_id"] ?: ""
        val clickAction = message.data["click_action"] ?: ""

        Log.d("FCM", "Title: $title")
        Log.d("FCM", "Body: $body")
        Log.d("FCM", "RaceId: $raceId")
        Log.d("FCM", "ClickAction: $clickAction")
        Log.d("FCM", "Все данные: ${message.data}")

        showNotification(title, body, raceId)
    }

    private fun showNotification(title: String, body: String, raceId: String = "") {
        val channelId = "biathlon_notifications"
        val channelName = "Биатлон уведомления"

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Создаем канал для Android 8+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Уведомления о гонках и событиях"
                enableVibration(true)
                setShowBadge(true)
            }
            notificationManager.createNotificationChannel(channel)
        }

        // Создаем Intent для открытия MainActivity
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("open_race", true)
            putExtra("race_id", raceId)
        }

        Log.d("FCM", "Intent extras: open_race=true, race_id=$raceId")

        // PendingIntent
        val requestCode = System.currentTimeMillis().toInt()

        val pendingIntentFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }

        val pendingIntent = PendingIntent.getActivity(
            this, requestCode, intent, pendingIntentFlags
        )

        // Создаем уведомление
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_notification)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(requestCode, notification)
        Log.d("FCM", "Уведомление показано с ID: $requestCode")
    }
}