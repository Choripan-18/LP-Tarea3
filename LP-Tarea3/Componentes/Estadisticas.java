package Componentes;

/**
 * Clase que representa las estadísticas de combate de una entidad.
 * Gestiona HP, MP, Fuerza y Magia tanto actuales como máximos.
 */
public class Estadisticas {

    private int hpActual;
    private int hpMaximo;
    private int mpActual;
    private int mpMaximo;
    private int fuerza;
    private int magia;

    /**
     * Constructor que inicializa las estadísticas con los valores base de Nivel 1.
     * HP Máximo: 200, MP Máximo: 50, Fuerza: 15, Magia: 15.
     */
    public Estadisticas() {
        this.hpMaximo = 200;
        this.hpActual = 200;
        this.mpMaximo = 50;
        this.mpActual = 50;
        this.fuerza = 15;
        this.magia = 15;
    }

    /**
     * Constructor con valores personalizados para instanciar estadísticas de enemigos.
     * @param hpMaximo HP máximo de la entidad
     * @param fuerza Fuerza de la entidad
     * @param magia Magia de la entidad
     */
    public Estadisticas(int hpMaximo, int fuerza, int magia) {
        this.hpMaximo = hpMaximo;
        this.hpActual = hpMaximo;
        this.mpMaximo = 0;
        this.mpActual = 0;
        this.fuerza = fuerza;
        this.magia = magia;
    }

    /**
     * Aplica daño al HP actual de la entidad, sin bajar de 0.
     * @param cantidad el daño recibido
     */
    public void recibirDMG(int cantidad) {
        this.hpActual -= cantidad;
        if (this.hpActual < 0) {
            this.hpActual = 0;
        }
    }

    /**
     * Restaura HP sin superar el límite máximo actual.
     * @param cantidad la cantidad de HP a restaurar
     */
    public void restaurarHP(int cantidad) {
        this.hpActual += cantidad;
        if (this.hpActual > this.hpMaximo) {
            this.hpActual = this.hpMaximo;
        }
    }

    /**
     * Restaura el HP al máximo (usado al regresar al Sector 7 tras derrota).
     */
    public void restaurarHPCompleto() {
        this.hpActual = this.hpMaximo;
    }

    /**
     * Restaura el MP al máximo (usado al iniciar combate).
     */
    public void restaurarMPCompleto() {
        this.mpActual = this.mpMaximo;
    }

    /**
     * Consume MP si hay suficiente disponible.
     * @param cantidad el coste de MP a consumir
     * @return true si se pudo consumir, false si no había suficiente MP
     */
    public boolean consumirMP(int cantidad) {
        if (this.mpActual >= cantidad) {
            this.mpActual -= cantidad;
            return true;
        }
        return false;
    }

    // --- Getters y Setters ---

    public int getHpActual() { return hpActual; }
    public void setHpActual(int hpActual) { this.hpActual = hpActual; }

    public int getHpMaximo() { return hpMaximo; }
    public void setHpMaximo(int hpMaximo) { this.hpMaximo = hpMaximo; }

    public int getMpActual() { return mpActual; }
    public void setMpActual(int mpActual) { this.mpActual = mpActual; }

    public int getMpMaximo() { return mpMaximo; }
    public void setMpMaximo(int mpMaximo) { this.mpMaximo = mpMaximo; }

    public int getFuerza() { return fuerza; }
    public void setFuerza(int fuerza) { this.fuerza = fuerza; }

    public int getMagia() { return magia; }
    public void setMagia(int magia) { this.magia = magia; }

    @Override
    public String toString() {
        return String.format("HP: %d/%d | MP: %d/%d | FUE: %d | MAG: %d",
                hpActual, hpMaximo, mpActual, mpMaximo, fuerza, magia);
    }
}
