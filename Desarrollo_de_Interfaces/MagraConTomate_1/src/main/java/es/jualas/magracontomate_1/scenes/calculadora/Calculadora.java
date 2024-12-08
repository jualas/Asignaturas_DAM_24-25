package es.jualas.magracontomate_1.scenes.calculadora;

import javafx.scene.control.TextField;

public class Calculadora {

    private double primerOperando;
    private double segundoOperando;
    private String operacion;
    private TextField pantalla;
    private boolean primerOperandoAsignado;
    private boolean operacionAsignada;
    private StringBuilder currentInput;

    public Calculadora(TextField pantalla) {
        this.pantalla = pantalla;
        reiniciar();
    }

    // Reinicia la calculadora a su estado inicial
    public void reiniciar() {
        primerOperando = 0;
        segundoOperando = 0;
        operacion = "";
        primerOperandoAsignado = false;
        operacionAsignada = false;
        currentInput = new StringBuilder();
        pantalla.setText("");
    }

    // Asigna el valor del operando actual
    public void asignarOperando(String valor) {
        currentInput.append(valor);
        pantalla.setText(currentInput.toString());
    }

    // Asigna la operación seleccionada (+, -, *, /)
    public void asignarOperacion(String op) {
        if (currentInput.length() > 0) {
            if (!primerOperandoAsignado) {
                primerOperando = Double.parseDouble(currentInput.toString());
                primerOperandoAsignado = true;
            } else {
                segundoOperando = Double.parseDouble(currentInput.toString());
            }
            currentInput.setLength(0); // Limpiar la entrada actual
        }
        if (primerOperandoAsignado && !operacionAsignada) {
            this.operacion = op;
            operacionAsignada = true;
            pantalla.setText(op); // Mostrar solo la operación
        } else if (operacionAsignada) {
            pantalla.setText("Error: Operación ya asignada");
        } else {
            pantalla.setText("Error: Operando no asignado");
        }
    }

    // Realiza el cálculo de la operación entre los operandos
    public void calcular() {
        if (currentInput.length() > 0) {
            segundoOperando = Double.parseDouble(currentInput.toString());
        }
        if (operacion.isEmpty() || !primerOperandoAsignado) {
            pantalla.setText("Error: Operación no asignada");
            return;
        }

        double resultado = 0;

        try {
            switch (operacion) {
                case "+":
                    resultado = primerOperando + segundoOperando;
                    break;
                case "-":
                    resultado = primerOperando - segundoOperando;
                    break;
                case "*":
                    resultado = primerOperando * segundoOperando;
                    break;
                case "/":
                    if (segundoOperando == 0) {
                        throw new ArithmeticException("Error: División por 0");
                    }
                    resultado = primerOperando / segundoOperando;
                    break;
                default:
                    pantalla.setText("Error: Operación no válida");
                    return;
            }
            pantalla.setText(String.valueOf(resultado));
        } catch (ArithmeticException e) {
            pantalla.setText(e.getMessage());
        }

        // Preparar para nueva operación
        primerOperando = resultado;
        primerOperandoAsignado = true;
        segundoOperando = 0;
        operacion = "";
        operacionAsignada = false;
        currentInput.setLength(0); // Limpiar la entrada actual
    }

    // Método para procesar la entrada del botón
    public void procesarEntrada(String valor) {
        switch (valor) {
            case "C":
                reiniciar();
                break;
            case "=":
                calcular();
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                asignarOperacion(valor);
                break;
            default:
                asignarOperando(valor);
                break;
        }
    }
    public String getTextoActual() {
        return pantalla.getText();
    }

    public void setTextoActual(String texto) {
        pantalla.setText(texto);
    }
}