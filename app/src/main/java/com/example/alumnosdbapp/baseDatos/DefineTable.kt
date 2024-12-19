package com.example.alumnosdbapp.baseDatos

import android.provider.BaseColumns

class DefineTable {

    object Alumno : BaseColumns {
        const val TABLE_NAME = "alumno"
        const val MATRICULA = "matricula"
        const val NOMBRE = "nombre"
        const val DOMICILIO = "domicilio"
        const val ESPECIALIDAD = "especialidad"
        const val FOTO = "foto"

    }
}