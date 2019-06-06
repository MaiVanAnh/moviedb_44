package com.sun_asterisk.moviedb_44.screen.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sun_asterisk.moviedb_44.R
import com.sun_asterisk.moviedb_44.screen.home.HomeFragment
import com.sun_asterisk.moviedb_44.utils.BottomNavigationBehavior
import com.sun_asterisk.moviedb_44.utils.addFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        addFragment(HomeFragment(), R.id.container)
        navigation.setOnNavigationItemSelectedListener(this)
        val layoutParams = navigation.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.behavior = BottomNavigationBehavior()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean = false // temporary
}
