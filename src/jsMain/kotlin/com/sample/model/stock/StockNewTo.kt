package com.sample.model.stock

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

@Serializable
data class StockNewTo(
    @SerialName("Code")
    val code: String,

    @SerialName("Company")
    val company: String,

    @SerialName("ApplicationDate")
    val applicationDate: String,

    @SerialName("Chairman")
    val chairman: String,

    @SerialName("AmountofCapital ")
    val amountofCapital: String,

    @SerialName("CommitteeDate")
    val committeeDate: String,

    @SerialName("ApprovedDate")
    val approvedDate: String,

    @SerialName("AgreementDate")
    val agreementDate: String,

    @SerialName("ListingDate")
    val listingDate: String,

    @SerialName("ApprovedListingDate")
    val approvedListingDate: String,

    @SerialName("Underwriter")
    val underwriter: String,

    @SerialName("UnderwritingPrice")
    val underwritingPrice: String,

    @SerialName("Note")
    val note: String
) {
    // TODO 處理重複的程式碼
    companion object{
        fun toJson(data:List<StockNewTo>) = Json.encodeToString(ListSerializer(serializer()),data)
        fun fromJson(it: String) = Json.decodeFromString(ListSerializer(serializer()),it)
    }
}

//fun <T: Serializable> List<T>.toJson() = Json.encodeToString(ListSerializer(StockNewTo.serializer()),this)