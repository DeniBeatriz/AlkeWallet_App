package com.example.alkewallet.controller

import android.content.Context

class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences("alke_wallet_session", Context.MODE_PRIVATE)

    fun saveUserSession(name: String, email: String) {
        prefs.edit()
            .putBoolean("is_logged_in", true)
            .putString("user_name", name)
            .putString("user_email", email)
            .apply()
    }



    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("is_logged_in", false)
    }



    fun getUserName(): String {
        return prefs.getString("user_name", "") ?: ""
    }

    fun getUserEmail(): String {
        return prefs.getString("user_email", "") ?: ""
    }

    fun saveProfileImageUrl(url: String) {
        prefs.edit().putString("profile_image_url", url).apply()
    }

    fun getProfileImageUrl(): String {
        return prefs.getString(
            "profile_image_url",
            "https://i.pravatar.cc/300?img=12"
        ) ?: "https://i.pravatar.cc/300?img=12"
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}