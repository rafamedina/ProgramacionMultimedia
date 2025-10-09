package Juego;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
public class Operaciones {
    static Scanner scanner = new Scanner(System.in);
    public void jugar() {
        int x = 21;
        int num = 0;
        String volver = "";
        int numIA;
        System.out.println("Intenta no ser el último en tachar un palito!!");
        while (x > 0) {
            imprimir(x);
            volver = pedirNumero(x);
            if (volver.equalsIgnoreCase("r")) {
                System.out.println("Regresando al menú...");
                break;
            }
            num = Integer.parseInt(volver);
            x -= num;
            imprimir(x);
            if (x == 0) {
                System.out.println("\n¡Perdiste!");
                break;
            }
            numIA = pedirIA(x);
            System.out.println("\nLa IA tachó " + numIA + " palos.");
            x -= numIA;
            if (x == 0) {
                System.out.println("\n¡Ganaste!");
                break;
            }
        }
    }

    public void jugarvsJugador() {
        int x = 21;
        int num;
        String volver;
        boolean turno = true;
        System.out.println("Intenta no ser el último en tachar un palito!!");
        while (x > 0) {
            imprimir(x);
            System.out.println("Es el turno del jugador: " + (turno ? "1" : "2"));
            volver = pedirNumero(x);
            if (volver.equalsIgnoreCase("r")) {
                System.out.println("Regresando al menú...");
                break;
            }
            num = Integer.parseInt(volver);
            x -= num;
            turno = !turno;
            if (x == 0) {
                if (turno) {
                    System.out.println("Ha perdido el jugador 1\n");
                } else {
                    System.out.println("Ha perdido el jugador 2\n");
                }
            }
        }
    }




    public String pedirNumero(int max) {
        while (true) {
            try {
                System.out.print("\n¿Cuántos palos tachas? (1-4): ");
                String num = scanner.nextLine();
                if (num.equalsIgnoreCase("r")) {
                    return num;
                }
                int numero = Integer.parseInt(num);
                if (numero >= 1 && numero <= 4 && numero <= max) {
                    return String.valueOf(numero);
                }
                System.out.println("Número fuera de rango. Intenta de nuevo.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Escribe un número.");
            }
        }
    }

    public void imprimir(int x) {
        System.out.print("Palos restantes (" + x + "): ");
        for (int i = 0; i < x; i++) {
            System.out.print("|");
        }
        System.out.println();
    }

    public int pedirIA(int x) {
        while(true){
            if (x > 4) {
                Random rand = new Random();
                return rand.nextInt(4) + 1;
            } else {
                switch (x) {
                    case 1, 2:
                        return 1;

                    case 3:
                        return 2;

                    case 4:
                        return 3;

                }
        }
        }
    }


    public void controles(){
        System.out.println("Si le das a jugar tienes en la primera opcion jugar jugador vs jugador, en la segunda vs la IA y si aprietas el 3 volveras atras en los menus, siempre que pulses la R dentro de un juego lo reinicias");
    }
}

