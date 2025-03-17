package server;

import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private static final String LOCAL_FRIEND_CODE = "123456";  // 本地设置的好友码

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

            // 验证好友码
            if (!authenticateFriendCode(input, output)) {
                return;  // 如果验证失败，结束线程
            }

            // 如果验证成功，继续接收和转发消息
            String message;
            while ((message = input.readUTF()) != null) {
                System.out.println("Received: " + message);

                //                                        //
                // 可以添加更多处理逻辑，如广播、保存聊天记录等   //
                //                                        //
                output.writeUTF("Message received: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();  // 确保关闭连接
        }
    }

    // 验证好友码
    private boolean authenticateFriendCode(DataInputStream input, DataOutputStream output) throws IOException {
            //接收客户端输入的好友码
        String enteredFriendCode = input.readUTF();
        System.out.println("Entered friend code: " + enteredFriendCode);

            //验证好友码是否与本地设置的好友码一致
        if (LOCAL_FRIEND_CODE.equals(enteredFriendCode)) {
            output.writeUTF("OK");
            System.out.println("You have successfully logged in!");
            return true;  // 验证成功，允许连接
        } else {
            output.writeUTF("Invalid friend code. Connection closed.");
            System.out.println("Friend code invalid.");
            return false;  // 验证失败，拒绝连接
        }
    }

    // 关闭连接
    private void close() {
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
            e.printStackTrace();
        }
    }
}
