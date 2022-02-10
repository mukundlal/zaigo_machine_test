package com.example.test.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test.R
import kotlinx.android.synthetic.main.gallery_item.view.*
import java.io.File

class GalleryAdapter:RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

     var fileList=ArrayList<File>()
    class ViewHolder(val view: View):RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.gallery_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

           Glide.with( holder.itemView.image)
               .load(fileList[position])
               .into(holder.itemView.image)
    }

    override fun getItemCount(): Int {
       return fileList.size
    }
    fun setData(data:List<File>)
    {
        fileList.clear()
        fileList.addAll(data)
        notifyDataSetChanged()
    }
}