package com.sample.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sample.TopicEnum
import com.sample.model.Ubike
import com.sample.storageCacheList
import com.sample.web.WebApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UbikeViewModel(topic: TopicEnum,private val coroutineScope: CoroutineScope) {

    init {
        getUBike()
    }
    var ubikeList: List<Ubike> by mutableStateOf((emptyList()))
        private set

    private fun getUBike() {
        coroutineScope.launch {
            storageCacheList<Ubike>(
                topic = TopicEnum.UBIKE,
                fetchFromService = {
                    WebApi.getUBikeList()
                }
            ).collect {
                ubikeList = it
            }
        }
    }
}