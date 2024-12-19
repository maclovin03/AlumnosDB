package com.example.alumnosdbapp.baseDatos

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.util.Log.*

class dbAlumnos(private val context: Context) {
    fun openDatabase() {
        db = dbHelper.writableDatabase
    }

    fun actualizarAlumno(alumno: Alumno): Int {
        val values = ContentValues().apply {
            put(DefineTable.Alumno.MATRICULA, alumno.matricula)
            put(DefineTable.Alumno.NOMBRE, alumno.nombre)
            put(DefineTable.Alumno.DOMICILIO, alumno.domicilio)
            put(DefineTable.Alumno.ESPECIALIDAD, alumno.especialidad)
            put(DefineTable.Alumno.FOTO, alumno.foto)
        }
        db.update("alumno", values, "${DefineTable.Alumno.MATRICULA} = ?", arrayOf(alumno.matricula))

        val affectedRows = db.update("alumno", values, "${DefineTable.Alumno.MATRICULA} = ?", arrayOf(alumno.matricula))
        return affectedRows
    }

    fun getAlumnoByMatricula(matricula: String): Alumno {
        val alumno = Alumno(0, "", "", "", "", "")
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM ${DefineTable.Alumno.TABLE_NAME} WHERE ${DefineTable.Alumno.MATRICULA} = ?"
        val cursor = db.rawQuery(query, arrayOf(matricula))

        if (cursor.moveToFirst()) {
            d("Alumno", "Alumno encontrado")
            alumno.matricula = cursor.getString(cursor.getColumnIndexOrThrow(DefineTable.Alumno.MATRICULA))
            Log.d("Alumno", "Matricula encontrada: ${alumno.matricula}")
            alumno.id = cursor.getInt(cursor.getColumnIndexOrThrow(DefineTable.Alumno.MATRICULA))
            alumno.nombre = cursor.getString(cursor.getColumnIndexOrThrow(DefineTable.Alumno.NOMBRE))

            alumno.domicilio = cursor.getString(cursor.getColumnIndexOrThrow(DefineTable.Alumno.DOMICILIO))
            alumno.especialidad = cursor.getString(cursor.getColumnIndexOrThrow(DefineTable.Alumno.ESPECIALIDAD))
            alumno.foto = cursor.getString(cursor.getColumnIndexOrThrow(DefineTable.Alumno.FOTO))
            cursor.close()

        }
        cursor.close()
        return alumno
    }

    fun borrarAlumno(matricula: String): Int {
        val affectedRows = db.delete("alumno", "${DefineTable.Alumno.MATRICULA} = ?", arrayOf(matricula))
        return affectedRows
    }

    fun agregarAlumno(alumno: Alumno): Long {
        val values = ContentValues().apply {
            put(DefineTable.Alumno.MATRICULA, alumno.matricula)
            put(DefineTable.Alumno.NOMBRE, alumno.nombre)
            put(DefineTable.Alumno.DOMICILIO, alumno.domicilio)
            put(DefineTable.Alumno.ESPECIALIDAD, alumno.especialidad)
            put(DefineTable.Alumno.FOTO, alumno.foto)
        }

        val newRowId = db.insert("alumno", null, values)
        return newRowId
    }

    fun getAllAlumnos(): List<Alumno> {
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM ${DefineTable.Alumno.TABLE_NAME}"
        val cursor = db.rawQuery(query, null)
        val alumnos = mutableListOf<Alumno>()

        if (cursor.moveToFirst()) {
            do {
                var alumno = Alumno(0, "", "", "", "", "")
                alumno.matricula = cursor.getString(cursor.getColumnIndexOrThrow(DefineTable.Alumno.MATRICULA))
                alumno.nombre = cursor.getString(cursor.getColumnIndexOrThrow(DefineTable.Alumno.NOMBRE))
                alumno.domicilio = cursor.getString(cursor.getColumnIndexOrThrow(DefineTable.Alumno.DOMICILIO))
                alumno.especialidad = cursor.getString(cursor.getColumnIndexOrThrow(DefineTable.Alumno.ESPECIALIDAD))
                alumno.foto = cursor.getString(cursor.getColumnIndexOrThrow(DefineTable.Alumno.FOTO))
                alumnos.add(alumno)
            }while (cursor.moveToNext())

        }
        cursor.close()
        return alumnos
    }



    private val dbHelper = AlumnoDatabaseHelper(context)
    private lateinit var db: SQLiteDatabase
}