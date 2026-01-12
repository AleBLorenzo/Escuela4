package com.example.model;

public class Libro {

     private int id;
    private String titulo;
    private String isbn;
    private int autorId;
    private String genero;
    private int anioPublicacion;
    private boolean disponible;

    public Libro() {
    }

    public Libro(int anioPublicacion, int autorId, boolean disponible, String genero, int id, String isbn, String titulo) {
        this.anioPublicacion = anioPublicacion;
        this.autorId = autorId;
        this.disponible = disponible;
        this.genero = genero;
        this.id = id;
        this.isbn = isbn;
        this.titulo = titulo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getAutorId() {
        return autorId;
    }

    public void setAutorId(int autorId) {
        this.autorId = autorId;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Libro{");
        sb.append("id=").append(id);
        sb.append(", titulo=").append(titulo);
        sb.append(", isbn=").append(isbn);
        sb.append(", autorId=").append(autorId);
        sb.append(", genero=").append(genero);
        sb.append(", anioPublicacion=").append(anioPublicacion);
        sb.append(", disponible=").append(disponible);
        sb.append('}');
        return sb.toString();
    }

}
