package com.glovoapp.feed

import android.os.Looper
import com.glovoapp.feed.data.DateProvider
import com.glovoapp.feed.data.FeedItem
import com.glovoapp.feed.data.FeedService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import java.util.Date
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

//Timeout for the CompletableFuture in case it fails, so the tests can continue
private const val TIMEOUT = 3000L

@RunWith(PowerMockRunner::class)
@PrepareForTest(Looper::class)
class FeedServiceTest {

    @Before
    fun setup() {
        val mockMainThreadLooper = mock(Looper::class.java)
        `when`(mockMainThreadLooper.isCurrentThread).thenReturn(false)

        PowerMockito.mockStatic(Looper::class.java)
        `when`(Looper.getMainLooper()).thenReturn(mockMainThreadLooper)
    }

    private val dateProvider = mock(DateProvider::class.java)
    private val service = FeedService(dateProvider)

    @Test
    fun testBefore() {
        val date = service.dateFormat.parse("01:00:30")
        `when`(dateProvider.now()).thenReturn(date)
        val limit = 5

        val future = CompletableFuture<List<FeedItem>>()

        service.getOlderItems(date, limit) { items ->
            future.complete(items)
        }

        val items = future.get(TIMEOUT, TimeUnit.SECONDS)

        assert(items.count() == limit)
        assert(items[0].title == "01:00:29")
        assert(items[1].title == "01:00:28")
        assert(items[2].title == "01:00:27")
        assert(items[3].title == "01:00:26")
        assert(items[4].title == "01:00:25")
    }

    @Test
    fun testAfter() {
        val limit = 5
        val date = service.dateFormat.parse("01:00:30")
        val offsetToEnsureThereAreItems = (limit + 2) * 1000
        `when`(dateProvider.now()).thenReturn(Date(date.time + offsetToEnsureThereAreItems))

        val items = CompletableFuture<List<FeedItem>>().also { future ->
            service.getNewerItems(date, limit) { items ->
                future.complete(items)
            }
        }.get(TIMEOUT, TimeUnit.SECONDS)

        assert(items.count() == limit)
        assert(items[0].title == "01:00:35")
        assert(items[1].title == "01:00:34")
        assert(items[2].title == "01:00:33")
        assert(items[3].title == "01:00:32")
        assert(items[4].title == "01:00:31")
    }

    @Test
    fun testLatest() {
        val date = Date()
        `when`(dateProvider.now()).thenReturn(date)
        val limit = 5

        val items = CompletableFuture<List<FeedItem>>().also { future ->
            service.getLatestItems(5) { items ->
                future.complete(items)
            }
        }.get(TIMEOUT, TimeUnit.SECONDS)

        assert(items.count() == limit)
        assert(items[0].title == service.dateFormat.format(Date(date.time - 1000)))
        assert(items[1].title == service.dateFormat.format(Date(date.time - 2000)))
        assert(items[2].title == service.dateFormat.format(Date(date.time - 3000)))
        assert(items[3].title == service.dateFormat.format(Date(date.time - 4000)))
        assert(items[4].title == service.dateFormat.format(Date(date.time - 5000)))
    }


    @Test
    fun `getNewerItems when date is 2 seconds ago, then return 2 items`() {
        val expectedAmount = 2
        val now = Date()
        `when`(dateProvider.now()).thenReturn(now)

        val date = Date(now.time - (expectedAmount * 1000))


        val items = CompletableFuture<List<FeedItem>>().also { future ->
            service.getNewerItems(date, expectedAmount + 5) { items ->
                future.complete(items)
            }
        }.get(TIMEOUT, TimeUnit.SECONDS)

        assert(items.count() == expectedAmount)
    }
}