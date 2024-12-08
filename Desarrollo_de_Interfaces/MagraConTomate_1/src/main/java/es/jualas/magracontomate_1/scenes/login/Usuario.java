package es.jualas.magracontomate_1.scenes.login;

import java.util.Objects;

public class Usuario {
    private String nombre; // Nombre del usuario
    private String apellido; // Apellido del usuario
    private String email; // Email del usuario
    private String password; // Contraseña del usuario


    // Constructor de la clase Usuario
    public Usuario(String nombre, String apellido, String email, String password) {
        this.nombre = nombre; // Inicializa el nombre
        this.apellido = apellido; // Inicializa el apellido
        this.email = email; // Inicializa el email
        this.password = password; // Inicializa la contraseña
    }

    public String getNombre() {
        return nombre; // Devuelve el nombre del usuario
    }

    public void setNombre(String nombre) {
        this.nombre = nombre; // Establece el nombre del usuario
    }

    public String getApellido() {
        return apellido; // Devuelve el apellido del usuario
    }

    public void setApellido(String apellido) {
        this.apellido = apellido; // Establece el apellido del usuario
    }

    public String getEmail() {
        return email; // Devuelve el email del usuario
    }

    public void setEmail(String email) {
        this.email = email; // Establece el email del usuario
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public boolean checkNombre(String nombre) {
        return this.nombre.equals(nombre);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(nombre, usuario.nombre) &&
                Objects.equals(password, usuario.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, password);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}