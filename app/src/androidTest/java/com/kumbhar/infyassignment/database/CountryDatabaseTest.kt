package com.kumbhar.infyassignment.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.kumbhar.infyassignment.getOrAwaitValue
import com.kumbhar.infyassignment.model.DataRows
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class CountryDatabaseTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: CountryDatabase
    private lateinit var dao: CountryDAO
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

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, CountryDatabase::class.java).allowMainThreadQueries().build()
        dao = db.countryDAO()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndGetCountryData() {
        dao.addCountryData(countryModel)
        val isDataExist = dao.isDataExists()
        Assert.assertEquals(isDataExist, true)
    }

    @Test
    fun insertAndGetCountryLiveData() {
        dao.addCountryData(countryModel)
        val liveDataList = dao.getCountryData().getOrAwaitValue()
        Assert.assertEquals(liveDataList[0].title, countryModel[0].title)
    }
}