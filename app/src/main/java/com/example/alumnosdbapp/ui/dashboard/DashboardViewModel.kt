package com.example.alumnosdbapp.ui.dashboard

import android.content.Context
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alumnosdbapp.Alumno
import com.example.alumnosdbapp.dbAlumnos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DashboardViewModel : ViewModel() {

    private val _alumnos = MutableStateFlow(emptyList<Alumno>().toMutableList())
    val alumnos: StateFlow<MutableList<Alumno>> = _alumnos.asStateFlow()

    fun getAlumnos(context: Context) {
        val dbAlumnos = dbAlumnos(context)
        dbAlumnos.openDatabase()
        val alumnos = dbAlumnos.getAllAlumnos()
        _alumnos.value = alumnos.toMutableList()
        Log.d("DashboardViewModel", "Alumnos: $alumnos")

    }

    fun filterAlumnos(query: String){
        _alumnos.value = _alumnos.value.filter { alumno -> alumno.nombre.contains(query, ignoreCase = true) }.toMutableList()
    }
}