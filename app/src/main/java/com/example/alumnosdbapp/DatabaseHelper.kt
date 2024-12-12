package com.example.alumnosdbapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AlumnoDatabaseHelper (context: Context) : SQLiteOpenHelper(context, "alumnos", null, 1) {

    companion object {
        private const val SQL_DELETE_ALUMNOS = "DROP TABLE IF EXISTS ${DefineTable.Alumno.TABLE_NAME}"
        private const val SQL_CREATE_ALUMNOS = "CREATE TABLE ${DefineTable.Alumno.TABLE_NAME} (" +
                "${DefineTable.Alumno.MATRICULA} INTEGER PRIMARY KEY," +
                "${DefineTable.Alumno.NOMBRE} TEXT," +
                "${DefineTable.Alumno.DOMICILIO} TEXT," +
                "${DefineTable.Alumno.ESPECIALIDAD} TEXT," +
                "${DefineTable.Alumno.FOTO} TEXT)"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ALUMNOS)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(SQL_DELETE_ALUMNOS)
        db?.execSQL(SQL_CREATE_ALUMNOS)

    }
}