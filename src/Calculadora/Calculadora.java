package Calculadora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Clase que representa la lógica de la calculadora
 */
public class Calculadora extends JFrame implements ActionListener, KeyListener {

    /** Contenido de los botones */
    private String[] buttonsText = {
        "1", "2", "3", "+",
        "4", "5", "6", "/",
        "7", "8", "9", "-",
        "0", "=", "DEL", ","
    };

    /** TextField para mostrar los resultados */
    private JTextField display;

    /** Variables para la lógica de cálculo */
    private String operacionCompleta = ""; // Cadena que almacena toda la operación
    private double resultado = 0; // Resultado actual
    private boolean nuevaOperacion = true; // Indica si inicia una nueva operación

    /** Constructor de la calculadora */
    public Calculadora() {
        init();
    }

    /** Inicialización de la ventana */
    public void init() {
        setTitle("Calculadora - Decimales con ',' y '.'");
        Toolkit screen = Toolkit.getDefaultToolkit();
        Dimension screenSizes = screen.getScreenSize();
        setSize(screenSizes.width / 2, 600); // Tamaño de la ventana
        setLocation(screenSizes.width / 4, screenSizes.height / 4);
        getContentPane().setBackground(new Color(46, 46, 46)); // Fondo gris oscuro
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear el área de visualización
        display = new JTextField();
        display.setEditable(false);
        display.setBackground(new Color(33, 33, 33)); // Fondo negro
        display.setForeground(Color.WHITE); // Texto blanco
        display.setFont(new Font("Arial", Font.BOLD, 24)); // Fuente grande
        display.setHorizontalAlignment(JTextField.RIGHT); // Alinear texto a la derecha
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Espaciado interno

        // Agregar el KeyListener para capturar entradas del teclado
        display.addKeyListener(this);

        // Panel con GridBagLayout
        JPanel panelBotones = new JPanel(new GridBagLayout());
        panelBotones.setOpaque(false); // Fondo transparente

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; // Ajustar tamaño de los botones
        gbc.insets = new Insets(5, 5, 5, 5); // Márgenes entre los botones

        // Configuración para ajustar tamaños personalizados
        gbc.weightx = 1.0; // Expandir horizontalmente
        gbc.weighty = 1.0; // Expandir verticalmente
        gbc.ipadx = 30; // Padding interno horizontal
        gbc.ipady = 20; // Padding interno vertical

        // Añadir botones al panel
        int row = 0, col = 0;
        for (String texto : buttonsText) {
            JButton boton = new JButton(texto);

            // Personalizar el color del botón según su tipo
            if (texto.matches("[+\\-*/=]")) { // Botones de operación
                boton.setBackground(new Color(41, 121, 255)); // Azul eléctrico
                boton.setForeground(Color.WHITE); // Texto blanco
            } else { // Botones numéricos, "," y "DEL"
                boton.setBackground(new Color(66, 66, 66)); // Gris medio
                boton.setForeground(Color.WHITE); // Texto blanco
            }

            boton.setFont(new Font("Arial", Font.PLAIN, 16)); // Tamaño de fuente
            boton.setFocusPainted(false); // Eliminar borde de enfoque

            boton.addActionListener(this);

            // Posición del botón en la cuadrícula
            gbc.gridx = col; // Columna
            gbc.gridy = row; // Fila
            panelBotones.add(boton, gbc);

            // Ajustar la posición de la cuadrícula
            col++;
            if (col > 3) { // 4 columnas por fila
                col = 0;
                row++;
            }
        }

        // Agregar margen al panel principal
        JPanel panelConMargen = new JPanel(new BorderLayout());
        panelConMargen.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Márgenes alrededor
        panelConMargen.setOpaque(false); // Fondo transparente
        panelConMargen.add(display, BorderLayout.NORTH); // Agregar la pantalla en la parte superior
        panelConMargen.add(panelBotones, BorderLayout.CENTER);

        add(panelConMargen, BorderLayout.CENTER);
        setVisible(true); // Mostrar la ventana después de añadir los componentes
    }

    /** Maneja los eventos de los botones */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        handleInput(command);
    }

    /** Maneja la entrada del teclado */
    @Override
    public void keyTyped(KeyEvent e) {
        char keyChar = e.getKeyChar();

        // Verifica si la tecla presionada es un número, operador, punto o coma
        if (Character.isDigit(keyChar) || "+-*/".indexOf(keyChar) >= 0 || keyChar == '.' || keyChar == ',') {
            handleInput(String.valueOf(keyChar));
        } else if (keyChar == '\n') { // Tecla Enter
            handleInput("=");
        } else if (keyChar == '\b') { // Tecla Backspace
            handleInput("DEL");
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // No implementado
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // No implementado
    }

    private void handleInput(String input) {
        try {
            if ("DEL".equals(input)) {
                if (!operacionCompleta.isEmpty()) {
                    operacionCompleta = operacionCompleta.substring(0, operacionCompleta.length() - 1);
                    display.setText(operacionCompleta.replace('.', ','));
                }
            } else if ("=".equals(input)) {
                calcularResultado();
            } else if (input.equals(",") || input.equals(".")) {
                // Si ya existe un punto o coma en el número, no permitir agregar otro
                if (!operacionCompleta.contains(",") && !operacionCompleta.contains(".")) {
                    operacionCompleta += ",";
                    display.setText(operacionCompleta.replace('.', ','));
                }
            } else if ("+".equals(input) || "-".equals(input) || "*".equals(input) || "/".equals(input)) {
                // Si ya tenemos un resultado calculado y queremos empezar una nueva operación
                if (nuevaOperacion) {
                    operacionCompleta = String.valueOf(resultado); // Inicia con el resultado previo
                    nuevaOperacion = false;
                }
                // Agregar el operador a la cadena de operación
                operacionCompleta += input;
                display.setText(operacionCompleta.replace('.', ','));
            } else {
                // Si estamos en una nueva operación, comenzamos de nuevo con el número
                if (nuevaOperacion && Character.isDigit(input.charAt(0))) {
                    operacionCompleta = input;
                    nuevaOperacion = false;
                } else {
                    // Concatenar la entrada al número actual
                    operacionCompleta += input;
                }
                display.setText(operacionCompleta.replace('.', ','));
            }
        } catch (Exception ex) {
            display.setText("Error");
        }
    }
    
    /** Calcula el resultado de la operación */
    private void calcularResultado() {
        try {
            // Evalúa la operación completa
            resultado = eval(operacionCompleta.replace(',', '.'));
            operacionCompleta = String.valueOf(resultado).replace('.', ','); // Actualiza la operación con el resultado
    
            // Verificar si el resultado es negativo
            if (resultado < 0) {
                display.setForeground(new Color(255, 87, 87)); // Rojo oscuro para números negativos
            } else {
                display.setForeground(Color.WHITE); // Volver al texto blanco
            }
    
            display.setText(operacionCompleta); // Mostrar el resultado
            nuevaOperacion = true; // Permitir comenzar una nueva operación
        } catch (ArithmeticException ex) {
            display.setText("Error: División por cero");
            display.setForeground(Color.RED);
        } catch (Exception ex) {
            display.setText("Error: Operación inválida");
            display.setForeground(Color.RED);
        }
    }
    



    /** Evalúa una operación matemática simple */
    private double eval(String operacion) {
        String[] tokens = operacion.split("([+\\-*/])");
        String operador = operacion.replaceAll("[^+\\-*/]", "");
        double num1 = Double.parseDouble(tokens[0]);
        double num2 = Double.parseDouble(tokens[1]);
        return switch (operador) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "*" -> num1 * num2;
            case "/" -> num1 / num2;
            default -> throw new IllegalArgumentException("Operador no válido");
        };
    }

    /** Método principal para iniciar la aplicación */
    public static void main(String[] args) {
        new Calculadora();
    }
}
