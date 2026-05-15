package Entidades;

import java.util.Random;

/**
 * Enemigo de entrenamiento disponible en el simulador del Sector 7.
 * Su ataque nunca reduce el HP del jugador por debajo de 1.
 * Otorga xp pero no suelta chatarra.
 */ 
public class EnemigoSimulador extends Enemigo {

    private static final Random rand = new Random();

    /**
     * Constructor del EnemigoSimulador Soldado Común.
     */
    public EnemigoSimulador() {
        super("Soldado Común", 0, 0, 50, 15);
        // XP aleatorio entre 15 y 20, se asigna al crear.
        this.xpRecompensa = 15 + rand.nextInt(6);
    }

    /**
     * Verifica que el daño calculado no reduzca el HP del jugador bajo 1.
     * @param jugador el jugador objetivo
     * @return true si el ataque fue seguro (garantiza HP >= 1)
     */
    public boolean checkdañoSeguro(Jugador jugador) {
        int daño = calcularDañoFisico();
        int hpResultante = jugador.getStats().getHpActual() - daño;
        return hpResultante >= 1;
    }

    /**
     * Ataca al jugador con un ataque físico. Tiene 85% de precisión.
     * El daño no puede reducir el HP del jugador bajo 1 en el simulador.
     * @param jugador el jugador objetivo del ataque
     */
    @Override
    public void atacar(Jugador jugador) {
        double precision = rand.nextDouble();
        if (precision > 0.85) {
            System.out.println(nombre + " falla su ataque!");
            return;
        }
        int daño = calcularDañoFisico();
        int hpActual = jugador.getStats().getHpActual();
        // Asegurar que HP no baje de 1
        if (hpActual - daño < 1) {
            daño = hpActual - 1;
        }
        if (daño > 0) {
            jugador.getStats().recibirDaño(daño);
            // Cargar barra de ataque límite al recibir daño
            jugador.getBusterSword().cargarLimiteAlRecibirDaño(daño);
            System.out.println(nombre + " ataca a Cloud por " + daño + " de daño físico!");
        } else {
            System.out.println(nombre + " ataca pero Cloud ya tiene 1 HP, el daño es nulo.");
        }
    }

    /**
     * Entrega la recompensa de XP al jugador. No suelta chatarra.
     * @param jugador el jugador que recibe la recompensa
     */
    @Override
    public void darRecompensa(Jugador jugador) {
        System.out.println(nombre + " derrotado! Cloud obtiene " + xpRecompensa + " XP.");
        jugador.recibirXP(xpRecompensa);
    }
}
