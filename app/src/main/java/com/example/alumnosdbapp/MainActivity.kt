package com.example.alumnosdbapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.alumnosdbapp.baseDatos.Alumno
import com.example.alumnosdbapp.baseDatos.dbAlumnos

class MainActivity : AppCompatActivity() {
    private lateinit var btnagregar: Button
    private lateinit var btnbuscar: Button
    private lateinit var btnactualizar: Button
    private lateinit var btnborrar: Button
    private lateinit var btnlimpiar: Button

    private lateinit var etmatricula: EditText
    private lateinit var etnombre: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initButtons()
    }

    private fun initButtons() {
        btnagregar = findViewById(R.id.agregarBtn)
        btnbuscar = findViewById(R.id.buscarBtn)
        btnactualizar = findViewById(R.id.updateBtn)
        btnborrar = findViewById(R.id.borrarBtn)
        btnlimpiar = findViewById(R.id.cleanBtn)

        etmatricula = findViewById(R.id.matriculaET)
        etnombre = findViewById(R.id.nombreET)

        btnborrar.setOnClickListener {
            val db = dbAlumnos(this)
            db.openDatabase()
            val matricula = etmatricula.text.toString()
            val resultado = db.borrarAlumno(matricula)
            Toast.makeText(this, "Borrando alumno", Toast.LENGTH_SHORT).show()
        }
        btnagregar.setOnClickListener {
            if (etmatricula.text.isEmpty() || etnombre.text.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            val alumno = Alumno(0, etmatricula.text.toString(), etnombre.text.toString(), "", "", "")
            //val db = dbAlumnos(this)
            //db.openDatabase()
            Toast.makeText(this, "Alumno agregado", Toast.LENGTH_SHORT).show()

        }

        btnlimpiar.setOnClickListener {
            etmatricula.text.clear()
            etnombre.text.clear()

        }
        btnactualizar.setOnClickListener {
            val db = dbAlumnos(this)
            db.openDatabase()
            val alumno = Alumno(0, etmatricula.text.toString(), etnombre.text.toString(), "", "", "")
            val resultado = db.actualizarAlumno(alumno)
            Toast.makeText(this, "Actualizando alumno", Toast.LENGTH_SHORT).show()
        }
        btnbuscar.setOnClickListener {
            Toast.makeText(this, "Buscando alumno", Toast.LENGTH_SHORT).show()
        }
    }
}