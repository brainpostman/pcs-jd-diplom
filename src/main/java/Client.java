import java.io.*;
import java.net.Socket;
import java.nio.Buffer;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 8989;
        try (Socket clientSocket = new Socket(host, port);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedWriter writer = new BufferedWriter(new FileWriter("searchResult.json"));
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {
            System.out.println(in.readLine());
            String word = scanner.nextLine();
            out.println(word.trim());
            int input;
            StringBuilder sb = new StringBuilder();
            while ((input = in.read()) != -1) {
                sb.append((char) input);
            }
            writer.write(sb.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
