package com.kumbhar.infyassignment.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kumbhar.infyassignment.utils.SingleLiveEvent
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
    var progressDialog: SingleLiveEvent<Boolean>? = SingleLiveEvent()
    var countryDataList = MutableLiveData<List<DataRows>>()

    //var countryDataList = MutableLiveData<CountryModel>()
    private var dataRepository = DataRepository()
    var countryUpdatedData = dataRepository.getCountryData(context)

    fun getCountryData() {
        dataRepository.getCountryInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getDataListObserverRx())
    }

    private fun getDataListObserverRx(): Observer<CountryModel> {
        return object : Observer<CountryModel> {

            override fun onComplete() {
                progressDialog?.value = false
               /* countryDataList.value!!.let { e ->
                    dataRepository.insertCountryData(e, context)
                }
                countryUpdatedData = dataRepository.getCountryData(context)*/
                Log.i("onComplete***", "onComplete")
            }

            override fun onError(e: Throwable) {
                progressDialog?.value = false
                Log.i("onError***", e.toString())
            }

            override fun onNext(t: CountryModel) {
                // countryLiveData.value = t
                dataRepository.clearCountryData()
                countryDataList.value = t.rows
                countryDataList.value!!.let { e ->
                    dataRepository.insertCountryData(e, context)
                }
                Log.i("onNext***", t.toString())
            }

            override fun onSubscribe(d: Disposable) {
                Log.i("onSubscribe***", "onSubscribe")
                progressDialog?.value = true
            }
        }
    }

}