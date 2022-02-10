package com.example.test.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R
import com.example.test.adapters.RvAdapter
import com.example.test.common.hide
import com.example.test.common.show
import com.example.test.database.SharedPreference
import com.example.test.models.NewsResponse
import com.example.test.network.RetrofitClient2
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    lateinit var rvAdapter: RvAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvAdapter= RvAdapter()
        rvList.adapter=rvAdapter

        getApiData()//Fetch data from api

        btn_map.setOnClickListener {

            startActivity(Intent(this,MapsActivity::class.java))
        }
        btn_gallery.setOnClickListener {

            startActivity(Intent(this,GalleryActivity::class.java))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.logout->{
                AlertDialog.Builder(this)
                    .setTitle("Logout?")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("yes"){d,w->
                        SharedPreference(this).setLogin(false)
                        startActivity(Intent(this,LoginActivity::class.java))
                        this.finish()
                    }
                    .setNegativeButton("No"){d,w->
                        d.dismiss()
                    }
                    .show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun getApiData() {

        rv_loading.show()

        try {
            RetrofitClient2.apiInterface.getNews()
                .enqueue(object : Callback<NewsResponse> {
                    override fun onResponse(
                        call: Call<NewsResponse>,
                        response: Response<NewsResponse>
                    ) {
                        rv_loading.hide()
                        if (response.isSuccessful&&response.body()!=null) //Check if data exist
                        {
                            rvAdapter.setData(response.body()!!.articles)
                            Toast.makeText(this@MainActivity,"Data fetched",Toast.LENGTH_SHORT).show();
                        }
                    }

                    override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                        //Api call failure
                        rv_loading.hide()
                    }


                })
        }catch (e:Exception)
        {
            //Exception
        }

    }

}