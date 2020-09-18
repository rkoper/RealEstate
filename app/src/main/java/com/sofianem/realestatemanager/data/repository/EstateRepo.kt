package com.sofianem.realestatemanager.data.repository

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sofianem.realestatemanager.data.dao.EstateDao
import com.sofianem.realestatemanager.data.dataBase.AllDatabase
import com.sofianem.realestatemanager.data.model.EstateR
import com.sofianem.realestatemanager.data.model.ImageV
import com.sofianem.realestatemanager.data.model.NearbyPlaces
import kotlinx.coroutines.*

open class EstateRepo (estate_Dao: EstateDao) {


    val estate_Dao = estate_Dao
    private var mAllDataForSearch: List<Int>? = arrayListOf()
    var readAllLive: LiveData<List<EstateR>> = estate_Dao.getAllLiveList()
    var mCreateId: Long = 99
    private val mEstate = MutableLiveData<EstateR>()

    fun updateTodo(todo: EstateR) {
        GlobalScope.launch(Dispatchers.IO) {
            estate_Dao.update(todo)
        }
    }

    fun insertTodo(todo: EstateR): Long {
        GlobalScope.launch(Dispatchers.IO) {
            estate_Dao.insert(todo)
        }
        return mCreateId
    }


    fun getSearchAll(
        personn: String?, type: String?, surfaceMini: Int?,
        surfaceMax: Int?,
        priceMini: Int?,
        priceMax: Int?,
        roomMini: Int?,
        roomMax: Int?,
        dateCreateBegin: Long?,
        dateCreateEnd: Long?,
        nb_photo_mini: Int?,
        nb_photo_max: Int?,
        dateSoldBegin: Long?,
        dateSoldBeginEnd: Long?,
        status: String?,
        pharmacy: String?,
        school: String?,
        market: String?,
        park: String?
    ): List<Int>? {

        mAllDataForSearch = estate_Dao.getSearchAll(
            personn,
            type,
            surfaceMini,
            surfaceMax,
            priceMini,
            priceMax,
            roomMini,
            roomMax,
            dateCreateBegin,
            dateCreateEnd,
            nb_photo_mini,
            nb_photo_max,
            dateSoldBegin,
            dateSoldBeginEnd,
            status,
            pharmacy,
            school,
            market,
            park
        )
        return mAllDataForSearch
    }

    fun updateProxPharma(pharmacy: String, id: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            estate_Dao.updateProxPharma(pharmacy, id)
        }
    }

    fun updateProxPark(park: String, id: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            estate_Dao.updateProxPark(park, id)
        }
    }

    fun updateProxSchool(school: String, id: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            estate_Dao.updateProxSchool(school, id)
        }
    }

    fun updateProxMarket(market: String, id: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            estate_Dao.updateProxMarket(market, id)
        }
    }

    fun getById(mId: Int): MutableLiveData<EstateR> {
        GlobalScope.launch(Dispatchers.IO) {
            val list = estate_Dao.getById(mId)
            mEstate.postValue(list)

        }
        println( " T2 ------>" + mEstate)
        return mEstate
    }
}



