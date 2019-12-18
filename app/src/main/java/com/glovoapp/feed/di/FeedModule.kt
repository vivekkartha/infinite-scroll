package com.glovoapp.feed.di

import com.glovoapp.feed.data.FeedRepository
import com.glovoapp.feed.data.FeedService
import com.glovoapp.feed.data.FeedUseCase

class FeedModule: FeedComponent{
    override val feedService: FeedService= FeedService()
    override val feedRepository: FeedRepository= FeedRepository(feedService)
    override val feedUseCase: FeedUseCase = FeedUseCase(feedRepository)
}
