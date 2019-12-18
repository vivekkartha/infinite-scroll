package com.glovoapp.feed.data

import java.util.Date

class FeedRepository(private val feedService: FeedService) {

    fun getLatestItems(onComplete: (List<FeedItem>) -> Unit) {
        feedService.getLatestItems { items ->
            onComplete(items)
        }
    }

    fun getNewerItems(after: Date, limit: Int = 10, callback: (List<FeedItem>) -> Unit) {
        feedService.getNewerItems(after, limit, callback)
    }
}