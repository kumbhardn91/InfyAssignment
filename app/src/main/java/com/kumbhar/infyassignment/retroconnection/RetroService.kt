package com.kumbhar.infyassignment.retroconnection

import com.kumbhar.infyassignment.model.CountryModel
import io.reactivex.Observable
import retrofit2.http.GET

interface RetroService {

    @GET("facts.json")
    fun getCountryInfoApi(): Observable<CountryModel>

}