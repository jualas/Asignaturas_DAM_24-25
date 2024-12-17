package es.jualas.carteles

import android.content.Context

// Clase de datos que representa un artículo con varias propiedades
data class Item(
    val titulo: String,  // Título del artículo o película
    val anoLanzamiento: Int,  // Año de lanzamiento del artículo
    val tamano: String,  // Tamaño del artículo (e.g., Grande, Mediano, Pequeño)
    val estadoConservacion: String,  // Estado de conservación del artículo (e.g., Excelente, Bueno, Regular)
    val ilustrador: String,  // Nombre del ilustrador del artículo
    val paisOrigen: String,  // País de origen del artículo
    val precioEstimado: Double  // Precio estimado del artículo en el mercado
) {
    companion object {
        // Función que devuelve una lista de artículos de muestra
        fun getSampleItems(context: Context): List<Item> {
            return listOf(
                Item(
                    titulo = context.getString(R.string.movie_title_1),
                    anoLanzamiento = 1950,
                    tamano = "Grande",
                    estadoConservacion = "Excelente",
                    ilustrador = "Ilustrador A",
                    paisOrigen = "España",
                    precioEstimado = 500.0
                ),
                Item(
                    titulo = context.getString(R.string.movie_title_2),
                    anoLanzamiento = 1960,
                    tamano = "Mediano",
                    estadoConservacion = "Bueno",
                    ilustrador = "Ilustrador B",
                    paisOrigen = "España",
                    precioEstimado = 350.0
                ),
                Item(
                    titulo = context.getString(R.string.movie_title_3),
                    anoLanzamiento = 1970,
                    tamano = "Pequeño",
                    estadoConservacion = "Regular",
                    ilustrador = "Ilustrador C",
                    paisOrigen = "España",
                    precioEstimado = 200.0
                ),
                Item(
                    titulo = context.getString(R.string.movie_title_4),
                    anoLanzamiento = 1980,
                    tamano = "Grande",
                    estadoConservacion = "Excelente",
                    ilustrador = "Ilustrador D",
                    paisOrigen = "España",
                    precioEstimado = 600.0
                ),
                Item(
                    titulo = context.getString(R.string.movie_title_5),
                    anoLanzamiento = 1990,
                    tamano = "Mediano",
                    estadoConservacion = "Bueno",
                    ilustrador = "Ilustrador E",
                    paisOrigen = "España",
                    precioEstimado = 400.0
                )
            )
        }
    }
}