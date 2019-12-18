package com.glovoapp.feed.ui

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.glovoapp.feed.R
import com.glovoapp.feed.data.FeedItem
import com.glovoapp.feed.di.FeedComponent
import com.glovoapp.feed.di.FeedModule
import com.glovoapp.feed.di.app
import kotlinx.android.synthetic.main.activity_feed.*

class FeedActivity : FragmentActivity() {

    lateinit var feedViewModel: FeedViewModel
    private val feedList = mutableListOf<FeedItem>()
    private lateinit var feedItemAdapter: FeedItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        feedViewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        setContentView(R.layout.activity_feed)
        feedViewModel.getLatest()
        feedItemAdapter = getFeedAdapter()
        setRecycler()
        observeOnFeedLiveData()
    }

    private fun inject() {
        FeedComponent.instance = FeedModule()
    }

    private fun setRecycler() {
        recycler.apply {
            layoutManager = LinearLayoutManager(this@FeedActivity)
            adapter = feedItemAdapter
        }
    }

    private fun getFeedAdapter(): FeedItemAdapter {
        return FeedItemAdapter(feedList) { lastDate ->
            feedViewModel.getNewerItems(lastDate)
        }
    }

    private fun observeOnFeedLiveData() {
        feedViewModel.feedLiveData.observe(this, Observer {

            feedList.addAll(it)
            feedItemAdapter.notifyDataSetChanged()
        })
    }
}


