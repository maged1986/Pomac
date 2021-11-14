package com.example.pomacapplication.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pomacapplication.R
import com.example.pomacapplication.adapters.NewsAdapter
import com.example.pomacapplication.databinding.FragmentNewsListBinding
import com.example.pomacapplication.util.NewsResource
import com.example.pomacapplication.viewModel.NewsViewModel


class NewsListFragment : Fragment(R.layout.fragment_news_list) {
    //vars
    private lateinit var binding:FragmentNewsListBinding
    private lateinit var viewModel :NewsViewModel
    private lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //inflate vm
        viewModel = (activity as MainActivity).viewModel
        //inflate news Adapter
        newsAdapter= NewsAdapter()
        //inflate data  binding
        binding= FragmentNewsListBinding.bind(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        setupRecyclerView()
        super.onActivityCreated(savedInstanceState)
    }

    private fun setupRecyclerView(){
        binding.listFragRv.apply {
        //setting the rv LayoutManager
        val linearLayoutManager = LinearLayoutManager(context)
        setHasFixedSize(true)
        layoutManager = linearLayoutManager
        itemAnimator = null
        //setting the rv adapter
        adapter=newsAdapter
    }
        viewModel.popularNews.observe(viewLifecycleOwner, Observer { response ->
          //  check response status
            when(response) {
                is NewsResource.Success -> {
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.results)
                        Log.d("TAG", "setupRecyclerView: "+newsResponse.results)
                    }
                }
                is NewsResource.Error -> {
                    response.message?.let { message ->
                        Log.e("TAG", "An error occured: $message")
                    }
                }
                is NewsResource.Loading -> {
                }
            }
        })

        newsAdapter.setOnItemClickListener {
           //putting data in bundle
            val bundle = Bundle().apply {
                putSerializable("result", it)
            }
            //navigate and sending  data to newsDetailsFragment
            binding.listFragRv.findNavController().navigate(
                R.id.action_newsListFragment_to_newsDetailsFragment,
                bundle
            )
        }

    }

}