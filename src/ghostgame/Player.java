package ghostgame;

import javax.swing.JOptionPane;

public class Player {
     String usuario;
     String contra;
     int puntos;
     String[] partidajugadas = new String[10];
     int historial = 0;
     String[] ultimosJuegos = new String[10];
     int indiceJuegos = 0;
     int buenosRestantes;
     int malosRestantes;

    public Player(String usuario, String contra) {
        this.usuario = usuario;
        this.contra = contra;
        this.puntos = 0;
    }

    public String getUsuario() {
        return usuario;
    }

    public int getPuntos() {
        return puntos;
    }

    public String getContra() {
      return contra;
    }

    public void addPuntos(int puntos) {
        this.puntos += puntos;
    }

   public void registrarJuego(String descripcion) {
    ultimosJuegos[indiceJuegos] = descripcion;
    
    indiceJuegos = (indiceJuegos + 1) % 10;
}

    public String[] getUJuegos() {
        return ultimosJuegos;
    }

    public void cambiarContra(String nuevaContra) {
        if (nuevaContra.length() >= 8) {
            this.contra = nuevaContra;
           JOptionPane.showMessageDialog(null, "Contrasena cambiada");
        } else {
            JOptionPane.showMessageDialog(null, "La contraseña debe tener al menos 8 caracteres");
        }
    }
        public int getBuenosRestantes() {
        return buenosRestantes;
    }

    public void setBuenosRestantes(int buenosRestantes) {
        this.buenosRestantes = buenosRestantes;
    }

    public int getMalosRestantes() {
        return malosRestantes;
    }

    public void setMalosRestantes(int malosRestantes) {
        this.malosRestantes = malosRestantes;
    }
}



