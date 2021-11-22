package com.kumbhar.infyassignment.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.kumbhar.infyassignment.database.CountryDatabase
import com.kumbhar.infyassignment.model.CountryModel
import com.kumbhar.infyassignment.model.DataRows
import com.kumbhar.infyassignment.retroconnection.RetroInstance
import com.kumbhar.infyassignment.retroconnection.RetroService
import io.reactivex.Observable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DataRepository {

    private lateinit var db: CountryDatabase

    // Call api and get server data
    fun getCountryInfo(): Observable<CountryModel> {
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        return retroInstance.getCountryInfoApi()
    }

    // Insert data into room database
    fun insertCountryData(countryDataList: List<DataRows>, context: Context) {
        db = CountryDatabase.getDatabase(context)
        GlobalScope.launch {
            db.countryDAO().addCountryData(countryDataList)
        }
    }

    // Get data from room database
    fun getCountryData(context: Context): LiveData<List<DataRows>> {
        db = CountryDatabase.getDatabase(context)
        return db.countryDAO().getCountryData()
    }

    // Clear all data
    fun clearCountryData(context: Context) {
        GlobalScope.launch {
            db = CountryDatabase.getDatabase(context)
            db.countryDAO().deleteCountryData()
        }
    }

    // Check local data
    fun checkDataExist(context: Context): Boolean {
        var isDataExist = false
        GlobalScope.launch {
            db = CountryDatabase.getDatabase(context)
            isDataExist = db.countryDAO().isDataExists() ?: false
        }
        return isDataExist
    }

}