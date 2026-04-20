package com.biathlonapp.service

import android.content.Context
import com.google.firebase.messaging.FirebaseMessaging

class BiathlonFirebaseService(private val context: Context) {

    fun getFcmToken(callback: (String?) -> Unit) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback(task.result)
            } else {
                callback(null)
            }
        }
    }

    fun saveTokenToServer(token: String) {
        val prefs = context.getSharedPreferences("fcm", Context.MODE_PRIVATE)
        prefs.edit().putString("fcm_token", token).apply()
    }
}