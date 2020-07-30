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
import java.net.Socket;
import java.net.UnknownHostException;

public class YodafyClienteTCP {

	public static void main(String[] args) throws IOException {
		
		byte []buferEnvio;
		byte []buferRecepcion=new byte[256];
		int bytesLeidos=0;
                
                String recibidos = new String();
		
		// Nombre del host donde se ejecuta el servidor:
		String host="localhost";
		// Puerto en el que espera el servidor:
		int port=8989;
		
		// Socket para la conexión TCP
		Socket socketServicio=null;
		
		
                    try {
                      socketServicio=new Socket (host,port);
                    } catch (UnknownHostException e) {
			System.err.println("Error: Nombre de host no encontrado.");
                    } catch (IOException e) {
			System.err.println("Error de entrada/salida al abrir el socket.");
                    }
                               
                                		
			
			InputStream inputStream = socketServicio.getInputStream();
			OutputStream outputStream = socketServicio.getOutputStream();
			
                        PrintWriter outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
                        //cadena a enviar
                        String cadenaEnvio = "Al monte del volcan debes ir sin demora";

                        // Enviamos el string por el outPrinter;
                        //////////////////////////////////////////////////////
                        outPrinter.println(cadenaEnvio); 

                        BufferedReader inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));
                        
                        recibidos = inReader.readLine();
			// Leemos la respuesta del servidor. Para ello le pasamos un array de bytes, que intentará
			// rellenar. El método "read(...)" devolverá el número de bytes leídos.
			//////////////////////////////////////////////////////
			// bytesLeidos ... .read... buferRecepcion ; (Completar)
			//////////////////////////////////////////////////////
			
			// MOstremos la cadena de caracteres recibidos:
			System.out.println("Recibido: " + recibidos);
			
			// Una vez terminado el servicio, cerramos el socket (automáticamente se cierran
			// el inpuStream  y el outputStream)
                        socketServicio.close();
			//////////////////////////////////////////////////////
			// ... close(); (Completar)
			//////////////////////////////////////////////////////
			
			// Excepciones:

	}
}
