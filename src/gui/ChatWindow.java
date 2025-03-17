package gui;

import client.PeerClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatWindow extends JFrame {
    private PeerClient client;
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;

    public ChatWindow(PeerClient client) {
        this.client = client;
        setTitle("Chat Window");

        // 聊天区域
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        // 输入区域
        messageField = new JTextField(30);
        sendButton = new JButton("Send");

        // 底部面板，放置文本框和按钮
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(messageField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        // 添加到框架中
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // 按钮发送消息
        sendButton.addActionListener(e -> sendMessage());
        // 按 Enter 键发送消息
        messageField.addActionListener(e -> sendMessage());
        // 按 Ctrl+Enter 发送消息
        messageField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
                }
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 320);
        setLocationRelativeTo(null);  // 居中显示

        // 启动消息接收线程
        new Thread(() -> {
            try {
                String message;
                while ((message = client.receiveMessage()) != null) {
                    displayMessage("Friend: " + message);
                }
            } catch (Exception e) {
                displayMessage("⚠️ Connection lost: " + e.getMessage());
                disableChat();
            }
        }).start();
    }

    // 发送消息
    private void sendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            client.sendMessage(message);
            displayMessage("You: " + message);
            messageField.setText("");
        }
    }

    // 显示消息
    public void displayMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            chatArea.append(message + "\n");
            chatArea.setCaretPosition(chatArea.getDocument().getLength()); // 自动滚动到底部
        });
    }

    // 禁用发送区域
    private void disableChat() {
        SwingUtilities.invokeLater(() -> {
            messageField.setEditable(false);
            sendButton.setEnabled(false);
        });
    }

}
