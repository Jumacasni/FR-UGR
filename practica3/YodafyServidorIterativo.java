import fraplicacion.ProcesadorYodafy;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
//import ProcesadorYodafy.java;

//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
public class YodafyServidorIterativo {

	public static void main(String[] args) {
	
                // Socket
                
                ServerSocket socketServidor = null;
		// Puerto de escucha
		int port=8989;
		// array de bytes auxiliar para recibir o enviar datos.
		byte []buffer=new byte[256];
                Socket socketConexion = null;
		// Número de bytes leídos
		int bytesLeidos=0;
		
			try {
                        socketServidor = new ServerSocket(port);  
                        } catch (IOException e) 
                            { System.out.println("Error: no se pudo atender en el puerto "+port);}
			
			// Mientras ... siempre!
                        Hebrita heb = null;
			do {
                            
                                try {
                                socketConexion = socketServidor.accept();
                                } catch (IOException e) {
                                System.out.println("Error: no se pudo aceptar la conexi�n solicitada");
                                }
				
                               
				// Creamos un objeto de la clase ProcesadorYodafy, pasándole como 
				// argumento el nuevo socket, para que realice el procesamiento
				// Este esquema permite que se puedan usar hebras más fácilmente.
				ProcesadorYodafy procesador=new ProcesadorYodafy(socketConexion);
                                heb = new Hebrita(procesador);
                                heb.start(); //lanzamos una hebra por cada conexión nueva
                                
				
			} while (true);
	
	}

}
