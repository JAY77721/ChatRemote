package gui;

import client.PeerClient;

import javax.swing.*;
import java.awt.*;


public class FriendConnectWindow extends JFrame {
    private JTextField ipField;
    private JTextField friendCodeField;
    private JButton connectButton;

    public FriendConnectWindow() {
        setTitle("Connect to Friend");
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Friend's IP:"));
        ipField = new JTextField();
        add(ipField);

        add(new JLabel("Friend Code:"));
        friendCodeField = new JTextField();
        add(friendCodeField);

        connectButton = new JButton("Connect");
        add(connectButton);

        connectButton.addActionListener(e -> connectToFriend());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
    }

    private static final int SERVER_PORT = 8888;

    private void connectToFriend() {
        String ip = ipField.getText().trim();
        String friendCode = friendCodeField.getText().trim();

        if (ip.isEmpty() || friendCode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both IP and friend code.");
            return;
        }

        // 使用构造方法创建 PeerClient 对象
        PeerClient client = new PeerClient(ip, SERVER_PORT, friendCode);

        // 启动连接
        new Thread(() -> {
            client.connect();  // 发起连接

            // 等待连接成功的状态
            while (!client.isConnected()) {
                try {
                    Thread.sleep(100);  // 每100ms检查一次连接状态
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            // 连接成功后，关闭好友连接窗口，打开聊天窗口
            SwingUtilities.invokeLater(() -> {
                dispose();  // 关闭连接窗口
                ChatWindow chatWindow = new ChatWindow(client);  // 打开聊天窗口
                chatWindow.setVisible(true);
            });
        }).start();
    }
}
