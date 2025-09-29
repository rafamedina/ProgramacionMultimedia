package Menu;

import java.util.Scanner;

public class Utils {
    static Scanner scanner = new Scanner(System.in);

    public int eleccion(){
        int eleccion;
        while(true){
            try{

                System.out.println("Que eleccion escoges: ");
                eleccion = scanner.nextInt();
                if(eleccion<4 && eleccion>0){
                    return eleccion;
                } else{
                    System.out.println("No es una opcion valida");
            }
        } catch (Exception e){
                System.out.println("Error: "+e.getMessage());
            }
        }

    }



    public int Menu(){
        System.out.println("1. Empezar la partida");
        System.out.println("2. Controles");
        System.out.println("3. Salir");
        return eleccion();
    }

    public int subMenu(){
        System.out.println("1. Jugador vs Jugador");
        System.out.println("2. Jugador vs IA");
        System.out.println("3. Volver");
        return eleccion();
    }

}
