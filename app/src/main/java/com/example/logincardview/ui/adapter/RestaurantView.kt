package com.example.logincardview.ui.adapter

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.logincardview.R
import com.example.logincardview.databinding.ActivityRestaurantBinding
import com.example.logincardview.domain.models.Restaurant
import com.example.logincardview.ui.views.fragment.RestaurantDialogFragmentCU


class RestaurantView(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ActivityRestaurantBinding.bind(view)

    private val starImages = listOf(
        binding.imageView3,
        binding.imageView4,
        binding.imageView5,
        binding.imageView6,
        binding.imageView7
    )

    fun renderize(restaurant: Restaurant, isFavorite: Boolean, onFavoriteClick: (Long) -> Unit) {
        binding.localName.text = restaurant.name
        binding.localAddress.text = restaurant.address
        binding.localPhone.text = restaurant.phone

        if (!restaurant.imageUri.isNullOrEmpty()) {
            Glide.with(itemView.context)
                .load(Uri.parse(restaurant.imageUri))
                .centerCrop()
                .placeholder(R.drawable.cardview_img1)
                .error(R.drawable.cardview_img1)
                .into(binding.localImage)
        } else {
            Glide.with(itemView.context)
                .load(R.drawable.cardview_img1)
                .centerCrop()
                .into(binding.localImage)
        }

        updateStars(restaurant.rating)

        binding.favButton.setImageResource(
            if (isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
        )

        binding.favButton.setOnClickListener {
            onFavoriteClick(restaurant.id!!)
        }

        updateStars(restaurant.rating)

        binding.localPhone.setOnClickListener {
            val phoneNumber = restaurant.phone
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
            itemView.context.startActivity(intent)
        }

        binding.localAddress.setOnClickListener {
            val address = restaurant.address
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=$address"))
            itemView.context.startActivity(intent)
        }

        binding.localImage.setOnClickListener {
            val dialog = RestaurantDialogFragmentCU().newInstance(restaurant, isEdit = false)
            dialog.show((itemView.context as AppCompatActivity).supportFragmentManager, "RestaurantDialogFragmentCU")
        }
    }


    fun setAdminVisibility(isAdmin: Boolean) {

        binding.deleteBtn.visibility =
            if (isAdmin) View.VISIBLE else View.GONE
        binding.editBtn.visibility =
            if (isAdmin) View.VISIBLE else View.GONE
    }

    private fun updateStars(rate: Int) {
        for (i in starImages.indices) {
            if (i < rate) {
                starImages[i].setImageResource(R.drawable.baseline_star_24)
            } else {
                starImages[i].setImageResource(R.drawable.baseline_star_outline_24)
            }
        }
    }
}



