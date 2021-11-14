package com.example.pomacapplication.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.pomacapplication.R
import com.example.pomacapplication.databinding.FragmentNewsListBinding
import com.example.pomacapplication.databinding.NewsDetailsFragmentBinding
import com.example.pomacapplication.models.Result
import com.example.pomacapplication.viewModel.NewsViewModel

class NewsDetailsFragment : Fragment(R.layout.news_details_fragment) {
    //vars
    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: NewsDetailsFragmentBinding
    val args: NewsDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //inflate data binding
        binding= NewsDetailsFragmentBinding.bind(view)
        //inflate the viewModel
        viewModel = (activity as MainActivity).viewModel
        //getting the args
        val result = args.result
        bindResult(result)
    }
    private fun bindResult(result: Result){
        binding.apply {
            //To get Img url
            var imgUrl = result.multimedia.get(0).url
            Log.d("TAG", "bind: "+result)
            Glide.with(binding.root).load(imgUrl)
                .placeholder(R.drawable.ic_no_photography)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(newsDetailsIvImage)
            //put data in text field
            newsDetailsTvPublishedDate.text = "Date:  " + result.published_date
            /**
             * please notice that the data from api does not contain publish by
             * so i use type instead
             * to check here is the link:
             * https://api.nytimes.com/svc/topstories/v2/world.json?api-key=Ru9kpfJx0rdhGHgVa8nT0nL75mJajVVK
             */
            newsDetailsTvType.text = "Type:  " + result.item_type
            newsDetailsTvTitle.text = result.title
        }
    }



}