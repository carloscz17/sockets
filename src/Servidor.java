import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8081); // server conectado na porta 8081
            System.out.println("Servidor aguardando conexão...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Cliente conectado!");

            InputStream inputStream = clientSocket.getInputStream(); // entrada
            OutputStream outputStream = clientSocket.getOutputStream(); //  saida
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            String fileName = dataInputStream.readUTF();
            long fileSize = dataInputStream.readLong();

            byte[] buffer = new byte[(int) fileSize]; // armazenamento

            dataInputStream.readFully(buffer, 0, buffer.length); // receber

            FileOutputStream fileOutputStream = new FileOutputStream(fileName); // salvar
            fileOutputStream.write(buffer, 0, buffer.length);
            fileOutputStream.close();

            String filePath = new File(fileName).getAbsolutePath(); // retorno do caminho do diretorio da iamgem
            dataOutputStream.writeUTF(filePath);

            clientSocket.close();
            serverSocket.close();
            System.out.println("Conexões fechadas. Imagem recebida e salva em: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}