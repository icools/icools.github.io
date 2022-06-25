package com.sample.viewmodel

import com.sample.web.WebApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository {
    //<td class="room-tartime winfo-data">0004</td>
    //Regex regex = new Regex("<span[^>]*>(.*?)</span>");
    //string toMatch = "<span class=\"ajjsjs\">Some text</span>";
    suspend fun getCross(): String = withContext(Dispatchers.Default) {
        WebApi.getCross()
    }

    suspend fun getHospital() = withContext(Dispatchers.Default) {
        WebApi.getHospital().let {
            val preTag = "room-tartime winfo-data\">"
            val startIndex = it.indexOf(preTag) + preTag.length
            var endIndex = 0
            for (i in startIndex..it.length) {
                if (it[i] == '<') {
                    endIndex = i
                    break;
                }
            }
            it.substring(startIndex..endIndex)
        }//.toInt()
    }

    suspend fun getTraveling() = withContext(Dispatchers.Default) {
        WebApi.Traveling.getHotTravelSpot()
    }

    // TODO stock https://openapi.twse.com.tw/#/%E5%85%AC%E5%8F%B8%E6%B2%BB%E7%90%86/get_company_newlisting
    suspend fun getStockNewTo() = withContext(Dispatchers.Default) {
        WebApi.TwseStock.getNewTo()
    }
}