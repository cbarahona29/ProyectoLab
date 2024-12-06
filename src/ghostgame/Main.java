package ghostgame;

import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.mostrarMenuPrincipal();
    }

    public void mostrarMenuPrincipal() {
        GhostGame juego = new GhostGame();
        String menu;
        
        while (true) {
            menu = JOptionPane.showInputDialog("Menu de Inicio\n"
                    + "1. Login\n"
                    + "2. Crear Jugador\n"
                    + "3. Salir\n");

            switch (menu) {
                case "1": 
                    juego.login();
                    break;
                case "2":
                    juego.crearjugador();
                    break;
                case "3":
                    JOptionPane.showMessageDialog(null, "Saliendo");
                    System.exit(0);
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Opcion invalida", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}



