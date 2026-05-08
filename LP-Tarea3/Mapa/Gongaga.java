package Mapa;

import Componentes.Elemento;
import Componentes.Materia;
import Entidades.Enemigo;
import Entidades.EnemigoSalvaje;
import Entidades.Jugador;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Zona salvaje donde Cloud recolecta recursos.
 * Al explorar, ocurre un evento aleatorio: encontrar una Materia (30%)
 * o sufrir una emboscada de 1-3 EnemigoSalvaje (70%).
 * Requiere nivel mínimo 5 para acceder.
 */
public class Gongaga extends Zona {

    private List<Materia> posMaterias;
    private static final Random rand = new Random();
    private Sector7 sector7Ref;

    /**
     * Constructor de Gongaga. Requiere Nivel 5 para acceder.
     * Inicializa el pool de materias posibles de encontrar.
     * @param sector7 referencia al Sector7 para usar su lógica de combate
     */
    public Gongaga(Sector7 sector7) {
        super("Gongaga", 5);
        this.sector7Ref = sector7;
        this.posMaterias = new ArrayList<>();
        // Pool de materias posibles
        Elemento[] elementos = {Elemento.FUEGO, Elemento.HIELO, Elemento.RAYO, Elemento.CURA};
        for (Elemento e : elementos) {
            posMaterias.add(new Materia("Materia " + e.name(), e));
        }
    }

    /**
     * Ejecuta la exploración de Gongaga. 30% de encontrar una Materia, 70% de emboscada.
     * @param jugador el jugador que explora
     */
    @Override
    public void accionZona(Jugador jugador) {
        System.out.println("\n--- Explorando Gongaga... ---");
        double evento = rand.nextDouble();
        if (evento < 0.30) {
            encontrarMateria(jugador);
        } else {
            emboscada(jugador);
        }
    }

    /**
     * El jugador encuentra una Materia con elemento aleatorio y se añade a la mochila.
     * @param jugador el jugador que recibe la materia
     */
    private void encontrarMateria(Jugador jugador) {
        Elemento[] elementos = {Elemento.FUEGO, Elemento.HIELO, Elemento.RAYO, Elemento.CURA};
        Elemento elementoAleatorio = elementos[rand.nextInt(elementos.length)];
        Materia materia = new Materia("Materia " + elementoAleatorio.name(), elementoAleatorio);
        jugador.getMochila().add(materia);
        System.out.println("*** Cloud encontró una " + materia + "! Se guardó en la mochila. ***");
    }

    /**
     * Genera un grupo de enemigos para la emboscada (1-3 EnemigoSalvaje).
     * 60% de que aparezca 1, 30% de que aparezcan 2, 10% de que aparezcan 3.
     * @return lista de enemigos generados para la emboscada
     */
    public List<Enemigo> generarGrupoEnemigo() {
        int cantidad;
        double prob = rand.nextDouble();
        if (prob < 0.60) cantidad = 1;
        else if (prob < 0.90) cantidad = 2;
        else cantidad = 3;

        List<Enemigo> grupo = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            grupo.add(crearEnemigoAleatorio());
        }
        return grupo;
    }

    /**
     * Crea un EnemigoSalvaje aleatorio del catálogo disponible.
     * @return un nuevo EnemigoSalvaje instanciado
     */
    private EnemigoSalvaje crearEnemigoAleatorio() {
        int tipo = rand.nextInt(3);
        switch (tipo) {
            case 0: // Planta Carnívora
                return new EnemigoSalvaje("Planta Carnívora", 80, 15,
                        Arrays.asList(Elemento.FUEGO, Elemento.HIELO),
                        new ArrayList<>(),
                        Arrays.asList(Elemento.RAYO));
            case 1: // Sapo de la Jungla
                return new EnemigoSalvaje("Sapo de la Jungla", 60, 12,
                        Arrays.asList(Elemento.RAYO, Elemento.HIELO),
                        Arrays.asList(Elemento.FUEGO),
                        new ArrayList<>());
            case 2: // Robot Centinela
            default:
                return new EnemigoSalvaje("Robot Centinela", 100, 20,
                        Arrays.asList(Elemento.RAYO),
                        Arrays.asList(Elemento.FISICO, Elemento.HIELO),
                        new ArrayList<>());
        }
    }

    /**
     * Ejecuta una emboscada con el grupo de enemigos generado.
     * Si Cloud muere, aplica la penalización correspondiente.
     * @param jugador el jugador que es emboscado
     */
    private void emboscada(Jugador jugador) {
        List<Enemigo> grupo = generarGrupoEnemigo();
        System.out.println("*** EMBOSCADA! Aparecen " + grupo.size() + " enemigo(s): ***");
        for (Enemigo e : grupo) {
            System.out.println("  - " + e.getNombre());
        }
        sector7Ref.ejecutarCombate(jugador, grupo, true);

        // Verificar si Cloud murió
        if (jugador.getStats().getHpActual() <= 0) {
            jugador.aplicarPenalidadDerrota();
        }
    }

    /**
     * Valida si el jugador tiene nivel suficiente para entrar a Gongaga (mínimo Nivel 5).
     * @param jugador el jugador
     * @return true si el nivel es >= 5
     */
    @Override
    public boolean validarAcceso(Jugador jugador) {
        if (jugador.getNivel() < nivelRequerido) {
            System.out.println("Necesitas al menos Nivel " + nivelRequerido + " para entrar a Gongaga. Tu nivel: " + jugador.getNivel());
            return false;
        }
        return true;
    }

    // --- Getters y Setters ---

    public List<Materia> getPosMaterias() { return posMaterias; }
    public void setPosMaterias(List<Materia> posMaterias) { this.posMaterias = posMaterias; }
}
