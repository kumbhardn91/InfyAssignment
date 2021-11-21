package com.kumbhar.infyassignment.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kumbhar.infyassignment.model.CountryModel
import com.kumbhar.infyassignment.model.DataRows
import com.kumbhar.infyassignment.repository.DataRepository
import io.reactivex.Observable
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class CountryViewModelTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Mock
    private lateinit var countryViewModel: CountryViewModel

    @Mock
    private var dataRepository: DataRepository = DataRepository()

    private val countryModel = listOf(
        DataRows(
            description = "Beavers are second only to humans in their ability to manipulate and change their environment",
            imageHref = "http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg",
            title = "Beavers"
        ),
        DataRows(
            description = "Flag information",
            imageHref = "http://images.findicons.com/files/icons/662/world_flag/128/flag_of_canada.png",
            title = "Flag"
        )
    )
    private val countryModelObject = CountryModel(rows = countryModel, title = "About Canada")

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        countryViewModel = CountryViewModel(Application())
    }

    @Test
    fun getCountryInfo_onSuccess() {

        Mockito.`when`(dataRepository.getCountryInfo())
            .thenReturn(Observable.just(countryModelObject))

        countryViewModel.getCountryData()

        val countryViewModels = countryViewModel.countryDataList.value
        Assert.assertEquals(countryViewModels?.get(0)?.title, "Beavers")

    }

    @Test
    fun getCountryInfo_onError() {

        Mockito.`when`(dataRepository.getCountryInfo())
            .thenReturn(Observable.error(Throwable("Error")))

        countryViewModel.getCountryData()

        val countryViewModel = countryViewModel.countryDataList.value
        Assert.assertEquals(countryViewModel?.get(0)?.title, "Beavers")
    }

}