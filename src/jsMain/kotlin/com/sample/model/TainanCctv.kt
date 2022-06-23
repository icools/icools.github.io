package com.sample.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

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
){
    companion object{
        fun toJson(data:List<TainanCctv>) = Json.encodeToString(ListSerializer(TainanCctv.serializer()),data)

        fun fromJson(it: String) = Json.decodeFromString(ListSerializer(serializer()),it)
    }
}