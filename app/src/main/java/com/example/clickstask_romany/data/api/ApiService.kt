package com.example.clickstask_romany.data.api

import io.reactivex.Observable
import com.example.clickstask_romany.data.model.NewsResponse

interface ApiService {
    fun getNewsResponse() : Observable<NewsResponse>
}