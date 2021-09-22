package com.example.clickstask_romany.data.repository

import com.example.clickstask_romany.data.api.ApiHelper
import com.example.clickstask_romany.data.model.NewsResponse
import io.reactivex.Observable

class MainRepository (private val apiHelper: ApiHelper) {
    fun getNews (pageNumber : Int) : Observable<NewsResponse>
    {
        return apiHelper.getNewsPaper(pageNumber)
    }
}