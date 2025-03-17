package client;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import gui.ChatWindow;  // 引入聊天窗口类

public class PeerClient {
    private String ip;
    private int port;
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private ExecutorService executor;  // 用于异步任务的执行器
    private boolean isConnected = false; // 标记连接状态
    private String friendCode;  // 用于存储好友码
    private ChatWindow chatWindow;  // 引用聊天窗口

    public PeerClient(String ip, int port, String friendCode) {
        this.ip = ip;
        this.port = port;
        this.friendCode = friendCode;
        this.executor = Executors.newCachedThreadPool();
    }

    public boolean isConnected() {
        return isConnected;
    }

    // 连接到服务器
    public void connect() {
        executor.submit(() -> {
            try {
                socket = new Socket(ip, port);  // 连接到服务器
                input = new DataInputStream(socket.getInputStream());
                output = new DataOutputStream(socket.getOutputStream());
                isConnected = true;  // 连接成功时设置标志位
                System.out.println("Connected to server: " + ip);

                // 发送好友码进行验证
                authenticateFriendCode();

            } catch (IOException e) {
                System.out.println("Connection error: " + e.getMessage());
                e.printStackTrace();
                isConnected = false;  // 连接失败时设置flag
            }
        });
    }

    // 发送好友码进行验证
    public void authenticateFriendCode() {
        executor.submit(() -> {
            try {
                // 发送好友码到服务器
                output.writeUTF(friendCode);
                System.out.println("Friend code sent: " + friendCode);

                // 等待服务器的验证结果
                String response = input.readUTF();
                if ("OK".equals(response)) {
                    System.out.println("Friend code verified. Chat started!");

                    // 连接成功后启动聊天窗口
                    startChat();
                } else {
                    System.out.println(response);
                    System.out.println("Friend code verification failed.");
                    closeConnection();
                }
            } catch (IOException e) {
                System.out.println("Error during friend code verification: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    // 启动聊天功能
    private void startChat() {
        // 在连接成功后启动聊天窗口
        chatWindow = new ChatWindow(this);
        chatWindow.setVisible(true);  // 显示聊天窗口
    }

    // 发送消息
    public void sendMessage(String message) {
        executor.submit(() -> {
            if (output != null) {
                try {
                    output.writeUTF(message);
                    System.out.println("Sent: " + message);
                } catch (IOException e) {
                    System.out.println("Failed to send message: " + e.getMessage());
                }
            } else {
                System.out.println("Output stream is not initialized.");
            }
        });
    }

    // 接收消息
    public String receiveMessage() throws IOException {
        return input.readUTF(); // 从输入流中读取消息
    }

    // 关闭连接
    public void closeConnection() {
        try {
            if (input != null) {
                input.close();
            }
            if (output != null) {
                output.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
                System.out.println("Connection closed successfully.");
            }
        } catch (IOException e) {
            System.out.println("Error while closing connection: " + e.getMessage());
        }
    }
}
