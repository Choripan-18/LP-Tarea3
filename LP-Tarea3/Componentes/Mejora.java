package Componentes;

/**
 * Clase que representa una mejora permanente comprable en la Tienda del Sector 7.
 * Aplica bonificaciones a las estadísticas base de Cloud.
 */
public class Mejora {

    private String nombre;
    private int costoChatarra;
    private TipoStat statAfectado;
    private int valorBono;

    /**
     * Constructor que crea una Mejora con sus atributos de tienda.
     * @param nombre nombre de la mejora
     * @param costoChatarra costo en chatarra para adquirirla
     * @param statAfectado estadística que mejora
     * @param valorBono cantidad de mejora aplicada
     */
    public Mejora(String nombre, int costoChatarra, TipoStat statAfectado, int valorBono) {
        this.nombre = nombre;
        this.costoChatarra = costoChatarra;
        this.statAfectado = statAfectado;
        this.valorBono = valorBono;
    }

    // --- Getters y Setters ---

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getCostoChatarra() { return costoChatarra; }
    public void setCostoChatarra(int costoChatarra) { this.costoChatarra = costoChatarra; }

    public TipoStat getStatAfectado() { return statAfectado; }
    public void setStatAfectado(TipoStat statAfectado) { this.statAfectado = statAfectado; }

    public int getValorBono() { return valorBono; }
    public void setValorBono(int valorBono) { this.valorBono = valorBono; }

    @Override
    public String toString() {
        return String.format("%s (+%d %s) - Costo: %d chatarra", nombre, valorBono, statAfectado, costoChatarra);
    }
}
