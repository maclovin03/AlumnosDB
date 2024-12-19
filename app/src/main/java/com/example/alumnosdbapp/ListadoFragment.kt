package com.example.alumnosdbapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alumnosdbapp.baseDatos.Alumno
import com.example.alumnosdbapp.databinding.FragmentDashboardBinding
import com.example.alumnosdbapp.recyclerView.AlumnoRVAdapter
import kotlinx.coroutines.launch

class
ListadoFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private lateinit var adapter: AlumnoRVAdapter
    private var tempArrayList = ArrayList<Alumno>()
    private var newArrayList = ArrayList<Alumno>()

    private val dashboardViewModel: DashboardViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = AlumnoRVAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        binding.fabAddAlumno.setOnClickListener {
        }
        binding.searchView.addTextChangedListener {
            Log.d("DashboardFragment", "Buscando...")
            val filteredList = tempArrayList.filter { nomina -> nomina.nombre.lowercase().contains(it.toString().lowercase()) }
            adapter.alumnos = filteredList.toMutableList()
            adapter.notifyDataSetChanged()
        }
        viewModelInit()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun viewModelInit() {
        viewLifecycleOwner.lifecycleScope.launch {
            dashboardViewModel.getAlumnos(requireContext())
            dashboardViewModel.alumnos.collect {
                newArrayList.addAll(it)
                tempArrayList.addAll(newArrayList)
                adapter.alumnos = tempArrayList
                adapter.notifyDataSetChanged()
            }
        }
    }
}