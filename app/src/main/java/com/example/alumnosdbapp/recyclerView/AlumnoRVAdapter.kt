package com.example.alumnosdbapp.recyclerView

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.alumnosdbapp.Alumno
import com.example.alumnosdbapp.R
import com.example.alumnosdbapp.databinding.AlumnoLayoutBinding

class AlumnoRVAdapter : RecyclerView.Adapter<AlumnoRVAdapter.AlumnoVH>() {

    var alumnos = mutableListOf<Alumno>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlumnoRVAdapter.AlumnoVH {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.alumno_layout, parent, false)

        return AlumnoVH(view)
    }

    override fun onBindViewHolder(holder: AlumnoRVAdapter.AlumnoVH, position: Int) {
        holder.bind(alumnos[position])
    }

    override fun getItemCount() = alumnos.size

    inner class AlumnoVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = AlumnoLayoutBinding.bind(itemView)
        fun bind(alumno: Alumno) {
            binding.tvNombre.text = alumno.nombre
            binding.tvCarrera.text = alumno.especialidad

            if (alumno.foto != null) {
                Glide.with(itemView)
                    .load(Uri.parse(alumno.foto))
                    .apply(RequestOptions().override(100, 100))
                    .into(binding.ivAlumno)
            } else {
                binding.ivAlumno.setImageResource(R.drawable.foto)
            }
        }

    }
}