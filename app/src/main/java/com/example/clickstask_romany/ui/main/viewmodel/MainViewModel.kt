package com.example.clickstask_romany.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clickstask_romany.data.model.NewsResponse
import com.example.clickstask_romany.data.repository.MainRepository
import com.example.clickstask_romany.utlis.Resource
import com.example.clickstask_romany.utlis.Status
import io.reactivex.schedulers.Schedulers
import io.reactivex.disposables.CompositeDisposable


class MainViewModel(private val mainRepository: MainRepository) : ViewModel()
{
    private val compositeDisposable = CompositeDisposable()

    private val newsResponse = MutableLiveData<NewsResponse>()
    private val isLoading = MutableLiveData<Boolean>()
    private val errorMsg = MutableLiveData<String>()


    fun getNewsResponse(pageNumber : Int)
    {
        handelGetNewsResponse(Resource.loading(null))

        compositeDisposable.add(mainRepository.getNews(pageNumber).subscribeOn(Schedulers.io())
                .subscribe(
                        { newsPaper -> handelGetNewsResponse(Resource.success(newsPaper)) },
                        { handelGetNewsResponse(Resource.error("Some Thing went wrong!",null)) }
                )
        )
    }

    private fun handelGetNewsResponse(response : Resource<NewsResponse>)
    {
        when (response.status)
        {
            Status.SUCCESS -> newsResponse.postValue(response.data)
            Status.LOADING -> isLoading.postValue(true)
            Status.ERROR -> errorMsg.postValue("Wrong !")
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getNewsListResponse () : LiveData<NewsResponse>
    {
        return newsResponse
    }

    fun showLoader() : LiveData<Boolean>
    {
        return isLoading
    }

    fun getLoadingError() : LiveData<String>
    {
        return errorMsg
    }
}