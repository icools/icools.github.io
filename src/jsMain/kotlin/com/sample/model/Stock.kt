package com.sample.model

@kotlinx.serialization.Serializable
data class Stock (
    val msgArray: List<MsgArray>,
    val referer: String,
    val userDelay: Long,
    val rtcode: String,
    val queryTime: QueryTime,
    val rtmessage: String,
    val exKey: String,
    val cachedAlive: Long
){
    @kotlinx.serialization.Serializable
    data class MsgArray (
        val tv: String,
        val ps: String,
        val pz: String,
        val bp: String,
        val fv: String,
        val oa: String,
        val ob: String,
        val a: String,
        val b: String,
        val c: String,
        val d: String,
        val ch: String,
        val ot: String,
        val tlong: String,
        val f: String,
        val ip: String,
        val g: String,
        val mt: String,
        val ov: String,
        val h: String,
        val i: String,
        val it: String,
        val oz: String,
        val l: String,
        val n: String,
        val o: String,
        val p: String,
        val ex: String,
        val s: String,
        val t: String,
        val u: String,
        val v: String,
        val w: String,
        val nf: String,
        val y: String,
        val z: String,
        val ts: String
    )

    @kotlinx.serialization.Serializable
    data class QueryTime (
        val sysDate: String,
        val stockInfoItem: Long,
        val stockInfo: Long,
        val sessionStr: String,
        val sysTime: String,
        val showChart: Boolean,
        val sessionFromTime: Long,
        val sessionLatestTime: Long
    )
}