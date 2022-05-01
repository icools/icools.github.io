package com.sample.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sample.web.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class SellCountUiState(
    val ubike: String = "Loading...",
    val rapidTest: String = "Loading...",
    val cms: String = "Loading...",
    val stock: String = "Loading..",
    val hospitalWaitingId: String = "Loading..."
)

class MainViewModel {
    private val scope = MainScope()
    private val dataRepo = MainDataRepo()
    var uiState: SellCountUiState by mutableStateOf(SellCountUiState())

    init{
        getCountByFilter()
        getUBike()
        getCms()
        getStore()
        getHospital()
    }

    private fun getCountByFilter(filterId: String = "593106") {
        scope.launch {
            WebApi.getSellCount()
                .let {
                    it[filterId]
                }.let {
                    "${it?.name},剩餘:${it?.count},Note:${it?.note ?: "無"}"
                }.let{
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

    private fun getCms(){
        scope.launch {
            WebApi.getCmsList().let{
                uiState = uiState.copy(cms = it.locations.random().run{
                    "${cmsName}:${cmsMsg}"
                })
        } }
    }

    private fun getStore(id: String = "2330"){
        scope.launch {
            WebApi.getStock().msgArray.first().z.let{ currentPrice ->
                uiState = uiState.copy(stock = currentPrice)
            }
        }
    }

    private fun getHospital(){
        scope.launch {
            dataRepo.getHospital().let{
                it.toString()
            }.let{
                uiState = uiState.copy(hospitalWaitingId = it)
            }

        }
    }
}

class MainDataRepo{
    //<td class="room-tartime winfo-data">0004</td>
    //Regex regex = new Regex("<span[^>]*>(.*?)</span>");
    //string toMatch = "<span class=\"ajjsjs\">Some text</span>";
    suspend fun getHospital() = withContext(Dispatchers.Default){
        WebApi.getHospital().let{
            val preTag = "room-tartime winfo-data\">"
            val startIndex = it.indexOf(preTag) + preTag.length
            var endIndex = 0
            for(i in startIndex..it.length){
                if(it[i] == '<'){
                    endIndex = i
                    break;
                }
            }
            it.substring(startIndex..endIndex)
        }.toInt()
    }
}