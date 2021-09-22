package com.example.clickstask_romany.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clickstask_romany.data.model.NewsResponse
import com.example.clickstask_romany.data.repository.MainRepository
import com.example.clickstask_romany.utlis.Resource
import io.reactivex.schedulers.Schedulers
import io.reactivex.disposables.CompositeDisposable



class MainViewModel(private val mainRepository: MainRepository) : ViewModel(){
    private val newsResponse = MutableLiveData<Resource<NewsResponse>>()
    private val compositeDisposable = CompositeDisposable()

    init {
        getNewsResponse()
    }

    private fun getNewsResponse() {
        newsResponse.postValue(Resource.loading(null))
        compositeDisposable.add(
            mainRepository.getNews()
                .subscribeOn(Schedulers.io())
                .subscribe({
                    newsPaper ->
                    newsResponse.postValue(Resource.success(newsPaper))
                },{ throwable->
                    newsResponse.postValue(Resource.error("Wrong!",null))
                }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getNewsResponseObject () : LiveData<Resource<NewsResponse>>
    {
        return newsResponse
    }
}