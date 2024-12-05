package Calculadora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que representa la lógica de la calculadora con GridBagLayout y tamaño personalizado
 */
public class Calculadora extends JFrame implements ActionListener {

    /** Contenido de los botones */
    private String[] buttonsText = {
        "1", "2", "3", "+",
        "4", "5", "6", "/",
        "7", "8", "9", "-",
        "0", "=", "DEL", "*"
    };

    /** Constructor de la calculadora */
    public Calculadora() {
        init();
    }

    /** Inicialización de la ventana */
    public void init() {
        setTitle("Calculadora - Tamaño Personalizado");
        Toolkit screen = Toolkit.getDefaultToolkit();
        Dimension screenSizes = screen.getScreenSize();
        setSize(screenSizes.width / 2, 600); // Tamaño de la ventana
        setLocation(screenSizes.width / 4, screenSizes.height / 4);
        getContentPane().setBackground(new Color(46, 46, 46)); // Fondo gris oscuro
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
            } else { // Botones numéricos y "DEL"
                boton.setBackground(new Color(66, 66, 66)); // Gris medio
                boton.setForeground(Color.WHITE); // Texto blanco
            }

            boton.setFont(new Font("Arial", Font.PLAIN, 16)); // Tamaño de fuente
            boton.setFocusPainted(false); // Eliminar borde de enfoque

            // Establecer un tamaño exacto opcionalmente
            boton.setPreferredSize(new Dimension(30, 30)); // Tamaño fijo del botón (opcional)

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
        panelConMargen.setBorder(BorderFactory.createEmptyBorder(70, 70, 30, 70)); // Márgenes alrededor
        panelConMargen.setOpaque(false); // Fondo transparente
        panelConMargen.add(panelBotones, BorderLayout.CENTER);

        add(panelConMargen, BorderLayout.CENTER);
        setVisible(true); // Mostrar la ventana después de añadir los componentes
    }

    /** Maneja los eventos de los botones */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Obtener el texto del botón presionado
        String command = e.getActionCommand();
        System.out.println("Botón presionado: " + command);

        // Aquí puedes añadir la lógica de cálculo
    }

    /** Método principal para iniciar la aplicación */
    public static void main(String[] args) {
        new Calculadora();
    }
}
