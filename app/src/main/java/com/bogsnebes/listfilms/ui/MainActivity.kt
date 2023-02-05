package com.bogsnebes.listfilms.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bogsnebes.listfilms.R

class MainActivity : AppCompatActivity() {
    private val listFragment by lazy { ListFragment.newInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val currentFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
            if (currentFragment == null) {
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentContainerView, listFragment)
                    .commit()
            }
        } else {
            val currentFragment =
                supportFragmentManager.findFragmentById(R.id.fragment_container)
            if (currentFragment == null) {
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, listFragment)
                    .commit()
            }
        }
    }
}