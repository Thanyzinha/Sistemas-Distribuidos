import java.io.*;
import java.net.*;

public class BankClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println(in.readLine()); // Mensagem inicial
            System.out.print("Digite o número da conta: ");
            out.println(console.readLine());

            System.out.println(in.readLine()); // Solicitação de operação
            System.out.print("Digite o tipo de operação (saque ou deposito): ");
            out.println(console.readLine());

            System.out.println(in.readLine()); // Solicitação de valor
            System.out.print("Digite o valor: ");
            out.println(console.readLine());

            // Recebe as mensagens do servidor
            String resposta;
            while ((resposta = in.readLine()) != null) {
                System.out.println(resposta);
            }

        } catch (IOException e) {
            System.err.println("Erro no cliente: " + e.getMessage());
        }
    }
}
