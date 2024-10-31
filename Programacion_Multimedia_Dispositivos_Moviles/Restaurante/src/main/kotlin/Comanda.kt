package es.jualas

class Comanda(
    val numMesa: Int,
    val numComensales: Int
) {
    private val listaProductos: MutableList<Producto> = mutableListOf()

    fun getListaProductos(): List<Producto> {
        return listaProductos
    }

    fun aniadirProducto(producto: Producto) {
        listaProductos.add(producto)
    }

    fun eliminarProducto(producto: Producto) {
        listaProductos.remove(producto)
    }

    fun getPrecioComanda(): Float {
        return listaProductos.sumOf { (it.calcularPrecio() * (1 + it.obtenerIVA())).toDouble() }.toFloat()
    }

    fun getPrecioComanda(descuento: Int): Float {
        val total = getPrecioComanda()
        return total - (total * descuento / 100)
    }

    override fun toString(): String {
        return "Comanda(numMesa=$numMesa, numComensales=$numComensales, listaProductos=$listaProductos)"
    }
}