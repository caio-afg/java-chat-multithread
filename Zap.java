package zap;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class Zap {
    private static final int PORTA = 12345;
    private static List<ThreadCliente> clientes = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORTA)) {
            System.out.println("Servidor iniciado na porta " + PORTA);
            while (true) {
                Socket socket = serverSocket.accept();
                ThreadCliente cliente = new ThreadCliente(socket, clientes);
                cliente.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}