package com.example.artifact.view.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.artifact.R
//import com.example.artifact.view.ui.bookmark.BookmarkFragment
import com.example.artifact.view.ui.history.HistoryFragment
import com.example.artifact.view.ui.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HistoryFragment())
                .commit()
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            val selectedFragment: Fragment = when (item.itemId) {
                R.id.navigation_history -> HistoryFragment()
//                R.id.navigation_upload -> BookmarkFragment()
                R.id.navigation_profile -> ProfileFragment()
                else -> HistoryFragment()
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, selectedFragment)
                .commit()

            true
        }
    }
}
