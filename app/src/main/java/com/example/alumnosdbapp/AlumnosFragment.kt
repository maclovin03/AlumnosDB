package com.example.alumnosdbapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.alumnosdbapp.baseDatos.Alumno
import com.example.alumnosdbapp.baseDatos.dbAlumnos

class AlumnosFragment : Fragment() {

    private lateinit var dbAlumnos: dbAlumnos

    private lateinit var etNombre: EditText
    private lateinit var etMatricula: EditText
    private lateinit var etDomicilio: EditText
    private lateinit var etEspecialidad: EditText
    private lateinit var imageView2: ImageView
    private lateinit var btnGuardar: Button
    private lateinit var btnBuscar: Button
    private lateinit var btnBorrar: Button
    private lateinit var pickPhoto: Button

    // This property is only valid between onCreateView and
    // onDestroyView.

    private var imageUri: Uri? = null

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resActivity ->
            if (resActivity.data?.data != null) {
                imageUri = resActivity.data?.data
                imageView2.setImageURI(resActivity.data?.data)

            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        dbAlumnos = dbAlumnos(requireContext())
        dbAlumnos.openDatabase()


        val root: View = inflater.inflate(R.layout.alumnos_fragment, container, false)

        etNombre = root.findViewById(R.id.etNombre)
        etMatricula = root.findViewById(R.id.etMatricula)
        etDomicilio = root.findViewById(R.id.etDomicilio)
        etEspecialidad = root.findViewById(R.id.etEspecialidad)
        imageView2 = root.findViewById(R.id.imageView2)
        btnGuardar = root.findViewById(R.id.btnGuardar)
        btnBuscar = root.findViewById(R.id.btnBuscar)
        btnBorrar = root.findViewById(R.id.btnBorrarAlumno)
        pickPhoto = root.findViewById(R.id.pickPhoto)


        btnGuardar.setOnClickListener {
            if (etNombre.text.toString().isNotEmpty() && etMatricula.text.toString()
                    .isNotEmpty() && etDomicilio.text.toString()
                    .isNotEmpty() && etEspecialidad.text.toString().isNotEmpty()
            ) {
                val alumno = Alumno(
                    etMatricula.text.toString().toInt(),
                    etMatricula.text.toString(),
                    etNombre.text.toString(),
                    etDomicilio.text.toString(),
                    etEspecialidad.text.toString(),
                    imageUri.toString()
                )

                val res = dbAlumnos.agregarAlumno(alumno)
                if (res > 0) {
                    Toast.makeText(requireContext(), "Alumno agregado", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Error al agregar alumno", Toast.LENGTH_SHORT)
                        .show()
                }
                etNombre.text.clear()
                etMatricula.text.clear()
                etDomicilio.text.clear()
                etEspecialidad.text.clear()
            } else {
                Toast.makeText(requireContext(), "Campos sin capturar", Toast.LENGTH_SHORT).show()
            }
        }

        btnBorrar.setOnClickListener {
            if (etMatricula.text.toString().isEmpty()) {
                Toast.makeText(
                    requireContext(), "Matricula sin capturar",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Confirmar eliminación")
                builder.setMessage("¿Estás seguro de que desea eliminar este alumno?")


                builder.setPositiveButton("Sí") { dialog, which ->
                    dbAlumnos = dbAlumnos(requireContext())
                    dbAlumnos.openDatabase()

                    val alumno: Alumno =
                        dbAlumnos.getAlumnoByMatricula(etMatricula.text.toString())
                    if (alumno.id != 0) {
                        val result = dbAlumnos.borrarAlumno(alumno.id.toString())
                        if (result > 0) {
                            Toast.makeText(
                                requireContext(), "Alumno eliminado!",
                                Toast.LENGTH_SHORT
                            ).show()
                            etNombre.setText("")
                            etDomicilio.setText("")
                            etEspecialidad.setText("")
                            etMatricula.setText("")
                            imageView2.setImageResource(R.drawable.foto)
                            imageView2.tag = null
                        } else {
                            Toast.makeText(
                                requireContext(), "Error al eliminar alumno",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            requireContext(), "Matricula no encontrada",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }



                builder.setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                }


                val dialog = builder.create()
                dialog.show()
            }
        }

        btnBuscar.setOnClickListener {
            if (etMatricula.text.toString().isEmpty()) {
                Toast.makeText(
                    requireContext(), "Matricula sin capturar",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                dbAlumnos = dbAlumnos(requireContext())
                dbAlumnos.openDatabase()

                val alumno: Alumno = dbAlumnos.getAlumnoByMatricula(etMatricula.text.toString())
                if (alumno.id != 0) {
                    etNombre.setText(alumno.nombre)
                    etDomicilio.setText(alumno.domicilio)
                    etEspecialidad.setText(alumno.especialidad)
                    // Mostrar imagen si está disponible
                    Glide.with(requireContext())
                        .load(Uri.parse(alumno.foto))
                        .apply(RequestOptions().override(100, 100))
                        .into(imageView2)
                    imageView2.tag = alumno.foto
                } else {
                    Toast.makeText(
                        requireContext(), "Matricula no encontrada",
                        Toast.LENGTH_SHORT
                    ).show()
                }


            }
        }

        pickPhoto.setOnClickListener { pickImage() }



        return root
    }


    private fun pickImage() {

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        pickImageLauncher.launch(intent)

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}