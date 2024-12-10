package Calculadora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Clase que representa una calculadora con interfaz gráfica.
 * 
 * @author Pablo Dopazo Suárez
 * @version 1.0.0
 */
public class Calculadora extends JFrame implements ActionListener, KeyListener, ItemListener {

    /** Contenido de los botones de la calculadora */
    private String[] buttonsText = {
        "1", "2", "3", "+",
        "4", "5", "6", "/",
        "7", "8", "9", "-",
        "0", ",", "DEL", "="
    };

    /** Área de visualización donde se muestran las operaciones y resultados */
    private JTextArea display;

    /** Checkbox para seleccionar el modo teclado numérico */
    private JCheckBox tecladoNumericoMode;

    /** Checkbox para seleccionar el modo solo ratón */
    private JCheckBox soloRatonMode;

    /** Checkbox para seleccionar el modo mixto */
    private JCheckBox modoMixtoMode; 

    /** Panel que contiene los botones de la calculadora */
    private JPanel panelBotones;

    /** Operación completa en formato de texto */
    private String operacionCompleta = "";

    /** Resultado de la operación actual */
    private double resultado = 0;

    /** Indica si es una nueva operación */
    private boolean nuevaOperacion = true;

    /** Modo que indica si se puede usar el teclado numérico */
    private boolean modoTecladoNumerico = false;

    /** Modo que indica si se puede usar solo el ratón */
    private boolean soloRaton = false;

    /** Modo que permite la combinación de teclado y ratón */
    private boolean modoMixto = false;

    /**
     * Constructor de la clase Calculadora. Inicializa la interfaz gráfica.
     */
    public Calculadora() {
        init();
    }

    /**
     * Inicializa los componentes de la interfaz gráfica de la calculadora.
     * Configura los botones, display y modos de interacción.
     */
    public void init() {
        setTitle("Calculadora");
        Toolkit screen = Toolkit.getDefaultToolkit();
        Dimension screenSizes = screen.getScreenSize();
        setSize(screenSizes.width / 2, 650);
        setLocation(screenSizes.width / 4, screenSizes.height / 4);
        getContentPane().setBackground(new Color(46, 46, 46));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        display = new JTextArea(2, 20);
        display.setEditable(false);
        display.setBackground(new Color(33, 33, 33));
        display.setForeground(Color.WHITE);
        display.setFont(new Font("Arial", Font.BOLD, 70));
        display.setLineWrap(true);
        display.setWrapStyleWord(true);
        display.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        display.addKeyListener(this);

        tecladoNumericoMode = new JCheckBox("Modo Teclado Numérico");
        tecladoNumericoMode.setForeground(Color.WHITE);
        tecladoNumericoMode.setBackground(new Color(46, 46, 46));
        tecladoNumericoMode.setFocusPainted(false);
        tecladoNumericoMode.setFont(new Font("Arial", Font.PLAIN, 16));
        tecladoNumericoMode.addItemListener(this);

        soloRatonMode = new JCheckBox("Modo Solo Ratón");
        soloRatonMode.setForeground(Color.WHITE);
        soloRatonMode.setBackground(new Color(46, 46, 46));
        soloRatonMode.setFocusPainted(false);
        soloRatonMode.setFont(new Font("Arial", Font.PLAIN, 16));
        soloRatonMode.addItemListener(this);

        modoMixtoMode = new JCheckBox("Modo Mixto");
        modoMixtoMode.setForeground(Color.WHITE);
        modoMixtoMode.setBackground(new Color(46, 46, 46));
        modoMixtoMode.setFocusPainted(false);
        modoMixtoMode.setFont(new Font("Arial", Font.PLAIN, 16));
        modoMixtoMode.addItemListener(this);

        JPanel panelOpciones = new JPanel(new GridLayout(3, 1, 5, 5));
        panelOpciones.setOpaque(false);
        panelOpciones.add(tecladoNumericoMode);
        panelOpciones.add(soloRatonMode);
        panelOpciones.add(modoMixtoMode);

        panelBotones = new JPanel(new GridBagLayout());
        panelBotones.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.ipadx = 30;
        gbc.ipady = 20;

        int row = 0, col = 0;
        for (String texto : buttonsText) {
            JButton boton = new JButton(texto);

            if (texto.matches("[+\\-*/=]")) {
                boton.setBackground(new Color(41, 121, 255));
                boton.setForeground(Color.WHITE);
            } else {
                boton.setBackground(new Color(66, 66, 66));
                boton.setForeground(Color.WHITE);
            }

            boton.setFont(new Font("Arial", Font.PLAIN, 16));
            boton.setFocusPainted(false);

            boton.addActionListener(this);

            gbc.gridx = col;
            gbc.gridy = row;
            panelBotones.add(boton, gbc);

            col++;
            if (col > 3) {
                col = 0;
                row++;
            }
        }

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelSuperior.setOpaque(false);
        panelSuperior.add(panelOpciones, BorderLayout.WEST);
        panelSuperior.add(display, BorderLayout.CENTER);

        JPanel panelConMargen = new JPanel(new BorderLayout());
        panelConMargen.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelConMargen.setOpaque(false);
        panelConMargen.add(panelSuperior, BorderLayout.NORTH);
        panelConMargen.add(panelBotones, BorderLayout.CENTER);

        add(panelConMargen, BorderLayout.CENTER);
        setVisible(true);

        actualizarEstadoBotones();
    }

    /**
     * Maneja las acciones realizadas sobre los botones de la calculadora.
     * @param e Evento que contiene la acción realizada.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!modoMixto && !soloRaton && modoTecladoNumerico) {
            return;
        }

        String command = e.getActionCommand();
        handleInput(command);
    }

    /**
     * Maneja las teclas presionadas cuando el teclado es utilizado.
     * @param e Evento de tecla presionada.
     */
    @Override
    public void keyTyped(KeyEvent e) {
        if (!modoMixto && (soloRaton || !modoTecladoNumerico)) {
            e.consume();
            return;
        }

        char keyChar = e.getKeyChar();
        if (Character.isDigit(keyChar) || "+-*/".indexOf(keyChar) >= 0 || keyChar == ',' || keyChar == '.') {
            handleInput(String.valueOf(keyChar));
        } else if (keyChar == '\n') {
            handleInput("=");
        } else if (keyChar == '\b') {
            handleInput("DEL");
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    /**
     * Maneja los cambios de estado de los checkboxes (modos de interacción).
     * @param e Evento que representa el cambio de estado del checkbox.
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == tecladoNumericoMode) {
            modoTecladoNumerico = tecladoNumericoMode.isSelected();
            if (modoTecladoNumerico) modoMixtoMode.setSelected(false);
        } else if (e.getSource() == soloRatonMode) {
            soloRaton = soloRatonMode.isSelected();
            if (soloRaton) modoMixtoMode.setSelected(false);
        } else if (e.getSource() == modoMixtoMode) {
            modoMixto = modoMixtoMode.isSelected();
            if (modoMixto) {
                tecladoNumericoMode.setSelected(false);
                soloRatonMode.setSelected(false);
            }
        }

        actualizarEstadoBotones();
    }

    /**
     * Actualiza el estado de los botones de la calculadora según los modos activos.
     */
    private void actualizarEstadoBotones() {
        boolean habilitarBotones = soloRaton || modoMixto;

        for (Component componente : panelBotones.getComponents()) {
            if (componente instanceof JButton) {
                componente.setEnabled(habilitarBotones);
            }
        }
    }

    /**
     * Maneja la entrada del usuario, ya sea a través del teclado o los botones.
     * @param input Cadena de texto que representa la entrada del usuario.
     */
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
                if (!operacionCompleta.contains(",") && !operacionCompleta.contains(".")) {
                    operacionCompleta += ",";
                    display.setText(operacionCompleta.replace('.', ','));
                }
            } else if ("+".equals(input) || "-".equals(input) || "*".equals(input) || "/".equals(input)) {
                if (nuevaOperacion) {
                    operacionCompleta = String.valueOf(resultado);
                    nuevaOperacion = false;
                }
                operacionCompleta += input;
                display.setText(operacionCompleta.replace('.', ','));
            } else {
                if (nuevaOperacion && Character.isDigit(input.charAt(0))) {
                    operacionCompleta = input;
                    nuevaOperacion = false;
                } else {
                    operacionCompleta += input;
                }
                display.setText(operacionCompleta.replace('.', ','));
            }
        } catch (Exception ex) {
            display.setText("Error");
        }
    }

    /**
     * Calcula el resultado de la operación completa y lo muestra en la pantalla.
     */
    private void calcularResultado() {
        try {
            resultado = eval(operacionCompleta.replace(',', '.'));
            String resultadoStr = String.valueOf(resultado).replace('.', ',');
            display.setText(operacionCompleta.replace('.', ',') + "\n" + resultadoStr);
            operacionCompleta = resultadoStr;
            nuevaOperacion = true;
        } catch (Exception ex) {
            display.setText("Error");
        }
    }

    /**
     * Evalúa la operación matemática representada por una cadena de texto.
     * @param operacion La operación matemática en formato de cadena.
     * @return El resultado de la operación.
     */
    private double eval(String operacion) {
        String[] tokens = operacion.split("(?<=[-+*/])|(?=[-+*/])");
        double resultado = Double.parseDouble(tokens[0]);
        for (int i = 1; i < tokens.length; i += 2) {
            String operador = tokens[i];
            double num = Double.parseDouble(tokens[i + 1]);
            resultado = switch (operador) {
                case "+" -> resultado + num;
                case "-" -> resultado - num;
                case "*" -> resultado * num;
                case "/" -> resultado / num;
                default -> throw new IllegalArgumentException("Operador no válido");
            };
        }
        return resultado;
    }

    /**
     * Método principal que ejecuta la calculadora. 
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        new Calculadora();
    }
}
