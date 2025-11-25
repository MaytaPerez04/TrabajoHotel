package modelo;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String usuario;
    private String contrasena;
    private String rol;

    // Constructor completo
    public Usuario(int idUsuario, String nombre, String usuario, String contrasena, String rol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    // Constructor simplificado (si solo querés usuario, contraseña y rol)
    public Usuario(String usuario, String contrasena, String rol) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    // Getters
    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getRol() {
        return rol;
    }

    @Override
    public String toString() {
        return "Usuario: " + usuario + " | Nombre: " + nombre + " | Rol: " + rol;
    }
}