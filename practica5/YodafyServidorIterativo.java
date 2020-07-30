import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
public class YodafyServidorIterativo {

    public static void main(String[] args) throws IOException {

        // Puerto de escucha
        int port = 8989;
        // array de bytes auxiliar para recibir o enviar datos.
        byte[] buffer = new byte[256];
        // Número de bytes leídos
        int bytesLeidos = 0;
        int numClientes = 0;
        //Tablero tablero = new Tablero();

        // Abrimos el socket en modo pasivo, escuchando el en puerto indicado por "port"
        //////////////////////////////////////////////////
        ServerSocket socketPuerto = null;
        System.out.println("Se ha iniciado el servidor Tres en Raya");
        
        try {
            socketPuerto = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Error: no se pudo atender en el puerto " + port);
        }
        //////////////////////////////////////////////////

        // Mientras ... siempre!
        do {

            // Aceptamos una nueva conexión con accept()
            /////////////////////////////////////////////////
            Socket socketServicio = null;
            try {
                Tablero tablero = new Tablero();
                boolean primerapartida = true;
                // Conexión del primer jugador
                socketServicio = socketPuerto.accept();
                numClientes++;
                Jugador jugador1 = new Jugador(socketServicio, 1);
                
                // Conexión del segundo conexión
                socketServicio = socketPuerto.accept();
                numClientes++;
                Jugador jugador2 = new Jugador(socketServicio, 2);

                // Ya hay 2 jugadores, empieza la partida
                jugador1.setEnemy(jugador2);
                jugador2.setEnemy(jugador1);
                tablero.setCurrentPlayer(jugador1);
                System.out.println("Partida en curso...");
                
                while(true){
                    
                    if(primerapartida){
                        while(!jugador1.getAceptaPartida())
                            jugador1.menuInicial();

                        while(!jugador2.getAceptaPartida())
                            jugador2.menuInicial();
                        
                        primerapartida = false;
                    }
                    
                    // Empieza
                    if(tablero.Turno() == 1){
                        while(!tablero.Pfinalizada()){
                            System.out.println("Turno del jugador 1");
                            jugador1.run(tablero);
                            // Hay un ganador
                            if(tablero.Pfinalizada() && tablero.Ganador() != 0){
                                jugador2.hasPerdido();
                                break;
                            }
                            
                            // Empate
                            else if(tablero.Pfinalizada() && tablero.Ganador() == 0){
                                jugador2.hasEmpatado();
                                break;
                            }

                            System.out.println("Turno del jugador 2");
                            jugador2.run(tablero);
                        }

                        // Significa que ha ganado el jugador 2
                        if(tablero.Turno() == 2){
                            jugador1.hasPerdido();
                        }
                        
                        else{
                            jugador1.hasEmpatado();
                        }
                    }
                    
                    // Empieza el jugador 2
                    else{
                        while(!tablero.Pfinalizada()){
                            System.out.println("Turno del jugador 2");
                            jugador2.run(tablero);
                            // Si termina la partida y hay un ganador
                            if(tablero.Pfinalizada() && tablero.Ganador() != 0){
                                jugador1.hasPerdido();
                                break;
                            }
                            
                            // Si termina la partida en empate
                            else if(tablero.Pfinalizada() && tablero.Ganador() == 0){
                                jugador1.hasEmpatado();
                                break;
                            }
                            
                            System.out.println("Turno del jugador 1");
                            jugador1.run(tablero);
                        }

                        // Significa que ha ganado el jugador 1
                        if(tablero.Turno() == 1){
                            jugador2.hasPerdido();
                        }
                        
                        // Empate
                        else{
                            jugador2.hasEmpatado();
                        }
                    }
                    
                    jugador1.setAceptaPartida(false);
                    jugador2.setAceptaPartida(false);
                    
                    tablero.Reinicia(tablero.Turno());
                    
                    // Si ha ganado el jugador 1, el menu se pone primero a él
                    if(tablero.Turno() == 1){
                        while(!jugador1.getAceptaPartida())
                            jugador1.menuFinal();

                        while(!jugador2.getAceptaPartida())
                            jugador2.menuFinal();
                    }

                    // Si ha ganado el jugador 2, el menu se pone primero a él
                    else{
                        while(!jugador2.getAceptaPartida())
                            jugador2.menuFinal();

                        while(!jugador1.getAceptaPartida())
                            jugador1.menuFinal();
                    }
                }
                
            } catch (IOException e) {
                System.out.println("Error: no se pudo aceptar la conexión solicitada");
            }
            //////////////////////////////////////////////////
            // Creamos un objeto de la clase ProcesadorYodafy, pasándole como 
            // argumento el nuevo socket, para que realice el procesamiento
            // Este esquema permite que se puedan usar hebras más fácilmente.

        } while (true);
    }

}