//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class YodafyClienteTCP {

	public static void main(String[] args) throws IOException {
		
        int puerto = 8989;
        byte[] bufer = new byte[256];
        DatagramSocket socket = new DatagramSocket();
        InetAddress dir = null;
        DatagramPacket paquete;

        dir = InetAddress.getByName("localhost");
        
        //enviamos
        String cadenaEnvio = "Al monte del volcan debes ir sin demora";

        bufer = cadenaEnvio.getBytes();

        paquete = new DatagramPacket(bufer, bufer.length, dir, puerto);
        socket.send(paquete);

        paquete = new DatagramPacket(bufer, bufer.length);
        socket.receive(paquete);
        
        
        byte [] cadenarec = paquete.getData();
        
        // MOstremos la cadena de caracteres recibidos:
        System.out.println("Recibido: ");
        for (int i = 0; i < cadenarec.length; i++) {
            System.out.print((char) cadenarec[i]);
        }
                        socket.close();
                        
			//////////////////////////////////////////////////////
			// ... close(); (Completar)
			//////////////////////////////////////////////////////
			
			// Excepciones:

	}
}
