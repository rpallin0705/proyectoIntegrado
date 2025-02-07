package com.example.logincardview.ui.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.logincardview.R
import com.example.logincardview.databinding.FragmentAddRestaurantBinding
import com.example.logincardview.domain.models.Restaurant
import com.example.logincardview.ui.modelview.RestaurantViewModel

class RestaurantDialogFragmentCU : DialogFragment() {

    private lateinit var restaurantViewModel: RestaurantViewModel
    var onUpdate: ((Restaurant) -> Unit)? = null
    private var selectedRating = 0

    private lateinit var stars: List<ImageButton>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setValuesIntoDialog(view, arguments)
        updateStars(selectedRating)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewDialogAddRestaurant =
            inflater.inflate(R.layout.fragment_add_restaurant, container, false)

        // Obtener el ViewModel
        restaurantViewModel = ViewModelProvider(requireActivity())[RestaurantViewModel::class.java]

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
                onUpdate?.invoke(updatedRestaurant)
                dismiss()
            }
        }

        btnCancel.setOnClickListener {
            dismiss()
        }

        return viewDialogAddRestaurant
    }

    fun newInstance(restaurant: Restaurant): RestaurantDialogFragmentCU {
        val fragment = RestaurantDialogFragmentCU()
        val args = Bundle().apply {
            putString("name", restaurant.name)
            putString("phone", restaurant.phone)
            putString("address", restaurant.address)
            putString("description", restaurant.description)
            putInt("rating", restaurant.rating) // Agregar el rating
        }
        fragment.arguments = args
        return fragment
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
            selectedRating = it.getInt("rating", 0)
        }
    }

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
