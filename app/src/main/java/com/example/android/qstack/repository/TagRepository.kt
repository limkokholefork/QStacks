package com.example.android.qstack.repository

import com.example.android.qstack.api.NetworkApi
import com.example.android.qstack.data.SITE
import com.example.android.qstack.model.TagResponse
import com.example.android.qstack.utils.OrderBY
import com.example.android.qstack.utils.SortBy
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class TagRepository @Inject constructor(private val networkApi: NetworkApi) {

    fun getAllTagFromApiAsync(): Deferred<TagResponse>{
        return networkApi.getAllTagAsync(
            site = SITE,
            order = OrderBY.DESC.order,
            sortOrder = SortBy.POPULAR.sortOrder,
            pageSize = 99
        )
    }
}