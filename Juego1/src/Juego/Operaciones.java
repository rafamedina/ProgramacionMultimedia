package Juego;

import java.util.InputMismatchException;
import java.util.Scanner;
public class Operaciones {
    static Scanner scanner = new Scanner(System.in);
    public void jugar(){
        int x = 21;
        int num;
        int numIA;
        System.out.println("Intenta no ser el último en tachar un palito!!");
        while (x > 0) {
            imprimir(x);
            num = pedirNumero(x);
            x -= num;
            imprimir(x);
            if (x == 0) {
                System.out.println("\n¡Perdiste!");
                break;
            }
            numIA = pedirIA(num);
            System.out.println("\nLa IA tachó " + numIA + " palos.");
            x -= numIA;
        }
    }

    public int pedirNumero(int max) {
        while (true) {
            try {
                System.out.print("\n¿Cuántos palos tachas? (1-4): ");
                int num = scanner.nextInt();
                if (num >= 1 && num <= 4 && num <= max) {
                    return num;
                }
                System.out.println("Número fuera de rango. Intenta de nuevo.");
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Escribe un número.");
                scanner.nextLine();
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

    public int pedirIA(int num) {
        return 5 - num;
    }
}

