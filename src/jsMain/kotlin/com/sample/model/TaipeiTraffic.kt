package com.sample.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class TaipeiTraffic(
    val updateTime: String,
    @SerialName("News")
    val newsList: List<TaipeiTrafficNews>
) {

    @kotlinx.serialization.Serializable
    data class TaipeiTrafficNews(
        val chtmessage: String,
        val engmessage: String,
        val starttime: String,
        val endtime: String,
        val updatetime: String,
        val content: String,
        val url: String
    )
}