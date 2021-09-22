package com.example.clickstask_romany.data.model
import com.google.gson.annotations.SerializedName

data class NewsResponse (

    @SerializedName("totalResults")
     val totalResults: Int? = null,

    @SerializedName("articles")
    val articles: List<Article>? = null
)