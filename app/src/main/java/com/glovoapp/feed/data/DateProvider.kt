package com.glovoapp.feed.data

import java.util.Date

/**
 * Created by Roldán Galán on 2019-06-14.
 */

interface DateProvider {
    fun now(): Date
}

object SystemDateProvider : DateProvider {
    override fun now() = Date()
}