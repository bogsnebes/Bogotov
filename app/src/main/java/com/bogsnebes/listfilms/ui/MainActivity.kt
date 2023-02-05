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
                supportFragmentManager.findFragmentByTag(ListFragment.TAG)
            if (currentFragment != null) {
                val descriptionFragment =
                    supportFragmentManager.findFragmentByTag(DescriptionFragment.TAG)
                if (descriptionFragment != null) {
                    supportFragmentManager.popBackStack()
                }
            } else {
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, listFragment, ListFragment.TAG)
                    .commit()
            }
        } else {
            val currentFragment =
                supportFragmentManager.findFragmentByTag(ListFragment.TAG)
            if (currentFragment != null) {
                val descriptionFragment =
                    supportFragmentManager.findFragmentByTag(DescriptionFragment.TAG)
                if (descriptionFragment != null) {
                    supportFragmentManager.popBackStack()
                }
            } else {
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, listFragment, ListFragment.TAG)
                    .commit()
            }
        }
    }
}