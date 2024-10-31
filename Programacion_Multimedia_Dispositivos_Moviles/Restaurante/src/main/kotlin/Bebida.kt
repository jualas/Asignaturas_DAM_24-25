package es.jualas

class Bebida(
    foto: String,
    precio: Float,
    descripcion: String,
    val tamLitros: Float,
    val alcoholica: Boolean
) : Producto(foto, precio, descripcion) {

    fun isAlcoholica(): Boolean {
        return alcoholica
    }

    override fun calcularPrecio(): Float {
        return precio
    }

    override fun obtenerIVA(): Float {
        return if (alcoholica) IVA else IVA_REDUCIDO
    }

    override fun toString(): String {
        return "Bebida(tamLitros=$tamLitros, alcoholica=$alcoholica, ${super.toString()})"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Bebida) return false
        return tamLitros == other.tamLitros && alcoholica == other.alcoholica
    }
}