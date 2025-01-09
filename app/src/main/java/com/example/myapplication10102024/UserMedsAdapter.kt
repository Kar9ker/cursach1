import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication10102024.R
import com.example.myapplication10102024.UserMed

class UserMedsAdapter(
    private var userMeds: List<UserMed>,
    private val onItemClicked: (UserMed) -> Unit
) : RecyclerView.Adapter<UserMedsAdapter.UserMedsViewHolder>() {

    private var selectedItem: UserMed? = null

    class UserMedsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val medName: TextView = itemView.findViewById(R.id.tv_med_name)
        val medDescription: TextView = itemView.findViewById(R.id.tv_med_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserMedsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_med, parent, false)
        return UserMedsViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserMedsViewHolder, position: Int) {
        val userMed = userMeds[position]
        holder.medName.text = userMed.medicine.name
        holder.medDescription.text = userMed.medicine.description

        holder.itemView.setOnClickListener {
            selectedItem = userMed
            onItemClicked(userMed)
        }
    }

    override fun getItemCount(): Int = userMeds.size

    fun updateData(newUserMeds: List<UserMed>) {
        userMeds = newUserMeds
        notifyDataSetChanged()
    }

    fun getSelectedItem(): UserMed? {
        return selectedItem
    }
}
