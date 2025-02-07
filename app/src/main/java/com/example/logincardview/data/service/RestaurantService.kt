import com.example.logincardview.data.datasource.RestaurantDataSource
import com.example.logincardview.data.models.RestaurantDTO

class RestaurantService {
    private val dataSource = RestaurantDataSource()

    suspend fun getRestaurants(): List<RestaurantDTO> {
        return dataSource.getRestaurants()
    }

    suspend fun addRestaurant(restaurant: RestaurantDTO) {
        dataSource.addRestaurant(restaurant)
    }

    suspend fun deleteRestaurant(restaurantId: Int) {
        dataSource.deleteRestaurant(restaurantId)
    }
}
