package Entidades;

import Componentes.Estadisticas;

/**
 * Clase abstracta base para todos los enemigos del juego.
 * Define la estructura común de nombre, recompensa de XP,
 * recompensa de chatarra y estadísticas de combate.
 */
public abstract class Enemigo {

    protected String nombre;
    protected int xpRecompensa;
    protected int chatarraRecompensa;
    protected Estadisticas stats;

    /**
     * Constructor base de un enemigo.
     * @param nombre nombre del enemigo
     * @param xpRecompensa XP que otorga al ser derrotado
     * @param chatarraRecompensa chatarra que suelta al ser derrotado
     * @param hpMaximo HP máximo del enemigo
     * @param fuerza Fuerza del enemigo
     */
    public Enemigo(String nombre, int xpRecompensa, int chatarraRecompensa, int hpMaximo, int fuerza) {
        this.nombre = nombre;
        this.xpRecompensa = xpRecompensa;
        this.chatarraRecompensa = chatarraRecompensa;
        this.stats = new Estadisticas(hpMaximo, fuerza, 0);
    }

    /**
     * Realiza un ataque físico contra el jugador.
     * El daño se calcula como floor(Fuerza x 1.25).
     * Tiene un 85% de probabilidad de acertar (90% para Sephiroth).
     * @param jugador el jugador objetivo del ataque
     */
    public abstract void atacar(Jugador jugador);

    /**
     * Entrega la recompensa de XP y chatarra al jugador al ser derrotado.
     * @param jugador el jugador que recibe la recompensa
     */
    public abstract void darRecompensa(Jugador jugador);

    /**
     * Verifica si el enemigo está muerto (HP <= 0).
     * @return true si el HP actual es 0 o menos
     */
    public boolean estaMuerto() {
        return stats.getHpActual() <= 0;
    }

    /**
     * Calcula el daño físico base: floor(Fuerza x 1.25).
     * @return daño físico calculado
     */
    protected int calcularDañoFisico() {
        return (int)(stats.getFuerza() * 1.25);
    }

    // --- Getters y Setters ---

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getXpRecompensa() { return xpRecompensa; }
    public void setXpRecompensa(int xpRecompensa) { this.xpRecompensa = xpRecompensa; }

    public int getChatarraRecompensa() { return chatarraRecompensa; }
    public void setChatarraRecompensa(int chatarraRecompensa) { this.chatarraRecompensa = chatarraRecompensa; }

    public Estadisticas getStats() { return stats; }
    public void setStats(Estadisticas stats) { this.stats = stats; }

    @Override
    public String toString() {
        return String.format("%s | HP: %d/%d", nombre, stats.getHpActual(), stats.getHpMaximo());
    }
}
