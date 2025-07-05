# Aplicação de Chat em Java (Sockets e Multithreading)

![Status do Projeto: Concluído](https://img.shields.io/badge/status-concluído-brightgreen)

## 📝 Descrição do Projeto

Este projeto é uma aplicação de chat cliente-servidor desenvolvida em Java como um projeto acadêmico. O sistema permite que múltiplos usuários se conectem a um servidor central e comuniquem-se em tempo real, seja em um canal público com todos os usuários ou através de mensagens privadas.

O principal objetivo foi aplicar e aprofundar conhecimentos em programação de redes com Sockets, concorrência com Threads e desenvolvimento de interface gráfica com Swing.

## ✨ Funcionalidades

- **Arquitetura Cliente-Servidor:** Comunicação centralizada através de um servidor dedicado.
- **Múltiplos Clientes:** O servidor utiliza **multithreading** para gerenciar várias conexões de clientes de forma simultânea e independente.
- **Mensagens Públicas:** Envio de mensagens para todos os usuários conectados no chat.
- **Mensagens Privadas:** Opção de enviar mensagens diretas para um usuário específico.
- **Lista de Usuários Online:** A interface do cliente exibe uma lista dinâmica com todos os usuários conectados, que é atualizada em tempo real sempre que alguém entra ou sai do chat.
- **Interface Gráfica:** Interface de usuário (GUI) intuitiva e funcional desenvolvida com a biblioteca **Java Swing**.

## 🛠️ Tecnologias Utilizadas

- **Java**
- **Java Sockets:** Para a comunicação via rede TCP/IP.
- **Java Swing:** Para a construção da interface gráfica do cliente.
- **Java Threads:** Para a gestão de concorrência no lado do servidor.
- **`CopyOnWriteArrayList`:** Utilizada para garantir a segurança e a consistência da lista de clientes em um ambiente concorrente (thread-safety).

## 🚀 Como Executar o Projeto

Para executar este projeto, você precisará ter o **Java Development Kit (JDK)** instalado em sua máquina.

1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/seu-usuario/seu-repositorio.git](https://github.com/seu-usuario/seu-repositorio.git)
