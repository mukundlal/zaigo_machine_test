package com.example.test.database

import android.content.Context
import android.content.Context.MODE_PRIVATE

class SharedPreference(val ctx:Context) {

    val pref = ctx.getSharedPreferences("TEST", MODE_PRIVATE)
    fun setLogin(isLoggedIn:Boolean)
    {
        pref.edit().putBoolean("LOGGEDIN",isLoggedIn).apply()
    }
    fun getLoginStatus():Boolean
    {
        return pref.getBoolean("LOGGEDIN",false)
    }
}