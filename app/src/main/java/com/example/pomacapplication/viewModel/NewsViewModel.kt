package com.example.pomacapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pomacapplication.models.NewsResponse
import com.example.pomacapplication.repository.NewsRepository
import com.example.pomacapplication.util.NewsResource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel constructor (
    val repository: NewsRepository
        ):ViewModel(){
    //vars
    val _popularNews: MutableLiveData<NewsResource<NewsResponse>> = MutableLiveData()
    val popularNews: LiveData<NewsResource<NewsResponse>>
    get() = _popularNews


    init {
        getPopularNews()
    }
   //setting the loading status
     fun getPopularNews()= viewModelScope.launch {
       //setting the loading status
       _popularNews.postValue(NewsResource.Loading())
       //getting data from repository
         val response=repository.getPopularNews()
       //putting data in livedata
       _popularNews.postValue(handlePopularNews(response))
     }
    //    private fun handlePopularNews
    private fun handlePopularNews(response: Response<NewsResponse>) : NewsResource<NewsResponse>? {
       return if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
               NewsResource.Success(resultResponse)
            }
        }else{ NewsResource.Error(response.message(),null)}
    }
}