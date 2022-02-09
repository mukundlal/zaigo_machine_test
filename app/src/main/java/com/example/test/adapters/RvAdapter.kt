package com.example.test.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.test.R
import com.example.test.models.Article
import kotlinx.android.synthetic.main.rv_item.view.*

class RvAdapter:RecyclerView.Adapter<RvAdapter.ViewHolder>() {
    val article=ArrayList<Article>()
    class ViewHolder(val view: View):RecyclerView.ViewHolder(view) {
        val articleTitle=view.articleTitle
        val articleDesc=view.articleDesc
        val articleAlbum=view.articleThumb
        val artclePub=view.articlePub

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data=article[position]
        holder.apply {

            this.articleTitle.text=data.title
            this.articleDesc.text=data.description
            this.artclePub.text=data.source.name
            Glide.with(this.articleAlbum)
                .load(data.urlToImage)
                .into(this.articleAlbum)
        }

    }

    override fun getItemCount(): Int {
        return article.size
    }
    fun setData(articles: List<Article>)
    {
        article.clear()
        article.addAll(articles)
        notifyDataSetChanged()
    }
}