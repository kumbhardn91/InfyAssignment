package com.kumbhar.infyassignment.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kumbhar.infyassignment.model.DataRows
import junit.framework.TestCase
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CountryDatabaseTest: TestCase(){

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
    public override fun setUp(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, CountryDatabase::class.java).build()
        dao = db.countryDAO()
    }

    @After
    fun closeDb(){
        db.close()
    }

    @Test
    fun writeAndReadCountry(){
        dao.addCountryData(countryModel)
        val isDataExist = dao.isDataExists()
        Assert.assertEquals(isDataExist,true)
    }
}