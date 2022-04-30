package com.sample.model

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@kotlinx.serialization.Serializable
data class RapidTestStore(
    val code: Long? = null,
    val name: String? = null,
    val address: String? = null,
    val longitude: Double? = null,
    val latitude: Double? = null,
    val brands: List<String>? = null,
    val count: Long? = null,
    val phone: String? = null,
    val note: String? = null
)

