package com.example.myapplication10102024

import SharedViewModel
import UserMedsAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class UserMedsFragment : Fragment()  {

    private lateinit var adapter: UserMedsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var apiService: ApiService

    private var selectedMedicine: Medicine? = null

    private val sharedViewModel: SharedViewModel by activityViewModels()

    companion object {
        // Константа для ключа (используется для извлечения данных из Bundle)
        private const val ARG_STRING = "arg_string"

        // метод для создания нового экземпляра фрагмента с данными
        fun newInstance(data: String): UserMedsFragment {
            val fragment = UserMedsFragment()
            val args = Bundle()
            args.putString(ARG_STRING, data) // Передаём данные в Bundle
            fragment.arguments = args       // Устанавливаем аргументы
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_meds, container, false)

        val name = arguments?.getString(ARG_STRING)!!

        // Инициализация RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_user_meds)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = UserMedsAdapter(emptyList()) { userMed ->
            Toast.makeText(
                requireContext(),
                "Вы выбрали: ${userMed.medicine.name}",
                Toast.LENGTH_SHORT
            ).show()
        }
        recyclerView.adapter = adapter

        sharedViewModel.selectedMedicine.observe(viewLifecycleOwner) { medicine ->
            if (medicine != null) {
                Toast.makeText(requireContext(), "Передано лекарство: ${medicine.name}", Toast.LENGTH_SHORT).show()
                selectedMedicine = medicine
                sharedViewModel.clearSelectedMedicine()
            }
        }


        // Инициализация кнопок
        view.findViewById<View>(R.id.btn_add_med).setOnClickListener { addMedicine(name, selectedMedicine?.name) }
        view.findViewById<View>(R.id.btn_delete_med).setOnClickListener { deleteMedicine(name, adapter.getSelectedItem()?.medicine?.name) }

        // Загрузка данных
        loadUserMeds(name)

        return view
    }

    private fun loadUserMeds(name: String) {
        lifecycleScope.launch {
            val response = RetrofitClient.apiService.getUsersMeds(name = name)
            if (response.isSuccessful) {
                val userMeds = response.body() ?: emptyList()
                adapter.updateData(userMeds)
            } else {
                Toast.makeText(requireContext(), "Ошибка загрузки данных", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addMedicine(name: String, medicine_name: String?) {
        lifecycleScope.launch {
            if (medicine_name == null) {
                return@launch
            }
            val userMedRequest = UserMedRequest(user_name = name, medicine_name = medicine_name) // Пример данных
            val response = RetrofitClient.apiService.addUserMed(userMedRequest)
            loadUserMeds(name)
            if (response.isSuccessful) {
                Toast.makeText(requireContext(), "Лекарство добавлено", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Ошибка добавления", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteMedicine(name: String, medicine_name: String?) {
        lifecycleScope.launch {
            if (medicine_name == null) {
                return@launch
            }
            val deleteRequest = UserMedRequest(user_name = name, medicine_name = medicine_name) // Пример данных
            val response = RetrofitClient.apiService.deleteUserMed(deleteRequest)
            loadUserMeds(name)
            if (response.isSuccessful) {
                Toast.makeText(requireContext(), "Лекарство удалено", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Ошибка удаления", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
