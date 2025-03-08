import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logincardview.R
import com.example.logincardview.data.repository.RestaurantRepository
import com.example.logincardview.databinding.FragmentRestaurantBinding
import com.example.logincardview.network.RetrofitClient
import com.example.logincardview.ui.adapter.RestaurantAdapter
import com.example.logincardview.ui.modelview.RestaurantViewModel
import com.example.logincardview.ui.modelview.RestaurantViewModelFactory

class FavoritesFragment : Fragment(R.layout.fragment_restaurant) {

    private lateinit var binding: FragmentRestaurantBinding
    private lateinit var adapter: RestaurantAdapter
    private val restaurantViewModel: RestaurantViewModel by viewModels {
        RestaurantViewModelFactory(RestaurantRepository(RetrofitClient.restaurantApi))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRestaurantBinding.inflate(inflater, container, false)
        setupRecyclerView()
        observeViewModel()
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerViewLocal.layoutManager = LinearLayoutManager(requireContext())
        adapter = RestaurantAdapter(emptyList(), {}, {}, {}, emptySet())
        binding.recyclerViewLocal.adapter = adapter
    }

    private fun observeViewModel() {
        restaurantViewModel.restaurantLiveData.observe(viewLifecycleOwner) { restaurants ->
            restaurantViewModel.favoritesLiveData.observe(viewLifecycleOwner) { favorites ->
                val favoriteRestaurants = restaurants.filter { it.id in favorites }
                adapter.updateList(favoriteRestaurants)
            }
        }

        restaurantViewModel.getRestaurants()
        restaurantViewModel.getFavoriteRestaurants()
    }
}
