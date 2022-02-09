package com.example.test.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.test.R
import com.example.test.adapters.RvAdapter
import com.example.test.common.hide
import com.example.test.common.show
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
        getApiData()

        btn_map.setOnClickListener {

            startActivity(Intent(this,MapsActivity::class.java))
        }
        btn_gallery.setOnClickListener {

            startActivity(Intent(this,GalleryActivity::class.java))
        }

    }

    private fun getApiData() {

        rv_loading.show()
        RetrofitClient2.apiInterface.getNews()
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    rv_loading.hide()
                    if (response.isSuccessful&&response.body()!=null)
                    {
                        rvAdapter.setData(response.body()!!.articles)
                        Toast.makeText(this@MainActivity,"Data fetched",Toast.LENGTH_SHORT).show();
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    rv_loading.hide()
                }


            })
    }

}