package ghostgame;

import javax.swing.JOptionPane;

public class GhostGame {
    //Cesar Garciaa//

    Player[] jugadores = new Player[10];
    int contadorjugadores = 0;
    Player playerActivo = null;
    Player player2 = null;
    Tablero Tablero;
    int dificultad = 8;
    boolean isRandomMode = true; // Modo de colocación aleatoria (por defecto activado)

    public void login() {
        String usuario = JOptionPane.showInputDialog("Ingrese su usuario:").toLowerCase();
        String contra = JOptionPane.showInputDialog("Ingrese su contraseña:");

        for (int i = 0; i < contadorjugadores; i++) {
            if (jugadores[i].getUsuario().equals(usuario) && jugadores[i].getContra().equals(contra)) {
                playerActivo = jugadores[i];
                JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso");
                menu();
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecta");
    }

    public void crearjugador() {
        String usuario = JOptionPane.showInputDialog("Ingrese su usuario:").toLowerCase();
        String contra = JOptionPane.showInputDialog("Ingrese su contraseña:");

        if (contra.length() < 8) {
            JOptionPane.showMessageDialog(null, "La contraseña debe tener al menos 8 caracteres");
            return;
        }

        for (int i = 0; i < contadorjugadores; i++) {
            if (jugadores[i].getUsuario().toLowerCase().equals(usuario)) {
                JOptionPane.showMessageDialog(null, "Nombre de usuario ya en uso");
                return;
            }
        }

        jugadores[contadorjugadores] = new Player(usuario, contra);
        contadorjugadores++;
        JOptionPane.showMessageDialog(null, "El jugador se ha creado exitosamente");
    }

    public void menu() {
        while (true) {
            String menu = JOptionPane.showInputDialog("Menú Principal\n"
                    + "1. Jugar Ghosts\n"
                    + "2. Configuraciones\n"
                    + "3. Reportes\n"
                    + "4. Mi Perfil\n"
                    + "5. Salir");
            switch (menu) {
                case "1":
                    jugar();
                    break;
                case "2":
                    configuraciones();
                    break;
                case "3":
                    reportes();
                    break;
                case "4":
                    perfil();
                    break;
                case "5":
                    JOptionPane.showMessageDialog(null, "Cerrando sesión");
                    Main me = new Main();
                    me.mostrarMenuPrincipal();
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void jugar() {
        int filas = 6;
        int columnas = 6;
        Tablero = new Tablero(filas, columnas);

        String player2Username = JOptionPane.showInputDialog("Ingrese el usuario del jugador 2:").toLowerCase();
        for (int i = 0; i < contadorjugadores; i++) {
            if (jugadores[i].getUsuario().toLowerCase().equals(player2Username)) {
                player2 = jugadores[i];
                break;
            }
        }
        if (player2 == null) {
            JOptionPane.showMessageDialog(null, "Jugador 2 no encontrado. Saliendo del juego.");
            return;
        }

        int fantasmasBuenosActivos = dificultad / 2;
        int fantasmasMalosActivos = dificultad / 2;
        int fantasmasBuenosOponente = dificultad / 2;
        int fantasmasMalosOponente = dificultad / 2;

        colocarFantasmas(playerActivo);
        colocarFantasmas(player2);

        int turno = (Math.random() < 0.5) ? 0 : 1; // Decide aleatoriamente quién inicia el juego
        String[] nombreJugadores = {playerActivo.getUsuario(), player2.getUsuario()};

        while (true) {
            String jugadorActual = nombreJugadores[turno];
            JOptionPane.showMessageDialog(null, "Turno de: " + jugadorActual);

            Tablero.mostrarTablero();

            int xInicio = Coordenada("Ingrese la columna (y) del fantasma a mover (0-5, escriba -1 para salir):");
            if (xInicio == -1) break;

            int yInicio = Coordenada("Ingrese la fila (x) del fantasma a mover (0-5, escriba -1 para salir):");
            if (yInicio == -1) break;

            int xDestino = Coordenada("Ingrese la columna destino (y) (0-5, escriba -1 para salir):");
            if (xDestino == -1) break;

            int yDestino = Coordenada("Ingrese la fila destino (x) (0-5, escriba -1 para salir):");
            if (yDestino == -1) break;

            if (Math.abs(xDestino - xInicio) + Math.abs(yDestino - yInicio) != 1) {
                JOptionPane.showMessageDialog(null, "Movimiento inválido. Solo puedes moverte una casilla horizontal o verticalmente.");
                continue;
            }

            char fantasma = Tablero.getTipoReal(xInicio, yInicio);
            if (fantasma == '-') {
                JOptionPane.showMessageDialog(null, "No hay un fantasma en la posición seleccionada. Intente de nuevo.");
                continue;
            }

            if (!Tablero.valido(xDestino, yDestino)) {
                JOptionPane.showMessageDialog(null, "Movimiento fuera del tablero. Intente nuevamente.");
                continue;
            }

            char fantasmaDestino = Tablero.getTipoReal(xDestino, yDestino);

            if (fantasmaDestino != '-') {
                if (turno == 0) {
                    if (fantasmaDestino == 'B') fantasmasBuenosOponente--;
                    else fantasmasMalosOponente--;
                } else {
                    if (fantasmaDestino == 'B') fantasmasBuenosActivos--;
                    else fantasmasMalosActivos--;
                }
                JOptionPane.showMessageDialog(null, "Fantasma " + (fantasmaDestino == 'B' ? "bueno" : "malo") + " del oponente capturado");
            }

            Tablero.removerFantasma(xInicio, yInicio);
            Tablero.colocarFantasma(xDestino, yDestino, fantasma);

            if (fantasma == 'B' && (xDestino == 0 || xDestino == filas - 1 || yDestino == 0 || yDestino == columnas - 1)) {
                JOptionPane.showMessageDialog(null, "Fantasma bueno llevado fuera del castillo por " + jugadorActual);
                if (turno == 0) {
                    JOptionPane.showMessageDialog(null, playerActivo.getUsuario() + " ha ganado");
                    playerActivo.addPuntos(3);
                } else {
                    JOptionPane.showMessageDialog(null, player2.getUsuario() + " ha ganado");
                    player2.addPuntos(3);
                }
                break;
            }

            if (fantasmasBuenosActivos == 0 || fantasmasMalosOponente == 0) {
                JOptionPane.showMessageDialog(null, "¡" + player2.getUsuario() + " ha ganado!");
                player2.addPuntos(3);
                break;
            }
            if (fantasmasBuenosOponente == 0 || fantasmasMalosActivos == 0) {
                JOptionPane.showMessageDialog(null, "¡" + playerActivo.getUsuario() + " ha ganado!");
                playerActivo.addPuntos(3);
                break;
            }

            turno = 1 - turno; 
        }

        JOptionPane.showMessageDialog(null, "Juego terminado. Regresando al menú principal");
        menu();
    }

    public void colocarFantasmas(Player jugador) {
        int totalFantasmas = dificultad;
        int buenosRestantes = totalFantasmas / 2;
        int malosRestantes = totalFantasmas / 2;

        for (int i = 0; i < totalFantasmas; i++) {
            int x, y;
            char tipo;

            if (isRandomMode) {
                do {
                    x = (int) (Math.random() * 6);
                    y = (int) (Math.random() * 6);
                    tipo = buenosRestantes > 0 ? 'B' : 'M';
                } while (!Tablero.colocarFantasma(x, y, tipo));

                if (tipo == 'B') buenosRestantes--;
                else malosRestantes--;

            } else { 
                x = Coordenada(jugador.getUsuario() + ", ingrese la columna (y) para colocar un fantasma (0-5, escriba -1 para salir):");
                if (x == -1) return;

                y = Coordenada("Ingrese la fila (x) para colocar un fantasma (0-5, escriba -1 para salir):");
                if (y == -1) return;

                tipo = buenosRestantes > 0 ? 'B' : 'M';

                if (!Tablero.colocarFantasma(x, y, tipo)) {
                    JOptionPane.showMessageDialog(null, "Posición inválida. Intente nuevamente.");
                    i--; 
                    continue;
                }

                if (tipo == 'B') buenosRestantes--;
                else malosRestantes--;
            }
        }
    }

  public void configuraciones() {
    while (true) {
        String configuraciones = JOptionPane.showInputDialog("Configuración\n"
                + "1. Dificultad (Normal, Expert, Genius)\n"
                + "2. Modo de Juego (Aleatorio, Manual)\n"
                + "3. Regresar al menu principal");

        if (configuraciones == null) {
            return;
        }

        switch (configuraciones) {
            case "1":
                String diff = JOptionPane.showInputDialog("Seleccione dificultad:\n"
                        + "1. Normal\n"
                        + "2. Expert\n"
                        + "3. Genius");
                if (diff == null) {
                    break; 
                }
                switch (diff) {
                    case "1":
                        dificultad = 8;
                        JOptionPane.showMessageDialog(null, "Dificultad configurada: Normal");
                        break;
                    case "2":
                        dificultad = 4;
                        JOptionPane.showMessageDialog(null, "Dificultad configurada: Expert");
                        break;
                    case "3":
                        dificultad = 2;
                        JOptionPane.showMessageDialog(null, "Dificultad configurada: Genius");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opcion invalida", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                }
                break;
            case "2":
                String mode = JOptionPane.showInputDialog("Seleccione modo:\n"
                        + "1. Aleatorio\n"
                        + "2. Manual");
                if (mode == null) {
                    break; 
                }
                switch (mode) {
                    case "1":
               isRandomMode = true;
                        JOptionPane.showMessageDialog(null, "Modo configurado: Aleatorio");
                        break;
                    case "2":
                        isRandomMode = false;
                        JOptionPane.showMessageDialog(null, "Modo configurado: Manual");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opcion invalida", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                }
                break;
            case "3":
                return; 
            default:
                JOptionPane.showMessageDialog(null, "Opcion invalida", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }
}
    public int Coordenada(String mensaje) {
           
        try {
            return Integer.parseInt(JOptionPane.showInputDialog(mensaje));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Entrada no válida. Por favor ingrese un número.");
            return Coordenada(mensaje);
        }
    }

    public void reportes() {
        while (true) {
            String reportes = JOptionPane.showInputDialog("Reportes\n"
                    + "1. Descripcion de mis ultimos juegos\n"
                    + "2. Ranking de Jugadores\n"
                    + "3. Regresar al menu principal");
            switch (reportes) {
                case "1":
                    mostrargetUJuegos();
                    break;
                case "2":
                    mostrarRanking();
                    break;
                case "3":
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Opcion invalida", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
 public void mostrargetUJuegos() {
    String[] juegos = playerActivo.getUJuegos();
    StringBuilder historial = new StringBuilder("Ultimos 10 juegos:\n");
    for (int i = 9; i >= 0; i--) {
        if (juegos[i] != null) {
            historial.append("- ").append(juegos[i]).append("\n");
        }
    }

    JOptionPane.showMessageDialog(null, historial.toString());
}
    public void mostrarRanking() {
        Player[] jugadoresOrdenados = new Player[contadorjugadores];
        for (int i = 0; i < contadorjugadores; i++) {
            jugadoresOrdenados[i] = jugadores[i];
        }

        for (int i = 0; i < contadorjugadores - 1; i++) {
            for (int j = 0; j < contadorjugadores - i - 1; j++) {
                if (jugadoresOrdenados[j].getPuntos() < jugadoresOrdenados[j + 1].getPuntos()) {
                    Player temp = jugadoresOrdenados[j];
                    jugadoresOrdenados[j] = jugadoresOrdenados[j + 1];
                    jugadoresOrdenados[j + 1] = temp;
                }
            }
        }

        StringBuilder ranking = new StringBuilder("Ranking de jugadores:\n");
        for (int i = 0; i < contadorjugadores; i++) {
            Player jugador = jugadoresOrdenados[i];
            ranking.append((i + 1) + ". " + jugador.getUsuario() + " - Puntos: " + jugador.getPuntos() + "\n");
        }

        JOptionPane.showMessageDialog(null, ranking.toString());
    }

    public void perfil() {
        while (true) {
            String perfil = JOptionPane.showInputDialog("Perfil\n"
                    + "1. Ver mis datos\n"
                    + "2. Cambiar contraseña\n"
                    + "3. Eliminar cuenta\n"
                    + "4. Regresar al menú principal\n");

            switch (perfil) {
                case "1":
                    if (playerActivo != null) {
                        JOptionPane.showMessageDialog(null, "Usuario: " + playerActivo.getUsuario() + "\n"
                                + "Puntos: " + playerActivo.getPuntos());
                    }                     break;
                case "2":
                    if (playerActivo != null) {
                        String nuevaContra = JOptionPane.showInputDialog("Ingrese nueva contraseña: ");
                        if (nuevaContra != null && !nuevaContra.isEmpty()) {
                            playerActivo.cambiarContra(nuevaContra);
                        } else {
                            JOptionPane.showMessageDialog(null, "Por favor introduzca una contraseña", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    break;
                case "3":
                    if (playerActivo != null) {
                        for (int i = 0; i < contadorjugadores; i++) {
                            if (jugadores[i] == playerActivo) {
                                jugadores[i] = jugadores[contadorjugadores - 1];
                                jugadores[contadorjugadores - 1] = null;
                                contadorjugadores--;
                                playerActivo = null;
                                JOptionPane.showMessageDialog(null, "Su cuenta ha sido eliminada.");
                                Main menu=new Main();
                                menu.mostrarMenuPrincipal();
                            }
                        }
                    } 
                    break;
                case "4":
                    return; 
                default:
                    JOptionPane.showMessageDialog(null, "Opcion invalida", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}


    
