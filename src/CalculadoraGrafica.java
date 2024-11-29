import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculadoraGrafica extends JFrame implements ActionListener {
    private JTextField display;
    private double numero1, numero2, resultado;
    private char operador;

    public CalculadoraGrafica() {
        // Configuración de la ventana
        setTitle("Calculadora");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Pantalla de la calculadora
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        // Botones de la calculadora
        String[] botones = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+"
        };

        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if ("0123456789.".contains(comando)) {
            display.setText(display.getText() + comando);
        } else if ("/-*+=.".contains(comando)) {
            if (comando.equals("=")) {
                numero2 = Double.parseDouble(display.getText());
                switch (operador) {
                    case '+': resultado = numero1 + numero2; break;
                    case '-': resultado = numero1 - numero2; break;
                    case '*': resultado = numero1 * numero2; break;
                    case '/': resultado = numero2 != 0 ? numero1 / numero2 : Double.NaN; break;
                }
                display.setText(String.valueOf(resultado));
            } else {
                numero1 = Double.parseDouble(display.getText());
                operador = comando.charAt(0);
                display.setText("");
            }
        } else {
            display.setText("Error!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculadoraGrafica calculadora = new CalculadoraGrafica();
            calculadora.setVisible(true);
        });
    }
}
