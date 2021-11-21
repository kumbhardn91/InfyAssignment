package com.kumbhar.infyassignment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kumbhar.infyassignment.model.DataRows

@Database(entities = [DataRows::class], version = 1)
abstract class CountryDatabase : RoomDatabase() {

    abstract fun countryDAO(): CountryDAO

    companion object {

        private var INSTANCE: CountryDatabase? = null

        internal fun getDatabase(context: Context): CountryDatabase {

            if (INSTANCE == null) {
                synchronized(CountryDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            CountryDatabase::class.java, "country_databasae"
                        )
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }

}