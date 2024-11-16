import java.io.*;
import java.net.*;
import java.util.*;

public class BankServer {
    private static final int PORT = 12345;
    private static final String ARQUIVO_CONTAS = "contas.txt";
    private static Map<Integer, Double> contas = new HashMap<>();

    public static void main(String[] args) {
        carregarContas();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor bancário iniciado na porta " + PORT + ". Aguardando conexões...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    out.println("Conectado ao servidor bancário. Digite o número da conta:");
                    int numeroConta = Integer.parseInt(in.readLine());

                    if (!contas.containsKey(numeroConta)) {
                        out.println("Conta não encontrada. Conexão encerrada.");
                        continue;
                    }

                    out.println("Digite o tipo de operação (saque ou deposito):");
                    String operacao = in.readLine().toLowerCase();

                    out.println("Digite o valor:");
                    double valor = Double.parseDouble(in.readLine());

                    synchronized (contas) {
                        if (operacao.equals("saque")) {
                            if (valor > contas.get(numeroConta)) {
                                out.println("Saldo insuficiente.");
                            } else {
                                contas.put(numeroConta, contas.get(numeroConta) - valor);
                                out.println("Saque realizado. Saldo atual: R$ " + contas.get(numeroConta));
                            }
                        } else if (operacao.equals("deposito")) {
                            contas.put(numeroConta, contas.get(numeroConta) + valor);
                            out.println("Depósito realizado. Saldo atual: R$ " + contas.get(numeroConta));
                        } else {
                            out.println("Operação inválida.");
                        }
                    }

                    salvarContas();
                    out.println("Operação concluída. Conexão encerrada.");
                } catch (Exception e) {
                    System.out.println("Erro ao processar solicitação: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Erro no servidor: " + e.getMessage());
        }
    }

    private static void carregarContas() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_CONTAS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                int numeroConta = Integer.parseInt(partes[0].trim());
                double saldo = Double.parseDouble(partes[1].trim());
                contas.put(numeroConta, saldo);
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar contas: " + e.getMessage());
        }
    }

    private static void salvarContas() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO_CONTAS))) {
            for (Map.Entry<Integer, Double> entrada : contas.entrySet()) {
                writer.println(entrada.getKey() + "," + entrada.getValue());
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar contas: " + e.getMessage());
        }
    }
}
