package com.sample.model

@kotlinx.serialization.Serializable
data class Ubike (
    val sno: String,
    val sna: String,
    val tot: Long,
    val sbi: Long,
    val sarea: String,
    val mday: String,
    val lat: Double,
    val lng: Double,
    val ar: String,
    val sareaen: String,
    val snaen: String,
    val aren: String,
    val bemp: Long,
    val act: String,
    val srcUpdateTime: String,
    val updateTime: String,
    val infoTime: String,
    val infoDate: String
)