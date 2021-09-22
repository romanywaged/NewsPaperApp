package com.example.clickstask_romany.ui.main.view

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import com.example.clickstask_romany.R
import com.example.clickstask_romany.data.api.ApiHelper
import com.example.clickstask_romany.data.api.ApiServiceImp
import com.example.clickstask_romany.ui.base.ViewModelFactory
import com.example.clickstask_romany.ui.main.adapter.ArticleAdapter
import com.example.clickstask_romany.ui.main.viewmodel.MainViewModel
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clickstask_romany.data.model.Article
import com.example.clickstask_romany.ui.main.adapter.IArticleClickListener
import com.example.clickstask_romany.utlis.CommonMethod
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*
import kotlin.collections.ArrayList


class HomeActivity : AppCompatActivity(), IArticleClickListener
{
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: ArticleAdapter
    private lateinit var mLayoutManager: LinearLayoutManager
    private  var articles = ArrayList<Article>()
    private var  tempArticles = ArrayList<Article>()
    private val commonMethod = CommonMethod()

    private var loading = false
    private var pageNumber =0

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (commonMethod.checkNetworkConnection(this))
        {
            setupViewModel()
            setupObserver()
            setUpRecycle()
            setUpSearView()

            mainViewModel.getNewsResponse(pageNumber)
        }
        else
        {
            emptyList.visibility = View.VISIBLE
            progressPar.visibility = View.GONE
        }
    }

    private fun setupObserver()
    {
        mainViewModel.getNewsListResponse().observe(this, Observer
        {
            progressPar.visibility = View.GONE
            it?.let {
                newsResponse -> articles.addAll(newsResponse.articles!!)
                tempArticles.addAll(newsResponse.articles)
            }
        })

        mainViewModel.showLoader().observe(this, Observer {
            progressPar.visibility = View.VISIBLE
        })

        mainViewModel.getLoadingError().observe(this, Observer
        {
            progressPar.visibility = View.GONE
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun setUpRecycle()
    {
        mLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        article_rv.layoutManager = mLayoutManager

        adapter = ArticleAdapter(tempArticles, this, this)
        article_rv.adapter = adapter

        article_rv.addOnScrollListener(object : RecyclerView.OnScrollListener()
        {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int)
            {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?

                if (!loading && linearLayoutManager!!.itemCount <= linearLayoutManager.findLastVisibleItemPosition() + 2)
                {
                    loading = true
                    pageNumber++
                    mainViewModel.getNewsResponse(pageNumber)
                }
            }
        })
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(ApiServiceImp()))
        ).get(MainViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun articleClicked(img: ImageView, imgUrl:String,description: String, newsUrl: String, sourceName:String, Tittle:String)
    {

        var activityOptions: ActivityOptions? = null

        activityOptions = ActivityOptions.makeSceneTransitionAnimation(this,img,"SharedName")

        val intent:Intent = Intent(this,DescriptionActivity::class.java)

        intent.putExtra("description",description)
        intent.putExtra("newsUrl",newsUrl)
        intent.putExtra("sourceName",sourceName)
        intent.putExtra("Tittle",Tittle)
        intent.putExtra("imgUrl",imgUrl)
        startActivity(intent,activityOptions.toBundle())
    }


    private fun setUpSearView() {

        search_view.setOnClickListener {
            search_view.onActionViewExpanded()
        }

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener, androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
               return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                tempArticles.clear()

                val text = newText!!.toLowerCase(Locale.getDefault())
                if (text.isNotEmpty())
                {
                    articles.forEach {
                        if (it.title!!.toLowerCase(Locale.getDefault()).contains(text))
                        {
                            tempArticles.add(it)
                        }
                        article_rv.adapter!!.notifyDataSetChanged()
                    }
                }
                else
                {
                    tempArticles.clear()
                    tempArticles.addAll(articles)
                    article_rv.adapter!!.notifyDataSetChanged()
                }
                return false
            }
        })
    }
}