package com.sample.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sample.model.Stock
import com.sample.model.TaichungAir
import com.sample.model.TainanCctv
import com.sample.web.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

data class SellCountUiState(
    val ubike: String = "Loading...",
    val rapidTest: String = "Loading...",
    val cms: String = "Loading...",
    val stock: String = "Loading..",
    val hospitalWaitingId: String = "Loading...",
    val taipeiTraffic: String = "Loading...",
    val taichungAir: String = "Loading..."
)

class MainViewModel {
    private val scope = MainScope()
    private val dataRepo = MainDataRepo()
    var uiState: SellCountUiState by mutableStateOf(SellCountUiState())
    var taichungAirList: List<TaichungAir> by mutableStateOf((emptyList()))
    var tainanCctvList: List<TainanCctv> by mutableStateOf(emptyList())

    init {
        getCountByFilter()
        getUBike()
        getCms()
        getStore()
        getHospital()
        getTaipeiTraffic()
        getTaichungAir()
    }

    private fun getCountByFilter(filterId: String = "593106") {
        scope.launch {
            WebApi.getSellCount()
                .let {
                    it[filterId]
                }.let {
                    "${it?.name},剩餘:${it?.count},Note:${it?.note ?: "無"}"
                }.let {
                    uiState = uiState.copy(rapidTest = it)
                }
        }
    }

    private fun getUBike() {
        scope.launch {
            val data = WebApi.getUBikeList().takeIf {
                it.isNotEmpty()
            }?.random()?.let {
                "${it.sna} Total:${it.tot} ,Time:${it.updateTime}"
            } ?: "Loading..."
            uiState = uiState.copy(ubike = data)
        }
    }

    private fun getCms() {
        scope.launch {
            WebApi.getCmsList().let {
                uiState = uiState.copy(cms = it.locations.random().run {
                    "${cmsName}:${cmsMsg}"
                })
            }
        }
    }

    private fun getStore(id: String = "2330") {
        scope.launch {
            WebApi.getStock().let {
                it.msgArray.first().z
            }.let { currentPrice ->
                uiState = uiState.copy(stock = currentPrice)
            }
        }
    }

    private fun getTaipeiTraffic() {
        scope.launch {
            uiState = WebApi.getTaipeiTraffic().let {
                it.newsList
            }.let {
                it.random()
            }.let {
                it.content
            }.let {
                uiState.copy(taipeiTraffic = it)
            }
        }
    }

    private fun getTaichungAir() {
        scope.launch {
            uiState = WebApi.getTaichungAir().let {
                taichungAirList = it
                it.random()
            }.let {
                "${it.name} - ${it.value} (${it.time})"
            }.let {
                uiState.copy(taichungAir = it)
            }
        }
    }

    private fun getHospital() {
        scope.launch {
            dataRepo.getHospital().let {
                uiState = uiState.copy(hospitalWaitingId = it)
            }
        }
    }

    val data =
        """
    [
    {
        "位置": "101南科南路環西路一段",
        "經度": "120.2825374",
        "緯度": "23.09058354",
        "網址": "http://61.60.90.18:5001/Live?channel=1201&mode=0"
    },
    {
        "位置": "106南科三路道爺路",
        "經度": "120.2825374",
        "緯度": "23.09058354",
        "網址": "http://61.60.90.18:5001/Live?channel=1206&mode=0"
    }
    ]
    """.trim()
    
    fun getTainanCctv(){
        scope.launch {
            tainanCctvList =
                //Json{}.decodeFromString(ListSerializer(TainanCctv.serializer()),data)
                WebApi.Tainan.getCctv()
        }
    }
}

class MainDataRepo {
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
}

//YouBike站位每月熱門站點
//https://data.gov.tw/dataset/145725