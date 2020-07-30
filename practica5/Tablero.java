/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author jumacasni
 */
public class Tablero {
    
    private boolean partida_finalizada;
    private int turno;
    private int ganador;
    private int[][] matriz;
    private int filas;
    private int columnas;
    private Jugador currentPlayer;
    
    
    public Tablero(){
	partida_finalizada = false;
	turno = 1;
	ganador = 0;
        filas = 3;
        columnas = 3;
        
        matriz = new int[filas][columnas];
	for (int i=0; i<filas; ++i)
            for(int j=0; j<columnas; ++j)
                matriz[i][j] = 0;
    }
    
    public void setCurrentPlayer(Jugador currentPlayer){
        this.currentPlayer = currentPlayer;
    }
    
    public Jugador currentPlayer(){
        return currentPlayer;
    }
    
    public int Posicion(int f, int c, Jugador jugador){
            if ((f<0 || f>=filas || c<0 || c>=columnas) || jugador != currentPlayer)
                    return 0;
            
            return matriz[f][c];
    }

    public boolean Pfinalizada(){
            return partida_finalizada;
    }

    public int Turno(){
            return turno;
    }

    public int Ganador(){
            if(partida_finalizada)
                    return ganador;
            return 0;
    }
    
    public int Columnas(){
        return columnas;
    }
    
    public int Filas(){
        return filas;
    }

    int Insertar(int numero, Jugador jugador) {
            
            int fila = numero/filas;
            int columna = numero%columnas;
            
            if(numero < 0 || numero > 8){
                return -2;
            }
            
            if(matriz[fila][columna] == 0)
                matriz[fila][columna] = turno;
            
            else
                return -3;

            if(ComprobacionGanador(turno)){
                ganador = turno;
                partida_finalizada = true;
                return 1;
            }
            
            // Comprobacion de empate:

            if(TableroLleno()){
                ganador = 0;
                partida_finalizada = true;
                return 2;
            }

            // Cambio de turno:

            if(turno == 1){
                turno = 2;
                currentPlayer = jugador.getEnemy();
            }
            
            else{
                turno = 1;
                currentPlayer = jugador.getEnemy();
            }

            return 0;
    }

    public boolean ComprobacionGanador(int turno){
        if((matriz[0][0]==turno) && (matriz[0][1]==turno) && (matriz[0][2]==turno)){
            return true;
        }
        if((matriz[1][0]==turno) && (matriz[1][1]==turno) && (matriz[1][2]==turno)){
            return true;
        }
        if((matriz[2][0]==turno) && (matriz[2][1]==turno) && (matriz[2][2]==turno)){
            return true;
        }
        if((matriz[0][0]==turno) && (matriz[1][0]==turno) && (matriz[2][0]==turno)){
            return true;
        }
        if((matriz[0][1]==turno) && (matriz[1][1]==turno) && (matriz[2][1]==turno)){
            return true;
        }
        if((matriz[0][2]==turno) && (matriz[1][2]==turno) && (matriz[2][2]==turno)){
            return true;
        }
        if((matriz[0][0]==turno) && (matriz[1][1]==turno) && (matriz[2][2]==turno)){
            return true;
        }
        if((matriz[0][2]==turno) && (matriz[1][1]==turno) && (matriz[2][0]==turno)){
            return true;
        }
        return false;
    }
    
    public boolean TableroLleno(){
        for(int i=0; i<filas; i++)
            for(int j=0; j<columnas; j++){
                if(matriz[i][j] == 0)
                    return false;
            }
        
        return true;
    }
    
    public void Reinicia(int t) {

            for(int i=0; i<filas; ++i)
                    for(int j=0; j<columnas; ++j)
                            matriz[i][j] = 0;

            partida_finalizada = false;
            turno = t;
            ganador = 0;
    }

    public String ImprimeTablero(){
        String tablero = "";
        ;
        for(int i=0; i<filas;i++){
            for(int j=0; j<columnas;j++){
               
                if(matriz[i][j]==0)
                    tablero += "0";                    
                    
                else if (matriz[i][j]==1)
                     tablero += "1";
                    
                else
                    tablero += "2";
                    
            }
        }
        return tablero;
    }
}
