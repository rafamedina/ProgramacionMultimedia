package Menu;
import Juego.Operaciones;
public class Menu {



    int numero=0;
    Utils util = new Utils();
    Operaciones op = new Operaciones();
    public void Menu(){
        while(numero != 3){
            int eleccion=util.Menu();
            switch(eleccion){
                case 1:







            }










        }
    }

    public void submenu(){
        while(numero != 3){
            int eleccion2 = util.subMenu();
            switch(eleccion2){

                case 1:
                    op.jugarvsJugador();
                case 2:
                    op.jugar();

            }
        }

        }



}
