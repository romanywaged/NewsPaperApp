package com.example.clickstask_romany.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clickstask_romany.R
import com.example.clickstask_romany.data.model.Article
import kotlinx.android.synthetic.main.article_row.view.*

class ArticleAdapter (private val articles : ArrayList<Article> ,
                      private val clickListener : IArticleClickListener,
                      private val context:Context) : RecyclerView.Adapter<ArticleAdapter.MyArticleViewholder> (), Filterable
{

    val filteredArticles = ArrayList<Article>(articles)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int): ArticleAdapter.MyArticleViewholder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.article_row, parent, false)

        return MyArticleViewholder(view)
    }

    override fun getItemCount(): Int {
       return articles.size
    }

    override fun onBindViewHolder(holder: ArticleAdapter.MyArticleViewholder, position: Int) {
        val article:Article = articles.get(position)
        holder.sourceName_txt.text = article.source!!.name
        holder.articleDate.text = article.publishedAt
        holder.articleDescription.text = article.title
        Glide.with(context).load(article.urlToImage).into(holder.articleImage)


        holder.itemView.setOnClickListener {
            article.urlToImage?.let { it1 ->
                article.description?.let { it2 ->
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



    class MyArticleViewholder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val sourceName_txt = itemView.source_name_row!!
        val articleImage = itemView.article_image_row!!
        val articleDate = itemView.article_date_row!!
        val articleDescription = itemView.article_description_row!!
    }

    override fun getFilter(): Filter? {

       return  object : Filter() {
           override fun performFiltering(constraint: CharSequence): FilterResults {
               val filteredList: MutableList<Article> = ArrayList<Article>()
               if (constraint == null || constraint.length == 0) {
                   filteredList.addAll(filteredArticles)
               } else {
                   val pattern = constraint.toString().toLowerCase().trim { it <= ' ' }
                   for (article in filteredArticles) {
                       if (article.title?.toLowerCase()!!.contains(pattern)) {
                           filteredList.add(article)
                       }
                   }
               }
               val results = FilterResults()
               results.values = filteredList
               return results
           }

           override fun publishResults(constraint: CharSequence, results: FilterResults) {
               articles.clear()
               articles.addAll(results.values as ArrayList<Article>)
               notifyDataSetChanged()
           }
       }
   }

}


