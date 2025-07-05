package zap;

import java.awt.*;
import javax.swing.*;

public class ClienteView extends JFrame {
    private JTextField txtMensagem;
    private JComboBox<String> comboDestinatario;
    private JTextArea areaChat;
    private Cliente cliente;
    private String nome;

    public ClienteView() {
        solicitarNome();
        configurarInterface();
        conectarAoServidor();
    }

    private void solicitarNome() {
        while (nome == null || nome.trim().isEmpty() || nome.contains(" ")) {
            nome = JOptionPane.showInputDialog(this, "Digite seu nome de usuário (sem espaços):", "Login", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void configurarInterface() {
        setTitle("Chat - " + nome);
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        areaChat = new JTextArea();
        areaChat.setEditable(false);
        areaChat.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaChat.setLineWrap(true);
        areaChat.setWrapStyleWord(true);
        add(new JScrollPane(areaChat), BorderLayout.CENTER);

        JPanel painelMensagem = new JPanel(new BorderLayout(10, 10));
        
        comboDestinatario = new JComboBox<>();
        comboDestinatario.addItem("Todos");

        txtMensagem = new JTextField();
        
        JButton btnEnviar = new JButton("Enviar");

        painelMensagem.add(new JLabel("Para:"), BorderLayout.WEST);
        painelMensagem.add(comboDestinatario, BorderLayout.CENTER);
        
        JPanel painelInput = new JPanel(new BorderLayout(10,10));
        painelInput.add(txtMensagem, BorderLayout.CENTER);
        painelInput.add(btnEnviar, BorderLayout.EAST);
        
        add(painelMensagem, BorderLayout.NORTH);
        add(painelInput, BorderLayout.SOUTH);

        btnEnviar.addActionListener(e -> enviarMensagem());
        txtMensagem.addActionListener(e -> enviarMensagem());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void conectarAoServidor() {
        try {
            cliente = new Cliente("localhost", 12345);
            cliente.getSaida().println(nome);
            new Thread(this::ouvirMensagens).start();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao servidor: " + e.getMessage(), "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void enviarMensagem() {
        String destino = (String) comboDestinatario.getSelectedItem();
        String mensagem = txtMensagem.getText().trim();
        
        if (destino == null || mensagem.isEmpty()) {
            return;
        }
        
        cliente.getSaida().println(destino.toLowerCase() + ":" + mensagem);
        txtMensagem.setText("");
        txtMensagem.requestFocus();
    }

    private void ouvirMensagens() {
        try {
            String linha;
            while ((linha = cliente.getEntrada().readLine()) != null) {
                if (linha.startsWith("/usuarios")) {
                    atualizarListaUsuariosNaView(linha);
                } else {
                    final String msgFinal = linha;
                    SwingUtilities.invokeLater(() -> areaChat.append(msgFinal + "\n"));
                }
            }
        } catch (Exception e) {
            SwingUtilities.invokeLater(() -> areaChat.append("--- Conexão com o servidor perdida. ---\n"));
        }
    }
    
    private void atualizarListaUsuariosNaView(String linha) {
        String[] usuarios = linha.substring(10).split(" ");
        
        SwingUtilities.invokeLater(() -> {
            Object selecionado = comboDestinatario.getSelectedItem();
            
            comboDestinatario.removeAllItems();
            comboDestinatario.addItem("Todos");
            for (String usuario : usuarios) {
                if (!usuario.equals(this.nome)) {
                    comboDestinatario.addItem(usuario);
                }
            }
            
            comboDestinatario.setSelectedItem(selecionado);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClienteView::new);
    }
}