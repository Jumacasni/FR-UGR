/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jumacasni
 */
public class Jugador {
    
    private String nombre;
    private Jugador enemigo;
    private int njugador;
    private int partidasganadas;
    private Socket socket;
    private boolean aceptapartida;
    private boolean ganador;
    private BufferedReader reader;
    private PrintWriter writer;
        
    public Jugador(Socket socket, int turno) throws IOException{
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
        
        nombre = cogerNombre(socket, reader, writer);
        aceptapartida = false;
        njugador = turno;
        partidasganadas = 0;
        ganador = false;
        this.socket = socket;
        
        if(turno == 1)
            writer.println("WELCP1 Bienvenido al servidor Tres en Raya, " + nombre + ". Esperando oponente..."); //WELCP1 para recibir al primer jugador
        
        else
            writer.println("WELCP2 Bienvenido al servidor Tres en Raya, " + nombre + ". Es el turno del oponente. Esperando a que el oponente acepte la partida."); //WELCP2 recibe al segundo jugador
    }
    
    public void setEnemy(Jugador enemigo){
        this.enemigo = enemigo;
    }
    
    public Jugador getEnemy(){
        return enemigo;
    }
    
    public String cogerNombre(Socket socketServicio, BufferedReader reader, PrintWriter writer){

        // Como máximo leeremos un bloque de 1024 bytes. Esto se puede modificar.
        String datosRecibidos;

        // Array de bytes para enviar la respuesta. Podemos reservar memoria cuando vayamos a enviarka:
        String datosEnviar;

        String nombrejugador = "";
        try {
            
            //Codigo Insert_NAME (Introducir NOMBRE DEL JUGADOR)
            writer.println("INSERT_NAME");

            // Recibe el nombre
            nombrejugador = reader.readLine();
            String[] divide=nombrejugador.split("=");
            nombrejugador = divide[1];

        } catch (IOException e) {
                System.err.println("Error al obtener los flujos de entrada/salida.");
        }

        return nombrejugador;
    }
    
    public void menuInicial() throws IOException{
        
        writer.println("MENU_INIT Menu: ");

        String seleccion = reader.readLine();
        seleccion = seleccion.toUpperCase();
        
        if(seleccion.equals("ACCEPT_GAME")){
            if(njugador == 1){
                writer.println("WAITING_ENEMY_ACCEPT Esperando a que el oponente acepte la partida...");  
            }
            
            else{
                writer.println("WAITING_ENEMY_MOVE Esperando a que el oponente realice el primer movimiento...");
            }
            
            aceptapartida = true;
        }
        
        else if(seleccion.equals("EXIT_GAME")){
            writer.println("THANKS4P");
            
            socket.close();
        }
    }
    
    public void menuFinal() throws IOException{
        writer.println("MENU_FINAL Menu: ");
        
        String seleccion = reader.readLine();
        seleccion = seleccion.toUpperCase();
        
        if(seleccion.equals("ACCEPT_GAME")){
            
            if(!ganador && !enemigo.ganador){
                if(njugador == 1){
                    writer.println("WAITING_ENEMY_ACCEPT Esperando a que el oponente acepte la partida...");  
                }

                else{
                    writer.println("WAITING_ENEMY_MOVE Esperando a que el oponente realice el primer movimiento...");
                }
            }
            
            else if(ganador){
                writer.println("WAITING_ENEMY_ACCEPT Esperando a que el oponente acepte la partida...");        
            }
            
            else{
                writer.println("WAITING_ENEMY_MOVE Esperando a que el oponente realice el primer movimiento...");
            }
            
            aceptapartida = true;
        }
        
        else if(seleccion.equals("CHECK_RESULTS")){
            writer.println("GAMES_WON " + Integer.toString(partidasganadas));
        }
        
        else if(seleccion.equals("EXIT_GAME")){
            writer.println("THANKS4P ¡Gracias por jugar!");
            
            socket.close();
        }
    }
    
    public void run(Tablero t) throws IOException{
        writer.println("Es tu turno");
            
        writer.println("TABLERO " + t.ImprimeTablero());
        
        writer.println("ENTER_POSITION Elige una casilla: ");
        while(t.currentPlayer() == this){
            String seleccion = reader.readLine();
            
            seleccion = String.valueOf(seleccion.charAt(3));

            int numero = Integer.parseInt(seleccion);
           
            int resultado = t.Insertar(numero, this);

            if(resultado == -2){
                writer.println("ENTER_VALID_NUMBER");
            }

            else if(resultado == -3){
                writer.println("ENTER_FREE_POSITION Elige una casilla no ocupada: ");
            }

            else if(resultado == 1){
                writer.println("UWON ¡Has ganado!");
                writer.println(t.ImprimeTablero());
                partidasganadas++;
                ganador = true;
                enemigo.ganador = false;
                break;
            }

            else if(resultado == 2){
                writer.println("DRAW ¡Has empatado!");
                writer.println(t.ImprimeTablero());
                ganador = false;
                enemigo.ganador = false;
                break;
            }

            else{
                writer.println("SAVED_MOVEMENT Tu movimiento ha sido guardado, es el turno del oponente");
            }
        }
    }

    public void hasPerdido(){
        writer.println("ULOSE ¡Has perdido! Esperando a que el oponente acepte una nueva partida...");
    }
    
    public void hasEmpatado(){
        writer.println("DRAW ¡Has empatado! Esperando a que el oponente acepte una nueva partida...");
    }
    
    public String Nombre(){
            return nombre;
    }
    
    public void setAceptaPartida(boolean p){
        aceptapartida = p;
    }
    
    public boolean getAceptaPartida(){
        return aceptapartida;
    }
    
    public int getNJugador(){
        return njugador;
    }
}
