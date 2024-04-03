import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        try {

            Socket socket = new Socket("localhost", 8081); // conexão na porta 8081

            InputStream inputStream = socket.getInputStream(); // comunicação de entrada
            OutputStream outputStream = socket.getOutputStream(); // comunicação de saida
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            String fileName = "imagem.jpg"; // enviar
            File file = new File(fileName);
            long fileSize = file.length();
            dataOutputStream.writeUTF(fileName);
            dataOutputStream.writeLong(fileSize);

            FileInputStream fileInputStream = new FileInputStream(file); // enviar
            byte[] buffer = new byte[(int) fileSize];
            fileInputStream.read(buffer, 0, buffer.length);
            dataOutputStream.write(buffer, 0, buffer.length);
            fileInputStream.close();

            String filePath = dataInputStream.readUTF(); // caminho
            System.out.println("Imagem recebida e salva em: " + filePath);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
