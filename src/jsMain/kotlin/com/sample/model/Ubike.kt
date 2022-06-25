package com.sample.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

@kotlinx.serialization.Serializable
data class Ubike(
    @SerialName("sno")
    val stationNumber: String,

    @SerialName("sna")
    val stationName: String,

    @SerialName("tot")
    val totalCount: Long,

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
) {
    companion object {
        fun toJson(data: List<Ubike>) = Json.encodeToString(ListSerializer(Ubike.serializer()), data)

        fun fromJson(it: String) = Json.decodeFromString(ListSerializer(Ubike.serializer()), it)
    }
}