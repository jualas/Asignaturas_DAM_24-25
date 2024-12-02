package es.jualas.carteles

data class Item(
    val titulo: String,
    val anoLanzamiento: Int,
    val tamano: String,
    val estadoConservacion: String,
    val ilustrador: String,
    val paisOrigen: String,
    val precioEstimado: Double
)