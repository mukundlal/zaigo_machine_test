package com.example.test.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.test.R
import com.example.test.database.SharedPreference

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    lateinit var  preference: SharedPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        preference= SharedPreference(this)
        Handler().postDelayed({
            if (preference.getLoginStatus())
            {
                startActivity(Intent(this,MainActivity::class.java))
                this.finish()
            }else
            {
                startActivity(Intent(this,LoginActivity::class.java))
                this.finish()
            }
        },1000)

    }
}