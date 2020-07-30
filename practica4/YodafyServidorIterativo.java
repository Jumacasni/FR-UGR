import fraplicacion.ProcesadorYodafy;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
//import ProcesadorYodafy.java;


public class YodafyServidorIterativo {

	public static void main(String[] args) throws SocketException {
	
		// Puerto de escucha
		int puerto=8989;
		byte []buffer=new byte[256];
		

                DatagramSocket socketServidor=new DatagramSocket(puerto);
            
                do {
                    
                    //hay que cambiar el tipo de socket
                    ProcesadorYodafy procesador=new ProcesadorYodafy(socketServidor);
                    procesador.procesa();
                    
                } while (true);
	
	}

}
