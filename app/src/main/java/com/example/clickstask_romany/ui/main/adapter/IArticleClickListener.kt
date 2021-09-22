package com.example.clickstask_romany.ui.main.adapter

import android.widget.ImageView

interface IArticleClickListener {
    fun articleClicked(img:ImageView, imgUrl:String,description:String, newsUrl:String, sourceName:String, Tittle:String)
}
