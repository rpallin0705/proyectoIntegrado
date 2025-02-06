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
import com.example.logincardview.domain.models.Restaurant
import com.example.logincardview.ui.modelview.RestaurantViewModel

class RestaurantDialogFragmentCU : DialogFragment() {

    // Callback para devolver el restaurant editado
    var onUpdate: ((Restaurant) -> Unit)? = null

    // Variable para guardar la valoración seleccionada
    private var selectedRating = 0

    private lateinit var stars: List<ImageButton>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewDialogAddRestaurant = inflater.inflate(R.layout.fragment_add_restaurant, container, false)

        val btnConfirm = viewDialogAddRestaurant.findViewById<ImageButton>(R.id.positive_button)
        val btnCancel = viewDialogAddRestaurant.findViewById<ImageButton>(R.id.negative_button)

        // Configurar los valores iniciales
        setValuesIntoDialog(viewDialogAddRestaurant, arguments)

        // Inicialización de las estrellas
        val star1 = viewDialogAddRestaurant.findViewById<ImageButton>(R.id.star1)
        val star2 = viewDialogAddRestaurant.findViewById<ImageButton>(R.id.star2)
        val star3 = viewDialogAddRestaurant.findViewById<ImageButton>(R.id.star3)
        val star4 = viewDialogAddRestaurant.findViewById<ImageButton>(R.id.star4)
        val star5 = viewDialogAddRestaurant.findViewById<ImageButton>(R.id.star5)

        stars = listOf(star1, star2, star3, star4, star5)

        // Asigna un OnClickListener a cada estrella
        for (i in stars.indices) {
            stars[i].setOnClickListener {
                updateStars(i + 1)
            }
        }

        btnConfirm.setOnClickListener {
            val updatedRestaurant = recoverDataLayout(viewDialogAddRestaurant)

            if (updatedRestaurant.name.isEmpty() || updatedRestaurant.address.isEmpty() || updatedRestaurant.phone.isEmpty() || updatedRestaurant.description.isEmpty()) {
                Toast.makeText(activity, "Algún campo está vacío", Toast.LENGTH_LONG).show()
            } else {
                onUpdate?.invoke(updatedRestaurant)  // Llamar a la función de callback
                dismiss()  // Cerrar el diálogo
            }
        }

        btnCancel.setOnClickListener {
            dismiss()  // Cerrar el diálogo
        }

        return viewDialogAddRestaurant
    }

    private fun recoverDataLayout(view: View): Restaurant {
        val binding = FragmentAddRestaurantBinding.bind(view)

        val name = binding.editLocalName.text.toString()
        val address = binding.editLocalAddress.text.toString()
        val phone = binding.editLocalPhone.text.toString()
        val description = binding.editLocalDescription.text.toString()

        return Restaurant(name, address, phone, selectedRating, description)
    }

    private fun setValuesIntoDialog(viewDialogEditRestaurant: View, arguments: Bundle?) {
        val binding = FragmentAddRestaurantBinding.bind(viewDialogEditRestaurant)
        arguments?.let {
            binding.editLocalName.setText(it.getString("name"))
            binding.editLocalPhone.setText(it.getString("phone"))
            binding.editLocalAddress.setText(it.getString("address"))
            binding.editLocalDescription.setText(it.getString("description"))
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
