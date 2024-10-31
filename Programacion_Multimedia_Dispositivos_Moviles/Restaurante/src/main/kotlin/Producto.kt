package es.jualas

abstract class Producto(
    val foto: String,
    val precio: Float,
    val descripcion: String
) {
    companion object {
        const val IVA: Float = 0.21f
        const val IVA_REDUCIDO: Float = 0.10f
    }

    abstract fun calcularPrecio(): Float
    abstract fun obtenerIVA(): Float

    override fun toString(): String {
        return "Producto(foto='$foto', precio=$precio, descripcion='$descripcion')"
    }
}