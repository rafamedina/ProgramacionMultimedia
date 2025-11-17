package POJO;

public class Usuario {
    private String id;
    private String usuario;
    private String password;
    private int puntuaje;

    public Usuario(String usuario, int puntuaje) {
        this.usuario = usuario;

        this.puntuaje = puntuaje;
    }

    public Usuario(String id, String usuario, String password, int puntuaje) {
        this.id = id;
        this.usuario = usuario;
        this.password = password;
        this.puntuaje = puntuaje;
    }

    public Usuario(String id, String usuario, int puntuaje) {
        this.id = id;
        this.usuario = usuario;
        this.puntuaje = puntuaje;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPuntuaje() {
        return puntuaje;
    }

    public void setPuntuaje(int puntuaje) {
        this.puntuaje = puntuaje;
    }
}
