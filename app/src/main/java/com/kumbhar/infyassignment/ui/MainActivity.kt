package com.kumbhar.infyassignment.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kumbhar.infyassignment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        title = "Home"
        supportFragmentManager.beginTransaction().apply {
            replace(activityMainBinding.activityFrame.id, CountryFragment.newInstance())
            addToBackStack(null)
            commit()
        }

    }
}