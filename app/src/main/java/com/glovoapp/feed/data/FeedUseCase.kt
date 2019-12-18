package com.glovoapp.feed.data

import java.util.Date

class FeedUseCase(private val feedRepository: FeedRepository) {

    fun getLatestItems(onComplete: (feedItems: List<FeedItem>) -> Unit) {
        feedRepository.getLatestItems {
            onComplete.invoke(it)
        }
    }

    fun getNewerItems(lastItemDate: Date, onComplete: (feedItems: List<FeedItem>) -> Unit) {
        feedRepository.getNewerItems(lastItemDate, 10) {
            onComplete.invoke(it)
        }
    }
}