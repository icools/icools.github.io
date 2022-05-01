package com.sample.model

@kotlinx.serialization.Serializable
data class Cms(val locations: List<Location>) {
    @kotlinx.serialization.Serializable
    data class Location (
        val lat: Double,
        val lng: Double,
        val cmsId: String,
        val cmsName: String,
        val cmsMsg: String
    )
}

