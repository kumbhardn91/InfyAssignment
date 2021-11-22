package com.kumbhar.infyassignment.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kumbhar.infyassignment.model.CountryModel
import com.kumbhar.infyassignment.model.DataRows
import com.kumbhar.infyassignment.repository.DataRepository
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CountryViewModel(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext
    var countryDataList = MutableLiveData<List<DataRows>>()
    private var dataRepository = DataRepository()
    var countryUpdatedData = dataRepository.getCountryData(context)
    var isDataExist = dataRepository.checkDataExist(context)

    // get data from repository
    fun getCountryData() {
        dataRepository.getCountryInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getDataListObserverRx())
    }

    // Observe data list with RxJava
    private fun getDataListObserverRx(): Observer<CountryModel> {
        return object : Observer<CountryModel> {

            override fun onComplete() {}

            override fun onError(e: Throwable) {}

            override fun onNext(t: CountryModel) {
                // countryLiveData.value = t
                dataRepository.clearCountryData(context)
                countryDataList.value = t.rows
                countryDataList.value!!.let { e ->
                    dataRepository.insertCountryData(e, context)
                }
                Log.i("onNext***", t.toString())
            }

            override fun onSubscribe(d: Disposable) {}
        }
    }
}