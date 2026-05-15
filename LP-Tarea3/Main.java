import java.util.Scanner;

import Entidades.Jugador;
import Mapa.Mapa;

/**
 * Clase principal del juego.
 * Inicializa el jugador Cloud y arranca el bucle principal del juego.
 */
public class Main {

    /**
     * Método principal que inicia el juego.
     * Crea al jugador Cloud, muestra el mensaje de bienvenida
     * y lanza el menú principal del mapa.
     * @param args argumentos de la línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println(" La Amenaza de Sephiroth");
        System.out.println("=========================================");
        System.out.println("El mundo está en peligro...");
        System.out.println("Sephiroth se atrincheró en las profundidades del planeta.");
        System.out.println("Tú controlas a Cloud, un mercenario dispuesto a hacerle frente.");
        System.out.println("=========================================\n");

        // Instanciar al jugador Cloud
        Jugador cloud = new Jugador();
        Scanner sc = new Scanner(System.in);
        System.out.println("Cloud ha despertado en el Sector 7.");
        System.out.println("Stats iniciales: " + cloud.getStats());

        // Instanciar el mapa y comenzar el juego
        Mapa mapa = new Mapa();
        mapa.mostrarMapa(cloud, sc);
        sc.close();
    }
}
