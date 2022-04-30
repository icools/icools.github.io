package com.sample.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class RoadData(
    @SerialName("路段")
    val name:String? = null,

    @SerialName("編號")
    val number:String? = null,

    @SerialName("區域")
    val region:String? = null
)
