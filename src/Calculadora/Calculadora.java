package Calculadora;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que representa la lógica de la calculadora
 * @author Pablo Dopazo Suárez
 * @version 1.0.0
 */

public class Calculadora extends JFrame{
    /**Contenido de los botones */
    private String[] buttonsText = {
        "1", "2", "3", "+",
        "4", "5", "6", "/",
        "7", "8", "9", "-", 
        "0", "=", "DEL", "*" };
    
        public void init(){
        setVisible(true);
        setTitle("Calcuradora en Java");
        Toolkit screen = Toolkit.getDefaultToolkit();
        Dimension screenSizes = screen.getScreenSize();
        /**Altura de la pantalla*/
        int screenHeight = (int) screenSizes.getHeight();
        /**Anchura de la pantalla*/
        int screenWidth = (int) screenSizes.getWidth();
        setSize(screenWidth/2, 600);
        setLocation(screenWidth/4, screenHeight/4);
        getContentPane().setBackground(new Color(46,46,46));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(4, 4, 5, 5));

        for (String texto : buttonsText) {
            JButton boton = new JButton(texto);
            boton.setFont(new Font("Arial", Font.BOLD, 20));
            boton.addActionListener((ActionListener) this);
            panelBotones.add(boton);
        }

        add(panelBotones, BorderLayout.CENTER);
    }
}
