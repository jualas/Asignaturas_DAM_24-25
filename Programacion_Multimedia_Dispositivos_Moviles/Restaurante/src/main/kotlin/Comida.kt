package es.jualas

enum class TipoComida {
    ENTRANTE, PRINCIPAL, POSTRE
}

class Comida(
    foto: String,
    precio: Float,
    descripcion: String,
    val tipo: TipoComida
) : Producto(foto, precio, descripcion) {

    override fun calcularPrecio(): Float {
        return precio
    }

    override fun obtenerIVA(): Float {
        return IVA_REDUCIDO
    }

    override fun toString(): String {
        return "Comida(tipo=$tipo, ${super.toString()})"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Comida) return false
        return tipo == other.tipo
    }
}