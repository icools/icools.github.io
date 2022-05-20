package com.sample.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class TaichungAir(
    @SerialName("測站名稱")
    val name:String,
    @SerialName("測值時間")
    val time:String,
    @SerialName("監測項目")
    val item:String,
    @SerialName("測值")
    val value:String,
    @SerialName("狀態")
    val status:String,
)