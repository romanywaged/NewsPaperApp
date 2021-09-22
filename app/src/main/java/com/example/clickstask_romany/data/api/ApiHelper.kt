package com.example.clickstask_romany.data.api

class ApiHelper (private val apiService : ApiService) {
    fun getNewsPaper(pageNumber : Int) = apiService.getNewsResponse(pageNumber)
}