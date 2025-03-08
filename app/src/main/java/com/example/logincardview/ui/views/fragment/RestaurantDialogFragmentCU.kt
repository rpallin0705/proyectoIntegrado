package com.example.logincardview.ui.views.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.logincardview.R
import com.example.logincardview.databinding.FragmentAddRestaurantBinding
import com.example.logincardview.domain.models.Restaurant
import com.example.logincardview.ui.modelview.RestaurantViewModel

class RestaurantDialogFragmentCU : DialogFragment() {

    private val restaurantViewModel: RestaurantViewModel by activityViewModels()
    private lateinit var binding: FragmentAddRestaurantBinding
    private var selectedRating = 0
    private var isEditable: Boolean = true
    private var restaurantId: Long? = null
    var onUpdate: ((Restaurant) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.Theme_LoginCardView)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddRestaurantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setValuesIntoDialog(arguments)

        if (!isEditable) disableEditing()

        updateStars(selectedRating)
        setupListeners()
    }

    fun newInstance(restaurant: Restaurant, isEdit: Boolean = true): RestaurantDialogFragmentCU {
        return RestaurantDialogFragmentCU().apply {
            arguments = Bundle().apply {
                putLong("id", restaurant.id ?: 0L)
                putString("name", restaurant.name)
                putString("phone", restaurant.phone)
                putString("address", restaurant.address)
                putString("description", restaurant.description)
                putInt("rating", restaurant.rating)
                putBoolean("is_editable", isEdit)
            }
            this.isEditable = isEdit
        }
    }

    private fun setValuesIntoDialog(arguments: Bundle?) {
        arguments?.let {
            restaurantId = it.getLong("id", 0L).takeIf { id -> id != 0L }
            binding.editLocalName.setText(it.getString("name", ""))
            binding.editLocalPhone.setText(it.getString("phone", ""))
            binding.editLocalAddress.setText(it.getString("address", ""))
            binding.editLocalDescription.setText(it.getString("description", ""))
            selectedRating = it.getInt("rating", 0)
        }
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

    private fun setupListeners() {
        val stars = listOf(binding.star1, binding.star2, binding.star3, binding.star4, binding.star5)
        stars.forEachIndexed { index, imageButton ->
            imageButton.setOnClickListener { updateStars(index + 1) }
        }

        binding.positiveButton.setOnClickListener {
            val updatedRestaurant = recoverDataLayout()
            Log.d("DialogFragment", "Botón de confirmar pulsado con datos: $updatedRestaurant")

            if (updatedRestaurant.isValid()) {
                onUpdate?.invoke(updatedRestaurant)
                dismiss()
            } else {
                Toast.makeText(activity, "Algún campo está vacío", Toast.LENGTH_LONG).show()
            }
        }


        binding.negativeButton.setOnClickListener { dismiss() }
    }

    private fun recoverDataLayout(): Restaurant {
        return Restaurant(
            id = restaurantId,
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
