import gui.*;
import javax.swing.*;
import client.*;

public class APP {
    public static void main(String[] args) {
        String ip = "127.0.0.1";  // 服务器 IP 地址（改为你要远程连接的IP）
        int SERVER_PORT = 8888;  // 服务器端口（程序默认端口）
        String friendCode = "123456";  // 好友码（你要连接的好友码）

        // 创建 PeerClient 并连接
        PeerClient client = new PeerClient(ip, SERVER_PORT, friendCode);
        client.connect();  // 发起连接

        // 启动连接窗口
        SwingUtilities.invokeLater(() -> {
            FriendConnectWindow friendConnectWindow = new FriendConnectWindow();
            friendConnectWindow.setVisible(true);
        });
    }
}

