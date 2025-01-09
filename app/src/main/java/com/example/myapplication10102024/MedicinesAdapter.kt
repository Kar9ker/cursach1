import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication10102024.Medicine
import com.example.myapplication10102024.R

class MedicinesAdapter(
    private var medicines: List<Medicine>,
    private val onItemClicked: (Medicine) -> Unit // Callback для обработки клика
) : RecyclerView.Adapter<MedicinesAdapter.MedicineViewHolder>() {

    class MedicineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val medName: TextView = itemView.findViewById(R.id.tv_med_name)
        val medDescription: TextView = itemView.findViewById(R.id.tv_med_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_medicine, parent, false)
        return MedicineViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        val medicine = medicines[position]
        holder.medName.text = medicine.name
        holder.medDescription.text = medicine.description

        // Обрабатываем клик на элемент
        holder.itemView.setOnClickListener {
            onItemClicked(medicine) // Передаём выбранный элемент через callback
        }
    }

    override fun getItemCount(): Int = medicines.size

    fun updateData(newMedicines: List<Medicine>) {
        medicines = newMedicines
        notifyDataSetChanged()
    }
}
