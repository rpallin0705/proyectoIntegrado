import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.logincardview.MainScreenActivity
import com.example.logincardview.R
import com.example.logincardview.databinding.FragmentAddLocalBinding
import com.example.logincardview.models.ArgumentsLocal
import com.example.logincardview.models.Local
import com.example.logincardview.models.LocalRepository.locales

class AddLocalDFragment : DialogFragment() {

    // Callback para devolver el local editado
    var onUpdate: ((Local) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewDialogAddLocal = inflater.inflate(R.layout.fragment_add_local, container, false)

        val btnConfirm = viewDialogAddLocal.findViewById<ImageButton>(R.id.positive_button)
        val btnCancel = viewDialogAddLocal.findViewById<ImageButton>(R.id.negative_button)

        // Configurar los valores iniciales
        setValuesIntoDialog(viewDialogAddLocal, arguments)

        btnConfirm.setOnClickListener {
            val updatedLocal = recoverDataLayout(viewDialogAddLocal)

            if (updatedLocal.nombre.isEmpty() || updatedLocal.direccion.isEmpty() || updatedLocal.contacto.isEmpty()) {
                Toast.makeText(activity, "Algún campo está vacío", Toast.LENGTH_LONG).show()
            } else {
                onUpdate?.invoke(updatedLocal)  // Llamar a la función de callback
                dismiss()  // Cerrar el diálogo
            }
        }

        btnCancel.setOnClickListener {
            dismiss()  // Cerrar el diálogo
        }

        return viewDialogAddLocal
    }

    private fun recoverDataLayout(view: View): Local {
        val name = view.findViewById<EditText>(R.id.edit_local_name).text.toString()
        val address = view.findViewById<EditText>(R.id.edit_local_address).text.toString()
        val phone = view.findViewById<EditText>(R.id.edit_local_phone).text.toString()

        return Local(name, address, phone, 5)
    }

    private fun setValuesIntoDialog(viewDialogEditLocal: View, arguments: Bundle?) {
        val binding = FragmentAddLocalBinding.bind(viewDialogEditLocal)
        arguments?.let {
            binding.editLocalName.setText(it.getString(ArgumentsLocal.ARGUMENT_NAME))
            binding.editLocalPhone.setText(it.getString(ArgumentsLocal.ARGUMENT_PHONE))
            binding.editLocalAddress.setText(it.getString(ArgumentsLocal.ARGUMENT_ADDRESS))
        }
    }
}
