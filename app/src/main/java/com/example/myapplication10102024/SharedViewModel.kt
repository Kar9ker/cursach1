import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication10102024.Medicine

class SharedViewModel : ViewModel() {
    private val _selectedMedicine = MutableLiveData<Medicine?>()
    val selectedMedicine: LiveData<Medicine?> get() = _selectedMedicine

    fun setSelectedMedicine(medicine: Medicine) {
        _selectedMedicine.value = medicine
    }

    fun clearSelectedMedicine() {
        _selectedMedicine.value = null
    }
}