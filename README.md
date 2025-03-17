
---

# ChatRemote（基于java的即时通信软件）

**ChatRemote** 是一个简单的即时通信软件，允许用户通过 IP 地址和好友码进行连接，实现基本的文本聊天功能。该软件支持两台计算机之间的聊天。

## ✈✈项目概述

这个项目是一个基于 Java 的PeerToPeer（客户端-服务器）聊天系统，包含以下功能：
- **客户端**：用户可以通过 IP 地址和好友码连接到另一台计算机进行聊天。
- **服务器**：服务器接受来自客户端的连接请求，验证好友码并处理消息传输。
- **消息传输**：通过 TCP 连接实现消息的双向传输，客户端发送的消息会显示在聊天窗口中，同时接收到的消息也会实时显示。
- **注意事项**：默认在内网中使用。本项目前后端不分离。
## 🛩️项目结构

```
ChatRemote/
│
├─ .idea/                 # IntelliJ IDEA 配置文件
├─ src/                   # 源代码
│   ├─ client/            # 客户端相关类
│   │   ├─ PeerClient.java
│   ├─ server/            # 服务器相关类
│   │   ├─ PeerServer.java #主应用程序入口（1）
│   │   ├─ ClientHandler.java
│   ├─ gui/               # 图形用户界面类
│   │   ├─ ChatWindow.java
│   │   ├─ FriendConnectWindow.java
│   ├─ db/                # 数据库管理类
│   ├─ App.java           # 主应用程序入口（2）
├─ pom.xml                # Maven 配置文件
├─ README.md              # 项目说明文件
```

## 🚀安装与使用

### 1. 🛸克隆项目

首先，克隆这个仓库到你的本地机器：

```bash
git clone git@github.com:JAY77721/ChatRemote.git
```

### 2. 🛸安装依赖

项目使用 Maven 进行构建管理，确保你已经安装了 Maven。然后运行以下命令以安装项目依赖：

```bash
mvn install
```

### 3. 🛸配置 MySQL 数据库

- 在 MySQL 数据库中创建一个数据库，用于保存聊天记录。
- 修改数据库连接配置，确保 `DatabaseManager.java` 中的数据库连接参数正确。

### 4. 🛸启动服务器

在 `Server` 类的 `PeerServer.java` 方法启动服务器;

```bash
java -cp target/ChatRemote-1.0-SNAPSHOT.jar server.PeerServer.java
```

服务器会在 8888 端口上监听来自客户端的连接。

### 5. 🛸启动客户端

在 `App.java` 类中启动客户端应用。

```bash
java -cp target/ChatRemote-1.0-SNAPSHOT.jar App.java
```

客户端会提示用户输入好友的 IP 地址和好友码，一旦验证成功，就会打开聊天窗口。

### 6. 🛸聊天

一旦连接建立，用户可以通过输入框发送消息，接收来自好友的消息，聊天记录会实时显示在聊天窗口中。

## 🏆主要功能

- **连接功能**：用户通过输入 IP 地址和好友码连接到另一台计算机。
- **聊天功能**：用户可以发送和接收文本消息。
- **好友码验证**：服务器通过验证好友码来确保连接的有效性。

## 🥇依赖

- **Java 8+**：需要 Java 8 或更高版本运行。
- **MySQL**：可能需要一个 MySQL 数据库实例用于保存聊天记录。

## 🥈文件说明

- `PeerServer.java`：服务器端代码，负责监听客户端的连接请求并处理消息。
- `ClientHandler.java`：处理每个连接的客户端，验证好友码并进行消息的接收和转发。
- `PeerClient.java`：客户端代码，负责与服务器建立连接并发送/接收消息。
- `FriendConnectWindow.java`：客户端 GUI 窗口，用于输入 IP 地址和好友码进行连接。
- `ChatWindow.java`：聊天窗口，显示消息内容并允许用户发送消息。
- `DatabaseManager.java`：数据库管理类，负责保存和读取聊天记录。

## 🥉TODO

- 增加消息加密功能，确保通信的安全性。
- 优化用户界面，使其更友好。
- 支持多用户聊天，允许一个服务器同时处理多个客户端连接。

## 🌎联系

如果你有任何问题或建议，可以通过以下方式与我联系：

- GitHub Issues：[https://github.com/JAY77721/ChatRemote/issues](https://github.com/JAY77721/ChatRemote/issues)
- 邮箱：xu2835024653@gmail.com

---
