package com.example.clickstask_romany.data.model
import com.google.gson.annotations.SerializedName

data class Article (
    @SerializedName("source")
    val source: Source? = null,

    @SerializedName("author")
    val author: Any? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("url")
    val url: String? = null,

    @SerializedName("urlToImage")
    val urlToImage: String? = null,

    @SerializedName("publishedAt")
    val publishedAt: String? = null
)