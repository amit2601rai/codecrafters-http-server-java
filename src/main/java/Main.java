import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

  public static void main(String[] args) {
    System.out.println("Logs from your program will appear here!");
    try (final ServerSocket serverSocket = new ServerSocket(4221)) {
      serverSocket.setReuseAddress(true);
      while (true) {
        try (Socket socket = serverSocket.accept()) {
          BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
          String requestLine = in.readLine();
          if (requestLine != null && !requestLine.isEmpty()) {
            final String[] requestParts = requestLine.split(" ");
            if (requestParts.length >= 2) {
              final String method = requestParts[0];
              final String url = requestParts[1];
              System.out.println("HTTP Method: " + method);
              System.out.println("URL: " + url);
              final String prefix = "/echo/";
              if(url.startsWith(prefix)) {
                final String inputString = url.substring(prefix.length());
                System.out.println("inputString: " + inputString);
                final String response = String.format("HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nContent-Length: %d\r\n\r\n%s",inputString.length(), inputString );
                System.out.println("response: " + response);
                socket.getOutputStream().write(response.getBytes());
                continue;
              }
              if (url.equals("/")) {
                final String response = "HTTP/1.1 200 OK\r\n\r\n";
                socket.getOutputStream().write(response.getBytes());
                continue;
              }
            }
          }
          socket.getOutputStream().write("HTTP/1.1 404 Not Found\r\n\r\n".getBytes());
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}