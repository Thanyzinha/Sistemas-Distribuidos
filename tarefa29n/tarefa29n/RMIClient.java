import javax.swing.*;
import java.rmi.*;

public class RMIClient {
    public static void main(String[] args) {
        try {
            ConverterInterface converter = (ConverterInterface) Naming.lookup("rmi://localhost/Converter");

            double x = Double.parseDouble(JOptionPane.showInputDialog("Insira a coordenada x:"));
            double y = Double.parseDouble(JOptionPane.showInputDialog("Insira a coordenada y:"));

            double[] resultado = converter.converterParaPolar(x, y);

            JOptionPane.showMessageDialog(null, "Coordenadas polares:\nr = " + resultado[0] + "\nθ = " + resultado[1] + " radianos");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
