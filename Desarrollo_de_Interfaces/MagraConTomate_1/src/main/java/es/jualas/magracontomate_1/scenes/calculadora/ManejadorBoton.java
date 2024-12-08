package es.jualas.magracontomate_1.scenes.calculadora;

import es.jualas.magracontomate_1.scenes.calculadora.Calculadora;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ManejadorBoton implements EventHandler<ActionEvent> {

    private final String valor;  // Valor del botón presionado
    private final Calculadora calculadora;  // Instancia de la calculadora

    public ManejadorBoton(String valor, Calculadora calculadora) {
        this.valor = valor;  // Asignar el valor del botón
        this.calculadora = calculadora;  // Asignar la instancia de la calculadora
    }

    public String getValor() {
        return valor;
    }

    @Override
    public void handle(ActionEvent event) {
        if (esEntradaValida()) {
            calculadora.procesarEntrada(valor);
        }
    }

    private boolean esEntradaValida() {
        String textoActual = calculadora.getTextoActual();
        
        // Si el valor es "0" y el texto actual ya es "0", no permitir más ceros
        if (valor.equals("0") && textoActual.equals("0")) {
            return false;
        }
        
        // Si el texto actual es "0" y el nuevo valor no es una operación, reemplazar el "0"
        if (textoActual.equals("0") && !esOperacion(valor)) {
            calculadora.setTextoActual("");
        }
        
        return true;
    }

    private boolean esOperacion(String valor) {
        return valor.equals("+") || valor.equals("-") || valor.equals("*") || valor.equals("/") || valor.equals("=");
    }
}