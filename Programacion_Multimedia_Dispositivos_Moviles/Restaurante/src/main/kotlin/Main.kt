package es.jualas

fun main() {
    // Crear instancias de productos
    val bebida1 = Bebida("foto_bebida1.jpg", 2.5f, "Coca Cola", 0.5f, false)
    val bebida2 = Bebida("foto_bebida2.jpg", 3.0f, "Cerveza", 0.5f, true)
    val comida1 = Comida("foto_comida1.jpg", 10.0f, "Ensalada", TipoComida.ENTRANTE)
    val comida2 = Comida("foto_comida2.jpg", 15.0f, "Filete", TipoComida.PRINCIPAL)

    // Crear una comanda y agregar productos
    val comanda = Comanda(1, 2)
    comanda.aniadirProducto(bebida1)
    comanda.aniadirProducto(bebida2)
    comanda.aniadirProducto(comida1)
    comanda.aniadirProducto(comida2)

    // Mostrar la lista de productos en la comanda
    println("Productos en la comanda:")
    comanda.getListaProductos().forEach { println(it) }

    // Calcular y mostrar el precio total de la comanda
    val precioTotal = comanda.getPrecioComanda()
    println("Precio total de la comanda: $precioTotal")

    // Calcular y mostrar el precio total con un descuento del 10%
    val precioConDescuento = comanda.getPrecioComanda(10)
    println("Precio total con descuento del 10%: $precioConDescuento")

    // Mostrar el precio total con un descuento del 20%
    val precioConDescuento20 = comanda.getPrecioComanda(20)
    println("Precio total con descuento del 20%: $precioConDescuento20")

    // Mostrar el precio total con un descuento del 30%
    val precioConDescuento30 = comanda.getPrecioComanda(30)
    println("Precio total con descuento del 30%: $precioConDescuento30")
}


