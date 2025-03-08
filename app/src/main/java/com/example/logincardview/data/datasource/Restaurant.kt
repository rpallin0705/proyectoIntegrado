package com.example.logincardview.data.datasource

import com.example.logincardview.data.models.RestaurantDTO
import com.example.logincardview.domain.models.Restaurant
import kotlinx.coroutines.delay

class RestaurantDataSource {

    private val restaurantList = mutableListOf<RestaurantDTO>(
        RestaurantDTO(
            1,
            "Sukiyabashi Jiro",
            "Calle Gran Vía, 12, 28013 Madrid, España",
            "+34 911 123 456",
            5,
            "Famoso restaurante de sushi dirigido por el maestro Jiro Ono, conocido por su técnica impecable y su menú omakase."
        ),
        RestaurantDTO(
            2,
            "Narisawa",
            "Passeig de Gràcia, 45, 08007 Barcelona, España",
            "+34 932 345 678",
            4,
            "Restaurante innovador que combina técnicas francesas con ingredientes japoneses, destacando la sostenibilidad y la naturaleza."
        ),
        RestaurantDTO(
            3,
            "Kyo Aji",
            "Avenida de la Constitución, 18, 41004 Sevilla, España",
            "+34 954 987 654",
            3,
            "Auténtico restaurante kaiseki que ofrece platos de temporada preparados con precisión y maestría."
        ),
        RestaurantDTO(
            4,
            "Kitcho Arashiyama",
            "Calle Larios, 10, 29005 Málaga, España",
            "+34 952 321 789",
            5,
            "Experiencia culinaria kaiseki de lujo en un entorno tradicional con vistas serenas."
        ),
        RestaurantDTO(
            5,
            "Gion Sasaki",
            "Calle Alcalá, 25, 28014 Madrid, España",
            "+34 910 456 789",
            2,
            "Restaurante moderno de kaiseki que ofrece una mezcla única de creatividad y tradición."
        ),
        RestaurantDTO(
            6,
            "Mizai",
            "Calle Colón, 34, 46004 Valencia, España",
            "+34 963 258 741",
            4,
            "Restaurante exclusivo con un menú kaiseki cuidadosamente elaborado y una atmósfera íntima."
        ),
        RestaurantDTO(
            7,
            "Sushi Saito",
            "Calle Balmes, 15, 08007 Barcelona, España",
            "+34 934 567 890",
            5,
            "Uno de los mejores restaurantes de sushi, conocido por su pescado fresco y la destreza del chef."
        ),
        RestaurantDTO(
            8,
            "Nihonryori Ryugin",
            "Paseo del Prado, 8, 28014 Madrid, España",
            "+34 917 654 321",
            3,
            "Restaurante de alta gastronomía que fusiona la cocina japonesa con técnicas modernas e innovadoras."
        ),
        RestaurantDTO(
            9,
            "Ishikawa",
            "Calle San Fernando, 15, 41001 Sevilla, España",
            "+34 955 123 321",
            1,
            "Restaurante kaiseki que ofrece platos delicados y elegantes en una atmósfera tranquila y sofisticada."
        ),
        RestaurantDTO(
            10,
            "Sushi Mizutani",
            "Calle Mayor, 20, 28005 Madrid, España",
            "+34 911 987 654",
            4,
            "Restaurante de sushi tradicional, famoso por la precisión del chef y su compromiso con la calidad."
        )
    )


    fun getRestaurants(): List<RestaurantDTO> {
        return restaurantList
    }

    fun addRestaurant(restaurant: RestaurantDTO) {
        restaurantList.add(restaurant)
    }

    fun deleteRestaurant(restaurantId: Int) {
        restaurantList.removeAt(restaurantId.toInt())
    }

    fun editRestaurant(oldRestaurant: RestaurantDTO, newRestaurant: RestaurantDTO) {
        val index = restaurantList.indexOfFirst { it.name == oldRestaurant.name }
        if (index != -1) {
            restaurantList[index] = newRestaurant.copy(imageUri = newRestaurant.imageUri)
        }
    }

}
