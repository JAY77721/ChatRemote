package server;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class PeerServer {
    private int port;
    private static final String LOCAL_FRIEND_CODE = "123456";  // 本地设置的好友码（可以自定义）
    private ExecutorService executorService;  // 线程池，用于处理客户端连接

    public PeerServer(int port) {
        this.port = port;
        // 初始化线程池，最大线程数为 10，线程空闲超过 60秒则被回收
        this.executorService = Executors.newFixedThreadPool(10);
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is running and waiting for clients to connect...");
            while (true) {
                Socket socket = serverSocket.accept();  // 等待客户端连接
                System.out.println("New client connected");

                // 为每个客户端创建一个 ClientHandler，并提交给线程池处理
                ClientHandler clientHandler = new ClientHandler(socket);
                executorService.submit(clientHandler);  // 提交任务到线程池
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PeerServer server = new PeerServer(8888);  // 监听端口
        server.start();
    }
}
