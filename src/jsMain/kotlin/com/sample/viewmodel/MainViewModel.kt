package com.sample.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sample.TopicEnum
import com.sample.model.*
import com.sample.model.stock.StockNewTo
import com.sample.storageCache
import com.sample.web.*
import kotlinx.coroutines.*

data class SellCountUiState(
    val rapidTest: String = "Loading...",
    val stock: String = "Loading..",
    val hospitalWaitingId: String = "Loading...",
    val taipeiTraffic: String = "Loading...",
    val taichungAir: String = "Loading..."
)

class MainViewModel(topic: TopicEnum) {
    private val scope = MainScope()
    private val dataRepo = MainRepository()
    var uiState: SellCountUiState by mutableStateOf(SellCountUiState())

    var taichungAirList: List<TaichungAir> by mutableStateOf((emptyList()))
        private set

    var tainanCctvList: List<TainanCctv> by mutableStateOf(emptyList())
        private set

    var travelingResponse: TravelResponse by mutableStateOf(TravelResponse())
        private set

    var stockNewToList: List<StockNewTo> by mutableStateOf(emptyList())
        private set

    var cmsResponse: Cms by mutableStateOf(Cms())
        private set

    var ubikeList: List<Ubike> by mutableStateOf((emptyList()))
        private set

    init {
        when (topic) {
            TopicEnum.CCTV -> getTainanCctv()
            TopicEnum.TRAVELING -> getTraveling()
            TopicEnum.UBIKE -> getUBike()
            TopicEnum.WATER -> getTaichungAir()
            TopicEnum.CMS -> getCms()
            TopicEnum.STOCK -> getStockNewTo()
            TopicEnum.ALL -> {
                //getCountByFilter() // 疫苗
                //getStore()
                //getHospital()
                getTaipeiTraffic()
            }
        }
    }

    // TODO delegate localStorage by

    private fun getStockNewTo() {
        scope.storageCache(TopicEnum.STOCK,{
            StockNewTo.fromJson(it)
        }, {
            StockNewTo.toJson(it)
        }, {
            dataRepo.getStockNewTo()
        }){
            stockNewToList = it
        }
    }

    private fun getTraveling() {
        scope.storageCache(TopicEnum.TRAVELING,{
            TravelResponse.fromJson(it)
        }, {
            it.toJson()
        },{
            dataRepo.getTraveling()
        }){
            travelingResponse = it
        }
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
        scope.storageCache(TopicEnum.UBIKE,{
            Ubike.fromJson(it)
        },{
            Ubike.toJson(it)
        },{
            WebApi.getUBikeList()
        }){
            ubikeList = it
        }
    }

    private fun getCms() {
        scope.storageCache(TopicEnum.CMS,{
            Cms.fromJson(it)
        },{
            Cms.toJson(it)
        },{
            WebApi.getCmsList()
        }){
            cmsResponse = it
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

    private fun getTainanCctv() {
        scope.storageCache(TopicEnum.CCTV,{
            TainanCctv.fromJson(it)
        }, {
            TainanCctv.toJson(it)
        }, {
            WebApi.Tainan.getCctv()
        }){
            tainanCctvList = it
        }
    }
}

//YouBike站位每月熱門站點
//https://data.gov.tw/dataset/145725