package com.sample.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sample.TopicEnum
import com.sample.model.Cms
import com.sample.model.TaichungAir
import com.sample.model.TainanCctv
import com.sample.model.TravelResponse
import com.sample.model.stock.StockNewTo
import com.sample.web.*
import csstype.Top
import kotlinx.browser.localStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.get
import org.w3c.dom.set

data class SellCountUiState(
    val ubike: String = "Loading...",
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

    init {
        when(topic){
            TopicEnum.CCTV -> getTainanCctv()
            TopicEnum.TRAVELING -> getTraveling()
            TopicEnum.UBIKE -> getUBike()
            TopicEnum.WATER -> getTaichungAir()
            TopicEnum.REALTIME -> getCms()
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
        localStorage.get(TopicEnum.STOCK.value)?.let{
            stockNewToList = StockNewTo.fromJson(it)
        }

        scope.launch {
            stockNewToList = dataRepo.getStockNewTo()
            localStorage.set(TopicEnum.STOCK.value,StockNewTo.toJson(stockNewToList))
        }
    }

    private fun getTraveling() {
        localStorage.get(TopicEnum.TRAVELING.value)?.let{
            travelingResponse = TravelResponse.fromJson(it)
        }

        scope.launch {
            travelingResponse = dataRepo.getTraveling()
            localStorage.set(TopicEnum.TRAVELING.value,travelingResponse.toJson())
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
        localStorage.get(TopicEnum.REALTIME.value)?.let{
            cmsResponse = Cms.fromJson(it)
        }

        scope.launch {
            cmsResponse = WebApi.getCmsList()
            localStorage[TopicEnum.REALTIME.value] = Cms.toJson(cmsResponse)
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

    fun getTainanCctv(){
        localStorage.get(TopicEnum.CCTV.value)?.let{
            tainanCctvList = TainanCctv.fromJson(it)
        }

        scope.launch {
            tainanCctvList = WebApi.Tainan.getCctv()
            TainanCctv.toJson(tainanCctvList).let{
                localStorage.set(TopicEnum.CCTV.value,it)
            }
        }
    }
}

//YouBike站位每月熱門站點
//https://data.gov.tw/dataset/145725