import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Servidor Socket esperando conexão...");

            // Aguarda uma conexão do cliente
            try (Socket clientSocket = serverSocket.accept();
                 DataInputStream input = new DataInputStream(clientSocket.getInputStream());
                 DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream())) {

                System.out.println("Cliente conectado.");

                // Recebe as coordenadas x e y enviadas pelo cliente
                double x = input.readDouble();
                double y = input.readDouble();

                // Calcula as coordenadas polares
                double r = Math.sqrt(x * x + y * y);
                double theta = Math.atan2(y, x);

                // Envia as coordenadas polares de volta para o cliente
                output.writeDouble(r);
                output.writeDouble(theta);

                System.out.println("Conversão concluída e enviada ao cliente.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
