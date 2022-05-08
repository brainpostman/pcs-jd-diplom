import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        int port = 8989;
        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
        SimpleModule module = new SimpleModule();
        module.addSerializer(new PageEntrySerializer(PageEntry.class));
        ObjectMapper mapper = new ObjectMapper();
        try (ServerSocket serverSocket = new ServerSocket(port);
             Socket clientSocket = serverSocket.accept();
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             FileWriter writer = new FileWriter("searchResult.json");
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            out.println("Введите слово:");
            String word = in.readLine();
            List<PageEntry> pageEntries = engine.search(word.trim());
            String searchResult = mapper.registerModule(module)
                    .writer(new DefaultPrettyPrinter())
                    .writeValueAsString(pageEntries);
            writer.write(searchResult);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}