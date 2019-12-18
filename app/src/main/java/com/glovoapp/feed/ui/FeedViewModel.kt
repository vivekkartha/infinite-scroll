package com.glovoapp.feed.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.glovoapp.feed.data.FeedItem
import com.glovoapp.feed.data.FeedUseCase
import com.glovoapp.feed.di.app
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.Date

class FeedViewModel(private val feedUseCase: FeedUseCase = app().feedUseCase) : ViewModel() {

    val feedLiveData: MutableLiveData<List<FeedItem>> = MutableLiveData()

    fun getLatest() {
        Single.create<List<FeedItem>> { emitter ->
            feedUseCase.getLatestItems { items ->
                emitter.onSuccess(items)
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { items ->
                feedLiveData.postValue(items)
            }
            .subscribe()
    }

    fun getNewerItems(lastItemDate: Date) {
        Single.create<List<FeedItem>> { emitter ->
            feedUseCase.getNewerItems(lastItemDate) { items ->
                emitter.onSuccess(items)
            }
        }
            .subscribeOn(Schedulers.io())

            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { items ->
                feedLiveData.postValue(items)
            }
            .subscribe()
    }
}