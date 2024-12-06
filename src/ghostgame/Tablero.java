package ghostgame;

import javax.swing.JOptionPane;

    //Cesar Garciaa//

public class Tablero {
    char[][] tablero;
    char[][] tiposReales;
    char[][] celdas; 

    public Tablero(int x, int y) {
        tablero = new char[x][y];
        tiposReales = new char[x][y];
        inicializarTablero();
    }

    public void inicializarTablero() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tablero[i][j] = '-';
                tiposReales[i][j] = '-';
            }
        }
    }

    public boolean colocarFantasma(int x, int y, char tipoReal) {
        if (valido(x, y) && tablero[x][y] == '-') {
            tablero[x][y] = 'F';
            tiposReales[x][y] = tipoReal;
            return true;
        }
        return false;
    }

    public char getTipoReal(int x, int y) {
        if (valido(x, y)) {
            return tiposReales[x][y];
        }
        return '-';
    }

    public void removerFantasma(int x, int y) {
        if (valido(x, y)) {
            tablero[x][y] = '-';
            tiposReales[x][y] = '-';
        }
    }

    public boolean valido(int fila, int columna) {
        return fila >= 0 && fila < tablero.length && columna >= 0 && columna < tablero[0].length;
    }

    public void mostrarTablero() {
        JOptionPane.showMessageDialog(null, TableroText(), "Tablero de Juego", JOptionPane.INFORMATION_MESSAGE);
    }

    public String TableroText() {
        StringBuilder tableroTexto = new StringBuilder();

        tableroTexto.append("   ");
        for (int col = 0; col < tablero[0].length; col++) {
            tableroTexto.append(col).append(" ");
        }
        tableroTexto.append("\n");

        for (int x = 0; x < tablero.length; x++) {
            tableroTexto.append(x).append("  ");
            for (int col = 0; col < tablero[x].length; col++) {
                tableroTexto.append(tablero[x][col]).append(" ");
            }
            tableroTexto.append("\n");
        }

        return tableroTexto.toString();
    }
    public boolean esEspacioLibre(int x, int y) {
    return celdas[x][y] == '-';
    }
}


