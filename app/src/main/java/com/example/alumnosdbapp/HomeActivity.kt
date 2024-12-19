package com.example.alumnosdbapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNavigationView = findViewById(R.id.nav_view)

        if(savedInstanceState == null){

            cambiarFrame(HomeFragment())

        }

        bottomNavigationView.setOnItemSelectedListener {
                menuItem ->

            when(menuItem.itemId){

                R.id.btnmHome -> {
                    cambiarFrame(HomeFragment())
                    true
                }
                R.id.btnmalumno ->{
                    cambiarFrame(AlumnosFragment())
                    true
                }
                R.id.btnmlistado ->{
                    cambiarFrame(ListadoFragment())
                    true
                }
                R.id.btnmSalir -> {
                    cambiarFrame(SalirFragment())
                    true
                }
                else -> false
            }
        }
    }

    fun cambiarFrame(fragment: Fragment){
        supportFragmentManager.beginTransaction().
        replace(R.id.navHost,fragment).commit()


    }
}