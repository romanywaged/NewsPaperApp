package com.example.clickstask_romany.ui.main.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.clickstask_romany.R
import kotlinx.android.synthetic.main.activity_description.*

class DescriptionActivity : AppCompatActivity() {

    var uRL = ""
    var description = ""
    var tittle = ""
    var sourceName = ""
    var imgUrl = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        init_View()
    }

    fun init_View()
    {

        // get extras

        if (intent!=null)
        {
            uRL = intent.getStringExtra("newsUrl").toString()
            description = intent.getStringExtra("description").toString()
            tittle = intent.getStringExtra("Tittle").toString()
            sourceName = intent.getStringExtra("sourceName").toString()
            imgUrl = intent.getStringExtra("imgUrl").toString()
        }


        // set view

        if (imgUrl.equals("No")) {
            Glide.with(this).load(R.drawable.news_image_bg).into(news_Img)
        }else
        {
            Glide.with(this).load(imgUrl).into(news_Img)
        }
        description_sourceName.text =sourceName
        description_Tittle.text = tittle
        description_NewsDescription.text = description



        // handle clicks


        back_arrow.setOnClickListener {
            onBackPressed()
        }

        move_site.setOnClickListener {
            val intent: Intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(uRL))
            startActivity(intent)
        }

    }
}