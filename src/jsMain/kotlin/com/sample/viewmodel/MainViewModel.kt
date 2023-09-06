package com.sample.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sample.TopicEnum
import com.sample.model.*
import com.sample.model.stock.StockNewTo
import com.sample.storageCacheFlow
import com.sample.storageCacheList
import com.sample.web.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class MainViewModel(topic: TopicEnum,private val coroutineScope: CoroutineScope) {
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

    init {
        when (topic) {
            TopicEnum.CCTV -> getTainanCctv()
            TopicEnum.TRAVELING -> getTraveling()
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
        coroutineScope.launch{
            storageCacheList(
                topic = TopicEnum.STOCK,
                fetchFromService = dataRepo::getStockNewTo
            ).collect {
                stockNewToList = it
            }
        }
    }

    private fun getTraveling() {
        coroutineScope.launch {
            storageCacheFlow(
                topic = TopicEnum.TRAVELING,
                fetchFromService = dataRepo::getTraveling
            ).collect {
                travelingResponse = it
            }
        }
    }

    private fun getCountByFilter(filterId: String = "593106") {
        coroutineScope.launch {
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

    private fun getCms() {
        coroutineScope.launch {
            storageCacheFlow(
                topic = TopicEnum.CMS,
                fetchFromService = WebApi::getCmsList
            ).collect {
                cmsResponse = it
            }
        }
    }

    private fun getStore(id: String = "2330") {
        coroutineScope.launch {
            WebApi.getStock().let {
                it.msgArray.first().z
            }.let { currentPrice ->
                uiState = uiState.copy(stock = currentPrice)
            }
        }
    }

    private fun getTaipeiTraffic() {
        coroutineScope.launch {
            uiState = WebApi.getTaipeiTraffic().newsList.random().content.let {
                uiState.copy(taipeiTraffic = it)
            }
        }
    }

    private fun getTaichungAir() {
        coroutineScope.launch {
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
        coroutineScope.launch {
            dataRepo.getHospital().let {
                uiState = uiState.copy(hospitalWaitingId = it)
            }
        }
    }

    private fun getTainanCctv() {
        coroutineScope.launch {
            storageCacheList<TainanCctv>(TopicEnum.CCTV) {
                WebApi.Tainan.getCctv()
            }.collect {
                tainanCctvList = it
            }
        }
    }
}

//YouBike站位每月熱門站點
//https://data.gov.tw/dataset/145725