package com.example.pomacapplication.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.pomacapplication.R
import com.example.pomacapplication.databinding.NewsListItemBinding

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    /*
    *differCallback for checking if there is a new data
     */
    private val differCallback = object : DiffUtil.ItemCallback<com.example.pomacapplication.models.Result>() {
        override fun areItemsTheSame(oldItem: com.example.pomacapplication.models.Result, newItem: com.example.pomacapplication.models.Result): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: com.example.pomacapplication.models.Result, newItem: com.example.pomacapplication.models.Result): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    private var onItemClickListener: ((com.example.pomacapplication.models.Result) -> Unit)? = null

    inner class NewsViewHolder(val binding: NewsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        //to bind data in views
        fun bind(result: com.example.pomacapplication.models.Result){
            binding.apply {
                //To get Img url
              var imgUrl = result.multimedia.get(0).url
                Log.d("TAG", "bind: "+result)
                Glide.with(binding.root).load(imgUrl)
                    .placeholder(R.drawable.ic_no_photography)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(newsListIvImage)
                //put data in text field
                newsListTvPublishedDate.text = "Date: " + result.published_date
                /**
                 * please notice that the data from api does not contain publish by
                 * so i use type instead
                 * to check here is the link:
                 * https://api.nytimes.com/svc/topstories/v2/world.json?api-key=Ru9kpfJx0rdhGHgVa8nT0nL75mJajVVK
                 */
                newsListTvType.text = "Type: " + result.item_type
                newsListTvTitle.text = result.title
                newsListLlParent.setOnClickListener {
                    onItemClickListener?.let { it(result) }
                }
            }}

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding =
            NewsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }


    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)


    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    //to use ItemClickListener in fragment(view)
    fun setOnItemClickListener(listener: (com.example.pomacapplication.models.Result) -> Unit) {
        onItemClickListener = listener
    }


}