package com.sample.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class TravelResponse(
    val total: Long = 0,
    val data: List<Datum> = emptyList()
) {
    fun toJson() = Json.encodeToString(serializer(),this)

    companion object{
        fun fromJson(text: String) = Json.decodeFromString(serializer(),text)
    }
}

@Serializable
data class Datum(
    val id: Long,
    val name: String,

    @SerialName("name_zh")
    val nameZh: String? = null,

    @SerialName("open_status")
    val openStatus: Long,

    val introduction: String,

    @SerialName("open_time")
    val openTime: String,

    val zipcode: String,
    val distric: String,
    val address: String,
    val tel: String,
    val fax: String,
    val email: String,
    val months: String,
    val nlat: Double,
    val elong: Double,
    @SerialName("official_site")
    val officialSite: String,
    val facebook: String,
    val ticket: String,
    val remind: String,
    val staytime: String,
    val modified: String,
    val url: String,
    val category: List<Category>? = null,
    val target: List<Category?>? = null,
    val service: List<Category>? = null,
    val friendly: List<Category>? = null,
    val images: List<Image>? = null,
    val files: List<String?>? = null,
    val links: List<Link>? = null
)

@Serializable
data class Category(
    val id: Long,
    val name: String
)

@Serializable
data class Image(
    val src: String? = null,
    val subject: String? = null,
    val ext: String? = null
)

//@Serializable
//enum class EXT(val value: String) {
//    Jpg(".jpg");
//
//    companion object {
//        public fun fromValue(value: String): EXT = when (value) {
//            ".jpg" -> Jpg
//            else -> throw IllegalArgumentException()
//        }
//    }
//}

@Serializable
data class Link(
    val src: String? = null,
    val subject: String? = null
)
