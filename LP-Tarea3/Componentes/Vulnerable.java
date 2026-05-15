package Componentes;

/**
 * Interfaz que permite evaluar la debilidad de 
 * un enemigo ante un elemento específico.
 */
public interface Vulnerable {

    /**
     * Evalúa el multiplicador de daño según el elemento del ataque mágico recibido.
     * @param elemento el elemento del ataque mágico utilizado
     * @return 2.0 si es debilidad, 0.5 si es resistencia, 0.0 si es inmunidad, 1.0 si es neutro
     */
    double evaluarDebilidad(Elemento elemento);
}
