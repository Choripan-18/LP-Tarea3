package Mapa;

import Entidades.Jugador;
import Entidades.Sephiroth;
import java.util.Scanner;
import java.util.Arrays;

/**
 * Zona del Jefe Final. Requiere Nivel 20 y 
 * al menos 2 materias equipadas en el Arma.
 */
public class NucleoPlaneta extends Zona {

    private int materiasMinRequeridas;
    private Sector7 sector7Ref;

    /**
     * Constructor del Núcleo del Planeta.
     * @param sector7 referencia al Sector7 para usar su lógica de combate
     */
    public NucleoPlaneta(Sector7 sector7) {
        super("Núcleo del Planeta", 20);
        this.materiasMinRequeridas = 2;
        this.sector7Ref = sector7;
    }

    /**
     * Inicia el combate directamente contra Sephiroth sin poder huir.
     * Si el jugador es derrotado Sephiroth se restaura para el próximo intento.
     * Si el jugador gana se muestra el mensaje de victoria y el juego termina.
     * @param jugador el jugador
     */
    @Override
    public void accionZona(Jugador jugador, Scanner sc) {
        System.out.println("\n=== NÚCLEO DEL PLANETA ===");
        System.out.println("La presencia de Sephiroth lo llena todo...");
        System.out.println("*** JEFE FINAL: SEPHIROTH ***");

        Sephiroth sephiroth = new Sephiroth();
        sector7Ref.ejecutarCombate(jugador, Arrays.asList(sephiroth), false, sc); // false = no huir

        if (sephiroth.estaMuerto()) {
            mostrarVictoria(jugador);
            System.exit(0);
        } else if (jugador.getStats().getHpActual() <= 0) {
            // Restaurar Sephiroth para el próximo intento y aplicar derrota 
            // (perder mochila y volver a sector 7)
            sephiroth.restaurar();
            jugador.aplicarDerrota();
        }
    }

    /**
     * Muestra el mensaje de victoria al derrotar a Sephiroth.
     * @param jugador el jugador victorioso
     */
    private void mostrarVictoria(Jugador jugador) {
        System.out.println("*** Cloud ha salvado el planeta! Sephiroth ha sido derrotado! ***");
        System.out.println("... Has completado el juego. Felicitaciones! ...");
        System.out.println();
        System.out.println("=== ESTADÍSTICAS FINALES ===");
        System.out.println("Nivel alcanzado: " + jugador.getNivel());
        System.out.println("HP: " + jugador.getStats().getHpActual() + "/" + jugador.getStats().getHpMaximo());
        System.out.println("Materias equipadas: " + jugador.getBusterSword().getMateriasEquipadas());
        System.out.println();
        System.out.println("Gracias por jugar 'La Amenaza de Sephiroth'!");
    }

    /**
     * Valida si el jugador puede entrar al Núcleo del Planeta.
     * Requiere Nivel >= 20 y al menos 2 materias equipadas en el Arma.
     * @param jugador el jugador
     * @return true si cumple todos los requisitos
     */
    @Override
    public boolean validarAcceso(Jugador jugador) {
        if (jugador.getNivel() < nivelRequerido) {
            System.out.println("Necesitas al menos Nivel " + nivelRequerido + " para enfrentar a Sephiroth. Tu nivel: " + jugador.getNivel());
            return false;
        }
        if (jugador.getBusterSword().getMateriasEquipadas().size() < materiasMinRequeridas) {
            System.out.println("Necesitas al menos " + materiasMinRequeridas + " materias equipadas en tu Arma para entrar.");
            return false;
        }
        return true;
    }

    // --- Getters y Setters ---

    public int getMateriasMinRequeridas() { return materiasMinRequeridas; }
    public void setMateriasMinRequeridas(int materiasMinRequeridas) { this.materiasMinRequeridas = materiasMinRequeridas; }
}
