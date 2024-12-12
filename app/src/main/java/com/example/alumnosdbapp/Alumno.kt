package com.example.alumnosdbapp

data class Alumno(
    var id: Int,
    var matricula: String,
    var nombre: String,
    var domicilio: String,
    var especialidad: String,
    var foto: String? = null
)
