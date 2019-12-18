package com.glovoapp.feed.di

import com.glovoapp.feed.data.FeedRepository
import com.glovoapp.feed.data.FeedService
import com.glovoapp.feed.data.FeedUseCase

fun app() = FeedComponent.instance
interface FeedComponent {
    val feedUseCase: FeedUseCase
    val feedRepository: FeedRepository
    val feedService: FeedService

    companion object {
        lateinit var instance: FeedComponent
    }
}