import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class TCPSocketServer {
    public static void main(String[] args) {
        run();
    }

    public static void run() {
        // socket指定端口号，0-65535，不能与其他进程端口号冲突，否则启动会端口占用的错误
        int serverPort = 4700;
        // 创建服务端socket
        ServerSocket serverSocket;
        int receiveMsgSize;
        // 接收字节缓冲数组
        byte[] receiveMsgBuffer = new byte[32];
        try {
            serverSocket = new ServerSocket(serverPort);
            // 处理完一个客户端请求之后再次进入等待状态
            while (true) {
                // 程序执行到这之后，将一直等待客户端连接，直到有客户端接入，代码才继续执行
                System.out.println("服务端已启动，绑定端口" + serverPort);
                Socket clientSocket = serverSocket.accept();
                SocketAddress clientAddress = clientSocket
                        .getRemoteSocketAddress();
                System.out.println("收到客户端连接，ip：" + clientAddress);
                InputStream in = clientSocket.getInputStream();
                OutputStream out = clientSocket.getOutputStream();
                // 接收客户端发来的数据，并原样返回给客户端
                while ((receiveMsgSize = in.read(receiveMsgBuffer)) != -1) {
                    String receivedData = new String(receiveMsgBuffer);
                    System.out.println(receivedData);
                    receivedData = receivedData.toUpperCase();
                    out.write(receivedData.getBytes(), 0, receiveMsgSize);
                }
                // 释放socket资源
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
