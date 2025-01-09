import android.content.Context
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
import com.example.myapplication10102024.Medicine
import com.example.myapplication10102024.R
import com.example.myapplication10102024.RetrofitClient
import kotlinx.coroutines.launch

class MedicineFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_medicine, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_medicines)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = MedicinesAdapter(emptyList()) { medicine ->
            // Устанавливаем выбранную медицину в SharedViewModel
            sharedViewModel.setSelectedMedicine(medicine)
            Toast.makeText(requireContext(), "Вы выбрали: ${medicine.name}", Toast.LENGTH_SHORT).show()
        }

        recyclerView.adapter = adapter

        loadMedicines(adapter)

        return view
    }

    private fun loadMedicines(adapter: MedicinesAdapter) {
        lifecycleScope.launch {
            val response = RetrofitClient.apiService.getMedicines()
            if (response.isSuccessful) {
                val medicines = response.body() ?: emptyList()
                adapter.updateData(medicines)
            }
        }
    }
}


