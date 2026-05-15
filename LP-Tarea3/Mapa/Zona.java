package Mapa;

import Entidades.Enemigo;
import Entidades.Jugador;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clase abstracta que representa una zona del mundo del juego.
 * Cada zona tiene un nombre, un nivel mínimo requerido y una lista de enemigos disponibles.
 */
public abstract class Zona {

    protected String nombre;
    protected int nivelRequerido;
    protected List<Enemigo> enemigosDisponibles;

    /**
     * Constructor base de una Zona.
     * @param nombre nombre de la zona
     * @param nivelRequerido nivel mínimo necesario para entrar
     */
    public Zona(String nombre, int nivelRequerido) {
        this.nombre = nombre;
        this.nivelRequerido = nivelRequerido;
        this.enemigosDisponibles = new ArrayList<>();
    }

    /**
     * Ejecuta la acción principal de la zona (explorar, combatir, etc.).
     * @param jugador el jugador que realiza la acción
     * @param sc el scanner para leer la entrada del usuario
     */
    public abstract void accionZona(Jugador jugador, Scanner sc);

    /**
     * Valida si el jugador cumple los requisitos mínimos para ingresar a la zona.
     * @param jugador el jugador que intenta ingresar
     * @return true si el jugador puede ingresar, false si no cumple los requisitos
     */
    public abstract boolean validarAcceso(Jugador jugador);

    // --- Getters y Setters ---

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getNivelRequerido() { return nivelRequerido; }
    public void setNivelRequerido(int nivelRequerido) { this.nivelRequerido = nivelRequerido; }

    public List<Enemigo> getEnemigosDisponibles() { return enemigosDisponibles; }
    public void setEnemigosDisponibles(List<Enemigo> enemigosDisponibles) { this.enemigosDisponibles = enemigosDisponibles; }
}
