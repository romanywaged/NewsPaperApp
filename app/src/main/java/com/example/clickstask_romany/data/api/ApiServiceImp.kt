package com.example.clickstask_romany.data.api

import io.reactivex.Observable
import com.example.clickstask_romany.data.model.NewsResponse
import com.example.clickstask_romany.utlis.BASE_URL
import com.example.clickstask_romany.utlis.GET_NEWS_API
import com.rx2androidnetworking.Rx2AndroidNetworking

class ApiServiceImp : ApiService {
    override fun getNewsResponse(pageNumber : Int): Observable<NewsResponse>
    {
        val apiURL = BASE_URL+ GET_NEWS_API+pageNumber
        return Rx2AndroidNetworking.get(apiURL)
            .build()
            .getObjectObservable(NewsResponse::class.java)
    }
}