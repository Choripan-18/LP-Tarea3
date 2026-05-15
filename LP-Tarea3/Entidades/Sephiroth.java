package Entidades;

import java.util.Random;

/**
 * Jefe final del juego ubicado en el núcleo del planeta.
 * Su ataque SuperNova aniquila instantáneamente si su contador llega a 10.
 */
public class Sephiroth extends Enemigo {

    private int contadorSuperNova;
    private static final int LIMITE_SUPER_NOVA = 10;
    private static final Random rand = new Random();

    /**
     * Constructor de Sephiroth con sus estadísticas base: HP: 500, Fuerza: 40.
     */
    public Sephiroth() {
        super("Sephiroth", 0, 0, 500, 40);
        this.contadorSuperNova = 0;
    }

    /**
     * Ataca al jugador con un ataque físico. Tiene 90% de precisión.
     * Después del ataque, incrementa el contadorSuperNova.
     * Si el contador llega a 10, lanza SuperNova.
     * @param jugador el jugador objetivo del ataque
     */
    @Override
    public void atacar(Jugador jugador) {
        double precision = rand.nextDouble();
        if (precision > 0.90) {
            System.out.println("Sephiroth falla su ataque!");
        } else {
            int daño = calcularDañoFisico();
            jugador.getStats().recibirDaño(daño);
            jugador.getBusterSword().cargarLimiteAlRecibirDaño(daño);
            System.out.println("Sephiroth ataca a Cloud por " + daño + " de daño físico!");
        }
        // Incrementar contador de SuperNova después de atacar
        contadorSuperNova++;
        System.out.println("[Contador SuperNova: " + contadorSuperNova + "/" + LIMITE_SUPER_NOVA + "]");
        if (contadorSuperNova >= LIMITE_SUPER_NOVA) {
            lanzarSuperNova(jugador);
        }
    }

    /**
     * Lanza SuperNova, aniquilando a Cloud instantáneamente.
     * @param jugador el jugador objetivo
     */
    public void lanzarSuperNova(Jugador jugador) {
        System.out.println("*** Sephiroth lanza SUPERNOVA! ***");
        System.out.println("El ataque de Sephiroth destruye todo a su alrededor, has sido aniquilado...");
        jugador.getStats().setHpActual(0);
    }

    /**
     * Reinicia el contadorSuperNova a 0 (cuando Cloud lo golpea con un Límite).
     */
    public void reiniciarContadorSuperNova() {
        System.out.println("[El ataque Límite de Cloud interrumpe la carga de SuperNova! Contador reiniciado a 0]");
        this.contadorSuperNova = 0;
    }

    /**
     * Restaura completamente a Sephiroth (HP y contador) para un nuevo intento.
     */
    public void restaurar() {
        this.stats.restaurarHPCompleto();
        this.contadorSuperNova = 0;
        System.out.println("Sephiroth se ha restaurado para el próximo intento.");
    }

    /**
     * Entrega la recompensa al jugador (Sephiroth no otorga XP ni chatarra).
     * @param jugador el jugador que recibe la recompensa
     */
    @Override
    public void darRecompensa(Jugador jugador) {
    }

    // --- Getters y Setters ---

    public int getContadorSuperNova() { return contadorSuperNova; }
    public void setContadorSuperNova(int contadorSuperNova) { this.contadorSuperNova = contadorSuperNova; }
}
