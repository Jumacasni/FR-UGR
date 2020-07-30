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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static jdk.nashorn.tools.ShellFunctions.input;

public class YodafyClienteTCP {

    String nombrejugador;
    Scanner input;
    boolean turno;
    Socket socketServicio;
    PrintWriter writer;
    BufferedReader reader;
    private boolean pfinalizada;
    
    ClientUI interfaz;
    
    public YodafyClienteTCP() throws IOException{
        input = new Scanner(System.in);
        socketServicio = new Socket("localhost", 8989);
        writer = null;
        reader = null;
        turno = false;
        interfaz = new ClientUI();
        interfaz.setVisible(true);
        interfaz.setUI(this);
        
        try {
            writer = new PrintWriter(socketServicio.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(YodafyClienteTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {

        try {
            
            YodafyClienteTCP cliente = new YodafyClienteTCP();
            cliente.introducirNombres(cliente.writer, cliente.reader);
                
            while(true){
                cliente.run();
            }

            // Excepciones:
        } catch (UnknownHostException e) {
            System.err.println("Error: Nombre de host no encontrado.");
        } catch (IOException e) {
            System.err.println("Error de entrada/salida al abrir el socket.");
        }
    }
    
    public void run() throws IOException{
        while(true){
            String mensaje = reader.readLine();
            
            if(mensaje.startsWith("WELCP1")){
                interfaz.ponTexto(mensaje.substring(7));
            }
            
            else if (mensaje.startsWith("WELCP2")){
                interfaz.ponTexto(mensaje.substring(7));
            }
            
            else if (mensaje.startsWith("TABLERO")){
               interfaz.SetTablero(mensaje.substring(8));
            }
            
            else if(mensaje.startsWith("ENTER_POSITION") || mensaje.startsWith("ENTER_FREE_POSITION")){
                if (mensaje.startsWith("ENTER_POSITION")){
                    interfaz.ponTexto(mensaje.substring(15));
                    turno = true;
                    interfaz.getTablero().setVisible(true);
                }
                else if (mensaje.startsWith("ENTER_FREE_POSITION"))
                    interfaz.ponTexto(mensaje.substring(20));
                
                interfaz.EligeCasilla();
            }
            
            else if(mensaje.startsWith("UWON")){
                interfaz.ponTexto2(mensaje.substring(5));
                turno = false;
                interfaz.getTablero().setVisible(false);
            }
            
            else if(mensaje.startsWith("DRAW")){
                if(turno){
                    interfaz.ponTexto2(mensaje.substring(5));
                    turno = false;
                }
                
                else{
                    interfaz.ponTexto(mensaje.substring(5));
                }
                
                interfaz.getTablero().setVisible(false);
            }
            
            else if(mensaje.startsWith("ULOSE")){
                interfaz.ponTexto(mensaje.substring(6));
                turno = false;
                interfaz.getTablero().setVisible(false);
                interfaz.getJlabel14().setVisible(false);
            }
            
            else if(mensaje.startsWith("SAVED_MOVEMENT")){
                interfaz.ponTexto(mensaje.substring(15));
                turno = false;
            }
            
            else if(mensaje.startsWith("MENU_INIT") || mensaje.startsWith("MENU_FINAL") ){    
                if (mensaje.startsWith("MENU_FINAL")){
                    interfaz.ponTexto(mensaje.substring(11));
                    interfaz.muestraMenu(true,true);
                }        
                
                else{
                    interfaz.ponTexto(mensaje.substring(10));
                    interfaz.muestraMenu(true,false);   
                }
            }
            
            else if (mensaje.startsWith("WAITING_ENEMY_ACCEPT")){
              interfaz.ponTexto(mensaje.substring(21));
            }
            
            else if (mensaje.startsWith("WAITING_ENEMY_MOVE")){
              interfaz.ponTexto(mensaje.substring(19));  
            }
            
            else if (mensaje.startsWith("THANKS4P")){
              interfaz.ponTexto(mensaje.substring(9));  
            }
            
            else if (mensaje.startsWith("GAMES_WON")){
              interfaz.ponTexto2("Partidas ganadas: " + mensaje.charAt(10));
            }
               
            else{
                interfaz.ponTexto(mensaje);
            }
        }
    }
    
    public void EmpezarPartida(){
        writer.println("ACCEPT_GAME");
    }
    
    public void Salir(){
        writer.println("EXIT_GAME");
    }
    
    public void VerPuntuacion(){
        writer.println("CHECK_RESULTS");
    }
    
    public void Manda(String mensaje){
        writer.println(mensaje);
    }
    
    public void introducirNombres(PrintWriter output, BufferedReader reader) throws IOException{
        String mensaje = reader.readLine();
        if (mensaje.startsWith("INSERT_NAME")){
            interfaz.MuestraMeterNombre(true);
            while (interfaz.entered == false){
                        System.out.println("");
            }
            nombrejugador = interfaz.nombre;
            output.println("NAME_EQUALS=" + nombrejugador);
            interfaz.MuestraMeterNombre(false);
        }
        else 
            System.out.println("Error interno del servidor: no se ha pedido el nombre");
    }

}