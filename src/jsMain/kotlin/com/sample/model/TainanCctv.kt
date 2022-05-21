package com.sample.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class TainanCctv(
    @SerialName("位置")
    val positionName: String,
    @SerialName("經度")
    val lat: String,
    @SerialName("緯度")
    val lon: String,
    @SerialName("網址")
    val url: String? = null
)