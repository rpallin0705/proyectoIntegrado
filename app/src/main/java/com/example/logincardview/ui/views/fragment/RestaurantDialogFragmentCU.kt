package com.example.logincardview.ui.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private var isEditable : Boolean = true

    private lateinit var binding: FragmentAddRestaurantBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddRestaurantBinding.bind(view)

        setValuesIntoDialog(arguments)

        if (!isEditable)
            disableEditing()

        updateStars(selectedRating)
        setupListeners()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inicialización de ViewModel y otras configuraciones
        restaurantViewModel = ViewModelProvider(requireActivity())[RestaurantViewModel::class.java]
        return inflater.inflate(R.layout.fragment_add_restaurant, container, false)
    }

    fun newInstance(restaurant: Restaurant): RestaurantDialogFragmentCU {
        val fragment = RestaurantDialogFragmentCU()
        val args = Bundle().apply {
            putString("name", restaurant.name)
            putString("phone", restaurant.phone)
            putString("address", restaurant.address)
            putString("description", restaurant.description)
            putInt("rating", restaurant.rating)
        }
        fragment.arguments = args
        fragment.isEditable = true
        return fragment
    }

    fun newInstance(restaurant: Restaurant, isEdit: Boolean): RestaurantDialogFragmentCU {
        val fragment = RestaurantDialogFragmentCU()
        val args = Bundle().apply {
            putString("name", restaurant.name)
            putString("phone", restaurant.phone)
            putString("address", restaurant.address)
            putString("description", restaurant.description)
            putInt("rating", restaurant.rating)
        }

        fragment.arguments = args
        fragment.isEditable = isEdit
        return fragment
    }

    private fun disableEditing() {
        binding.editLocalName.isEnabled = false
        binding.editLocalPhone.isEnabled = false
        binding.editLocalAddress.isEnabled = false
        binding.editLocalDescription.isEnabled = false
        binding.star1.isEnabled = false
        binding.star2.isEnabled = false
        binding.star3.isEnabled = false
        binding.star4.isEnabled = false
        binding.star5.isEnabled = false
        binding.positiveButton.visibility = View.GONE
    }

    private fun setValuesIntoDialog(arguments: Bundle?) {
        arguments?.let {
            binding.editLocalName.setText(it.getString("name"))
            binding.editLocalPhone.setText(it.getString("phone"))
            binding.editLocalAddress.setText(it.getString("address"))
            binding.editLocalDescription.setText(it.getString("description"))
            selectedRating = it.getInt("rating", 0)
        }
    }

    private fun setupListeners() {
        // Configuración de estrellas
        val stars = listOf(binding.star1, binding.star2, binding.star3, binding.star4, binding.star5)
        stars.forEachIndexed { index, imageButton ->
            imageButton.setOnClickListener { updateStars(index + 1) }
        }

        binding.positiveButton.setOnClickListener {
            val updatedRestaurant = recoverDataLayout()
            if (updatedRestaurant.isValid()) {
                onUpdate?.invoke(updatedRestaurant)
                dismiss()
            } else {
                Toast.makeText(activity, "Algún campo está vacío", Toast.LENGTH_LONG).show()
            }
        }

        binding.negativeButton.setOnClickListener {
            dismiss()
        }
    }

    private fun recoverDataLayout(): Restaurant {
        return Restaurant(
            name = binding.editLocalName.text.toString(),
            address = binding.editLocalAddress.text.toString(),
            phone = binding.editLocalPhone.text.toString(),
            rating = selectedRating,
            description = binding.editLocalDescription.text.toString()
        )
    }

    private fun updateStars(rating: Int) {
        selectedRating = rating
        val stars = listOf(binding.star1, binding.star2, binding.star3, binding.star4, binding.star5)
        stars.forEachIndexed { index, imageButton ->
            val resource = if (index < rating) R.drawable.baseline_star_24 else R.drawable.baseline_star_outline_24
            imageButton.setImageResource(resource)
        }
    }

    private fun Restaurant.isValid(): Boolean {
        return name.isNotEmpty() && address.isNotEmpty() && phone.isNotEmpty() && description.isNotEmpty()
    }
}
