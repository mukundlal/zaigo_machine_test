package com.example.test.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.test.R
import com.example.test.common.hide
import com.example.test.common.isValidInput
import com.example.test.common.show
import com.example.test.database.SharedPreference
import com.example.test.models.LoginResponse
import com.example.test.network.RetrofitClient
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.jar.JarOutputStream


class LoginActivity : AppCompatActivity() {

    lateinit var  preference: SharedPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        preference= SharedPreference(this)

//        if (preference.getLoginStatus())
//        {
//            startActivity(Intent(this@LoginActivity,MainActivity::class.java))
//            this@LoginActivity.finish()
//        }


        loginBtn.setOnClickListener {

            etEmail.isValidInput() //validate fields
            etPassword.isValidInput() //validate fields
            loginUser() //Call userLogin api

        }




    }

    private fun loginUser() {

        if (etEmail.isValidInput()&&etPassword.isValidInput()) {
            loginBtn.hide()
            pbLoading.show()
            //Retrofit Call
            try {
                RetrofitClient.apiInterface.userLogin(
                    etEmail.text.toString(),
                    etPassword.text.toString()
                ).enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        loginBtn.show()
                        pbLoading.hide()
                        if (response.isSuccessful && response.body() != null) {
                            val data = response.body()!!
                            if (data.data != null) { //Check if login success or not

                                Toast.makeText(
                                    this@LoginActivity,
                                    data.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                                preference.setLogin(true) //Setting value in SharedPreference
                                startActivity(
                                    Intent(
                                        this@LoginActivity,
                                        MainActivity::class.java
                                    )
                                )// Change activity
                                this@LoginActivity.finish()
                            } else //Login credential failed
                            {
                                Toast.makeText(
                                    this@LoginActivity,
                                    data.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                "Login Error, try again",
                                Toast.LENGTH_SHORT
                            ).show();
                            // JSON Issue
                        }

                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                       //Api failure
                        loginBtn.show()
                        pbLoading.hide()
                    }
                })
            }catch (e:Exception){} //Exception
        }
    }
}


