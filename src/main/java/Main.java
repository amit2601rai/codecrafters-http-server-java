import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

  public static void main(String[] args) {
    System.out.println("Logs from your program will appear here!");
    final ServerSocket serverSocket;
    Socket clientSocket;
    try {
      serverSocket = new ServerSocket(4221);
      serverSocket.setReuseAddress(true);
      do {
        clientSocket = serverSocket.accept();
        clientSocket.getOutputStream().write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
      } while (true);
    } catch (final IOException e) {
      System.out.println("IOException: " + e.getMessage());
    }
  }
}