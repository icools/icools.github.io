package com.sample.model

import com.sample.model.stock.StockNewTo
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

@kotlinx.serialization.Serializable
data class Cms(val locations: List<Location> = emptyList()) {
    @kotlinx.serialization.Serializable
    data class Location (
        val lat: Double,
        val lng: Double,
        val cmsId: String,
        val cmsName: String,
        val cmsMsg: String
    )

    companion object{
        fun toJson(data: Cms) = Json.encodeToString(Cms.serializer(),data)
        fun fromJson(it: String) = Json.decodeFromString(Cms.serializer(),it)
    }
}

