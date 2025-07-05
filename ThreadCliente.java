package zap;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ThreadCliente extends Thread {
    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter saida;
    private String nome;
    private List<ThreadCliente> clientes;

    public ThreadCliente(Socket socket, List<ThreadCliente> clientes) {
        this.socket = socket;
        this.clientes = clientes;
        try {
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            saida = new PrintWriter(socket.getOutputStream(), true);

            this.nome = entrada.readLine();
            
            this.clientes.add(this);

            System.out.println("[Servidor]: " + nome + " conectou-se. Total de clientes: " + clientes.size());
            enviarParaTodos("[Servidor]: " + nome + " entrou no chat.");
            
            atualizarListaUsuariosParaTodos();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String mensagem;
        try {
            while ((mensagem = entrada.readLine()) != null) {
                String[] partes = mensagem.split(":", 2);
                if (partes.length == 2) {
                    String destino = partes[0].trim();
                    String corpo = partes[1].trim();

                    if (destino.equalsIgnoreCase("todos")) {
                        enviarParaTodos("[" + nome + " para Todos]: " + corpo);
                    } else {
                        enviarPrivado(destino, corpo, nome);
                    }
                }
            }
        } catch (IOException e) {
        } finally {
            clientes.remove(this);
            try {
                System.out.println("[Servidor]: " + nome + " desconectou-se. Total de clientes: " + clientes.size());
                enviarParaTodos("[Servidor]: " + nome + " saiu do chat.");
                
                atualizarListaUsuariosParaTodos();
                
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void enviarParaTodos(String msg) {
        for (ThreadCliente cliente : clientes) {
            cliente.saida.println(msg);
        }
    }

    private void enviarPrivado(String destino, String msg, String remetente) {
        boolean encontrado = false;
        for (ThreadCliente cliente : clientes) {
            if (cliente.nome.equalsIgnoreCase(destino)) {
                cliente.saida.println("[Privado de " + remetente + "]: " + msg);
                this.saida.println("[Você para " + destino + "]: " + msg);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            saida.println("[Servidor]: Usuário '" + destino + "' não encontrado ou offline.");
        }
    }

    private void atualizarListaUsuariosParaTodos() {
        System.out.println("[Servidor]: Atualizando lista de usuários para " + clientes.size() + " clientes.");
        StringBuilder sb = new StringBuilder();
        sb.append("/usuarios");
        for (ThreadCliente c : clientes) {
            sb.append(" ").append(c.nome);
        }
        enviarParaTodos(sb.toString());
    }
}