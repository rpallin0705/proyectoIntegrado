package com.example.logincardview.ui.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.logincardview.R
import com.example.logincardview.databinding.FragmentAddRestaurantBinding
import com.example.logincardview.models.ArgumentsLocal
import com.example.logincardview.models.Local

class RestaurantDialogFragmentCU : DialogFragment() {

    // Callback para devolver el local editado
    var onUpdate: ((Local) -> Unit)? = null

    // Variable para guardar la valoración seleccionada
    private var selectedRating = 0

    private lateinit var stars: List<ImageButton>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewDialogAddLocal = inflater.inflate(R.layout.fragment_add_restaurant, container, false)

        val btnConfirm = viewDialogAddLocal.findViewById<ImageButton>(R.id.positive_button)
        val btnCancel = viewDialogAddLocal.findViewById<ImageButton>(R.id.negative_button)

        // Configurar los valores iniciales
        setValuesIntoDialog(viewDialogAddLocal, arguments)

        // Inicialización de las estrellas
        val star1 = viewDialogAddLocal.findViewById<ImageButton>(R.id.star1)
        val star2 = viewDialogAddLocal.findViewById<ImageButton>(R.id.star2)
        val star3 = viewDialogAddLocal.findViewById<ImageButton>(R.id.star3)
        val star4 = viewDialogAddLocal.findViewById<ImageButton>(R.id.star4)
        val star5 = viewDialogAddLocal.findViewById<ImageButton>(R.id.star5)

        stars = listOf(star1, star2, star3, star4, star5)

        // Asigna un OnClickListener a cada estrella
        for (i in stars.indices) {
            stars[i].setOnClickListener {
                updateStars(i + 1)
            }
        }

        btnConfirm.setOnClickListener {
            val updatedLocal = recoverDataLayout(viewDialogAddLocal)

            if (updatedLocal.nombre.isEmpty() || updatedLocal.direccion.isEmpty() || updatedLocal.contacto.isEmpty() || updatedLocal.descripcion.isEmpty()) {
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
        val binding = FragmentAddRestaurantBinding.bind(view)

        val name = binding.editLocalName.text.toString()
        val address = binding.editLocalAddress.text.toString()
        val phone = binding.editLocalPhone.text.toString()
        val description = binding.editLocalDescription.text.toString()

        return Local(name, address, phone, selectedRating, description)
    }

    // todo CARGAR VALORACION DEL LOCAL A EDITAR EN EL DIALOGO
    private fun setValuesIntoDialog(viewDialogEditLocal: View, arguments: Bundle?) {
        val binding = FragmentAddRestaurantBinding.bind(viewDialogEditLocal)
        arguments?.let {
            binding.editLocalName.setText(it.getString(ArgumentsLocal.ARGUMENT_NAME))
            binding.editLocalPhone.setText(it.getString(ArgumentsLocal.ARGUMENT_PHONE))
            binding.editLocalAddress.setText(it.getString(ArgumentsLocal.ARGUMENT_ADDRESS))
            binding.editLocalDescription.setText(it.getString(ArgumentsLocal.ARGUMENT_DESCRIPTION))
        }
    }

    // Función para actualizar las estrellas y guardar la valoración
    private fun updateStars(rating: Int) {
        selectedRating = rating

        for (i in stars.indices) {
            if (i < rating) {
                stars[i].setImageResource(R.drawable.baseline_star_24) // Estrella rellena
            } else {
                stars[i].setImageResource(R.drawable.baseline_star_outline_24) // Estrella vacía
            }
        }
    }
}
