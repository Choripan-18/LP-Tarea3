package Entidades;

import Componentes.Elemento;
import Componentes.Estadisticas;
import Componentes.Vulnerable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Enemigo salvaje de la zona Gongaga. Sensible al poder elemental.
 * Implementa la interfaz Vulnerable para gestionar debilidades, resistencias e inmunidades.
 * Al ser derrotado suelta XP (80-100) y Chatarra (50-75).
 */
public class EnemigoSalvaje extends Enemigo implements Vulnerable {

    private List<Elemento> debilidades;
    private List<Elemento> resistencias;
    private List<Elemento> inmunidades;

    private static final Random rand = new Random();

    /**
     * Constructor de un EnemigoSalvaje con estadísticas y afinidades personalizadas.
     * @param nombre nombre del enemigo
     * @param hpMaximo HP máximo
     * @param fuerza fuerza de ataque
     * @param debilidades lista de elementos ante los que es débil (x2.0)
     * @param resistencias lista de elementos ante los que resiste (x0.5)
     * @param inmunidades lista de elementos ante los que es inmune (x0.0)
     */
    public EnemigoSalvaje(String nombre, int hpMaximo, int fuerza,
                          List<Elemento> debilidades,
                          List<Elemento> resistencias,
                          List<Elemento> inmunidades) {
        super(nombre, 0, 0, hpMaximo, fuerza);
        this.debilidades = debilidades != null ? debilidades : new ArrayList<>();
        this.resistencias = resistencias != null ? resistencias : new ArrayList<>();
        this.inmunidades = inmunidades != null ? inmunidades : new ArrayList<>();
        // XP y chatarra aleatorios, calculados al instanciarse
        this.xpRecompensa = 80 + rand.nextInt(21);
        this.chatarraRecompensa = 50 + rand.nextInt(26);
    }

    /**
     * Evalúa el multiplicador de daño mágico según el elemento del ataque recibido.
     * @param elemento el elemento del ataque de Cloud
     * @return 2.0 si es debilidad, 0.5 si es resistencia, 0.0 si es inmunidad, 1.0 si es neutro
     */
    @Override
    public double evaluarDebilidad(Elemento elemento) {
        if (inmunidades.contains(elemento)) return 0.0;
        if (debilidades.contains(elemento)) return 2.0;
        if (resistencias.contains(elemento)) return 0.5;
        return 1.0;
    }

    /**
     * Ataca al jugador con daño físico. Tiene 85% de precisión.
     * @param jugador el jugador objetivo del ataque
     */
    @Override
    public void atacar(Jugador jugador) {
        double precision = rand.nextDouble();
        if (precision > 0.85) {
            System.out.println(nombre + " falla su ataque!");
            return;
        }
        int dano = calcularDanoFisico();
        jugador.getStats().recibirDMG(dano);
        jugador.getBusterSword().cargarLimiteAlRecibirDano(dano);
        System.out.println(nombre + " ataca a Cloud por " + dano + " de daño físico!");
    }

    /**
     * Entrega la recompensa de XP y chatarra al jugador.
     * @param jugador el jugador que recibe la recompensa
     */
    @Override
    public void giveXpRecompensa(Jugador jugador) {
        System.out.println(nombre + " derrotado! Cloud obtiene " + xpRecompensa + " XP y " + chatarraRecompensa + " de chatarra.");
        jugador.recibirXP(xpRecompensa);
        jugador.giveChatarraRecompensa(this);
    }

    // --- Getters y Setters ---

    public List<Elemento> getDebilidades() { return debilidades; }
    public void setDebilidades(List<Elemento> debilidades) { this.debilidades = debilidades; }

    public List<Elemento> getResistencias() { return resistencias; }
    public void setResistencias(List<Elemento> resistencias) { this.resistencias = resistencias; }

    public List<Elemento> getInmunidades() { return inmunidades; }
    public void setInmunidades(List<Elemento> inmunidades) { this.inmunidades = inmunidades; }
}
