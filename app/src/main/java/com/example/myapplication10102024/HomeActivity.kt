package com.example.myapplication10102024

import MedicineFragment
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val logoutButton = findViewById<ImageButton>(R.id.btn_logout)
        logoutButton.setOnClickListener {
            val logInIntent = Intent(this, MainActivity::class.java)
            startActivity(logInIntent)
        }


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)

        val userName = intent.getStringExtra("USERNAME")!!
        val fragment = UserMedsFragment.newInstance(userName)

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_user_meds -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit()
                    true
                }
                R.id.nav_medicine -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, MedicineFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}