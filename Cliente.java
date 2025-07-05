package zap;

import java.io.*;
import java.net.Socket;

public class Cliente {
    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter saida;

    public Cliente(String host, int porta) throws IOException {
        socket = new Socket(host, porta);
        entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        saida = new PrintWriter(socket.getOutputStream(), true);
    }

    public BufferedReader getEntrada() {
        return entrada;
    }

    public PrintWriter getSaida() {
        return saida;
    }
} 
