package com.example.clickstask_romany.data.api

import io.reactivex.Observable
import com.example.clickstask_romany.data.model.NewsResponse
import com.rx2androidnetworking.Rx2AndroidNetworking

class ApiServiceImp : ApiService {
    override fun getNewsResponse(): Observable<NewsResponse> {
        return Rx2AndroidNetworking.get("https://newsapi.org/v2/top-headlines?country=eg&apiKey=43c16ed8dcab411e850a0067887b95b4")
            .build()
            .getObjectObservable(NewsResponse::class.java)
    }
}