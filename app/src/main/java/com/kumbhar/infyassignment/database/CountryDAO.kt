package com.kumbhar.infyassignment.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kumbhar.infyassignment.model.DataRows

@Dao
interface CountryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCountryData(countryData: List<DataRows>)

    @Query("SELECT * FROM CountryData")
    fun getCountryData(): LiveData<List<DataRows>>

    @Query("SELECT * FROM CountryData")
    fun getCountryListData(): List<DataRows>

    @Query("SELECT EXISTS(SELECT * FROM CountryData)")
    fun isDataExists(): Boolean

    @Query("DELETE FROM CountryData")
    fun deleteCountryData()

}