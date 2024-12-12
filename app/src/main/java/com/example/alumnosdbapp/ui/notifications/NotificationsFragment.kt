package com.example.alumnosdbapp.ui.notifications

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.alumnosdbapp.Alumno
import com.example.alumnosdbapp.R
import com.example.alumnosdbapp.databinding.FragmentNotificationsBinding
import com.example.alumnosdbapp.dbAlumnos

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private lateinit var dbAlumnos: dbAlumnos

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var imageUri: Uri? = null

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resActivity ->
            if (resActivity.data?.data != null) {
                imageUri = resActivity.data?.data
                binding.imageView2.setImageURI(resActivity.data?.data)

            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        dbAlumnos = dbAlumnos(requireContext())
        dbAlumnos.openDatabase()

        val notificationsViewModel =
            ViewModelProvider(this)[NotificationsViewModel::class.java]

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.apply {
            btnGuardar.setOnClickListener {
                if (etNombre.text.toString().isNotEmpty() && etMatricula.text.toString().isNotEmpty() && etDomicilio.text.toString().isNotEmpty() && etEspecialidad.text.toString().isNotEmpty()) {
                    val alumno = Alumno(
                        etMatricula.text.toString().toInt(),
                        etMatricula.text.toString(),
                        etNombre.text.toString(),
                        etDomicilio.text.toString(),
                        etEspecialidad.text.toString(),
                        imageUri.toString())

                    val res =dbAlumnos.agregarAlumno(alumno)
                    if (res > 0){
                        Toast.makeText(requireContext(), "Alumno agregado", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(requireContext(), "Error al agregar alumno", Toast.LENGTH_SHORT).show()
                    }
                    etNombre.text.clear()
                    etMatricula.text.clear()
                    etDomicilio.text.clear()
                    etEspecialidad.text.clear()
                }else{
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

                        val alumno: Alumno = dbAlumnos.getAlumnoByMatricula(etMatricula.text.toString())
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

            btnBorrar.setOnClickListener {

                etNombre.text.clear()
                etMatricula.text.clear()
                etDomicilio.text.clear()
                etEspecialidad.text.clear()
            }
        }

        return root
    }


    private fun pickImage() {

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        pickImageLauncher.launch(intent)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}