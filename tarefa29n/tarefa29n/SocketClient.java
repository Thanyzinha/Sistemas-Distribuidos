import javax.swing.*;
import java.io.*;
import java.net.*;

public class SocketClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000);
             DataOutputStream output = new DataOutputStream(socket.getOutputStream());
             DataInputStream input = new DataInputStream(socket.getInputStream())) {

            double x = Double.parseDouble(JOptionPane.showInputDialog("Insira a coordenada x:"));
            double y = Double.parseDouble(JOptionPane.showInputDialog("Insira a coordenada y:"));

            output.writeDouble(x);
            output.writeDouble(y);

            double r = input.readDouble();
            double theta = input.readDouble();

            JOptionPane.showMessageDialog(null, "Coordenadas polares:\nr = " + r + "\nθ = " + theta + " radianos");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
