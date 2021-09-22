package com.example.clickstask_romany.ui.main.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clickstask_romany.R
import com.example.clickstask_romany.data.model.Article
import com.example.clickstask_romany.utlis.CommonMethod
import kotlinx.android.synthetic.main.article_row.view.*



class ArticleAdapter (private val articles : ArrayList<Article> ,
                      private val clickListener : IArticleClickListener,
                      private val context:Context) : RecyclerView.Adapter<ArticleAdapter.MyArticleViewholder> ()
{
    private var fade: Animation? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleAdapter.MyArticleViewholder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.article_row, parent, false)

        return MyArticleViewholder(view)
    }

    override fun onBindViewHolder(holder: ArticleAdapter.MyArticleViewholder, position: Int) {


        var description = ""
        var imageUrlNull = ""
        var articleDate = ""
        var commonMethod = CommonMethod()
        val article:Article = articles.get(position)

        fade = AnimationUtils.loadAnimation(context, R.anim.scale_animition)
        holder.myCardView.animation = fade



        if (article.publishedAt != null && article.publishedAt != "00:00:00")
            articleDate = article.publishedAt?.let { commonMethod.separateString(it) }.toString()


        holder.sourceName_txt.text = article.source!!.name
        holder.articleDate.text = commonMethod.getDayNameFromDate(articleDate) + "-" +articleDate
        holder.articleTittle.text = article.title
        if (article.urlToImage!=null && article.description!=null) {
            holder.news_progressBar.visibility = View.GONE
            Glide.with(context).load(article.urlToImage).into(holder.articleImage)
            description = article.description
            imageUrlNull = article.urlToImage
        }
        else {
            holder.news_progressBar.visibility = View.GONE
            Glide.with(context).load(R.drawable.news_image_bg).into(holder.articleImage)
            imageUrlNull = "No"
            description = "No description Found"
        }

        holder.itemView.setOnClickListener {
            imageUrlNull?.let { it1 ->
                description?.let { it2 ->
                    article.url?.let { it3 ->
                        article.source.name?.let { it4 ->
                            article.title?.let { it5 ->
                                clickListener.articleClicked(holder.articleImage, it1, it2, it3
                                        , it4, it5)
                            }
                        }
                    }
                }
            }
        }

    }


    override fun getItemCount(): Int {
        return articles.size
    }

    class MyArticleViewholder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val sourceName_txt = itemView.source_name_row!!
        val articleImage = itemView.article_image_row!!
        val articleDate = itemView.article_date_row!!
        val articleTittle = itemView.article_tittle_row!!
        val news_progressBar = itemView.news_row_progress!!
        val myCardView = itemView.card_row!!
    }

}


