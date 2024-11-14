package com.example.eje.model;

import java.util.Objects;

/**
 * Clase que representa una Persona con atributos basicos como nombre, apellidos y edad.
 * Incluye metodos para acceder a los atributos y compararlos entre instancias.
 */
public class Persona {
    private String nombre;
    private String apellidos;
    private int edad;

    /**
     * Constructor que inicializa una nueva instancia de Persona.
     *
     * @param nombre    Nombre de la persona.
     * @param apellidos Apellidos de la persona.
     * @param edad      Edad de la persona.
     */
    public Persona(String nombre, String apellidos, int edad) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
    }

    /**
     * Obtiene el nombre de la persona.
     *
     * @return Nombre de la persona.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene los apellidos de la persona.
     *
     * @return Apellidos de la persona.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Obtiene la edad de la persona.
     *
     * @return Edad de la persona.
     */
    public int getEdad() {
        return edad;
    }

    /**
     * Compara esta instancia de Persona con otra para verificar igualdad.
     *
     * @param obj Objeto a comparar.
     * @return true si los objetos son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Persona persona = (Persona) obj;

        if (edad != persona.edad) return false;
        if (!nombre.equals(persona.nombre)) return false;
        return apellidos.equals(persona.apellidos);
    }

    /**
     * Genera un codigo hash para esta instancia de Persona.
     *
     * @return Codigo hash basado en el nombre, los apellidos y la edad.
     */
    @Override
    public int hashCode() {
        return Objects.hash(nombre, apellidos);
    }
}