package Componentes;

/**
 * Clase que representa una Materia (esfera mágica) del juego.
 * Las materias se equipan en las ranuras del Arma de Cloud para potenciar la magia.
 */
public class Materia {

    private String nombre;
    private Elemento elemento;

    /**
     * Constructor que crea una Materia con un nombre y un elemento dados.
     * @param nombre el nombre de la materia
     * @param elemento el elemento mágico de la materia
     */
    public Materia(String nombre, Elemento elemento) {
        this.nombre = nombre;
        this.elemento = elemento;
    }

    // --- Getters y Setters ---

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Elemento getElemento() { return elemento; }
    public void setElemento(Elemento elemento) { this.elemento = elemento; }

    @Override
    public String toString() {
        return nombre + " [" + elemento + "]";
    }
}
