package Menu;
import Juego.Operaciones;
public class Menu {
    int numero=0;
    Utils util = new Utils();
    Operaciones op = new Operaciones();
    public void MenuJuego(){
        while(numero != 3){
            int eleccion=util.Menu();
            switch(eleccion){
                case 1:
                    submenu();
                    break;
                case 2:
                    op.controles();
                    break;
                case 3:
                    numero = 3;
                    break;
                default:
                    System.out.println("Numero no válido");
            }
        }
    }
    public void submenu(){
        while(numero != 3){
            int eleccion2 = util.subMenu();
            switch(eleccion2){

                case 1:
                    op.jugarvsJugador();
                    break;
                case 2:
                    op.jugar();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Numero no válido");
            }
        }
        }
}
