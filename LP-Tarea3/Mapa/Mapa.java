package Mapa;

import Entidades.Jugador;
import java.util.Scanner;

/**
 * Clase principal del mapa del juego. Gestiona la navegación entre las tres zonas
 * del mundo: Sector 7, Gongaga y Núcleo del Planeta.
 */
public class Mapa {

    private Sector7 sector7;
    private Gongaga gongaga;
    private NucleoPlaneta nucleoPlaneta;

    /**
     * Constructor del Mapa. Inicializa todas las zonas del juego.
     */
    public Mapa() {
        this.sector7 = new Sector7();
        this.gongaga = new Gongaga(sector7);
        this.nucleoPlaneta = new NucleoPlaneta(sector7);
    }

    /**
     * Muestra el menú principal del mapa y permite al jugador elegir a dónde ir.
     * @param jugador el jugador que navega el mapa
     * @param sc el scanner para leer la entrada del usuario
     */
    public void mostrarMapa(Jugador jugador, Scanner sc) {
        boolean jugando = true;
        while (jugando) {
            System.out.println("\n=============================");
            System.out.println("    MAPA DEL MUNDO");
            System.out.println("=============================");
            System.out.println("Nivel: " + jugador.getNivel() + " | HP: " + jugador.getStats().getHpActual() + "/" + jugador.getStats().getHpMaximo());
            System.out.println("-----------------------------");
            System.out.println("1. Sector 7 (Zona Segura)");
            System.out.println("2. Gongaga (Zona Salvaje) [Req: Nivel 5]");
            System.out.println("3. Núcleo del Planeta (Jefe Final) [Req: Nivel 20, 2 Materias]");
            System.out.println("0. Salir del juego");
            System.out.println("=============================");
            System.out.print("\n¿A dónde deseas ir? \n");
            String opcion = sc.nextLine().trim();

            switch (opcion) {
                case "1":
                    sector7.accionZona(jugador, sc);
                    break;
                case "2":
                    if (gongaga.validarAcceso(jugador)) {
                        gongaga.accionZona(jugador, sc);
                    }
                    break;
                case "3":
                    if (nucleoPlaneta.validarAcceso(jugador)) {
                        nucleoPlaneta.accionZona(jugador, sc);
                    }
                    break;
                case "0":
                    System.out.println("Hasta la próxima!");
                    jugando = false;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    // --- Getters y Setters ---

    public Sector7 getSector7() { return sector7; }
    public void setSector7(Sector7 sector7) { this.sector7 = sector7; }

    public Gongaga getGongaga() { return gongaga; }
    public void setGongaga(Gongaga gongaga) { this.gongaga = gongaga; }

    public NucleoPlaneta getNucleoPlaneta() { return nucleoPlaneta; }
    public void setNucleoPlaneta(NucleoPlaneta nucleoPlaneta) { this.nucleoPlaneta = nucleoPlaneta; }
}
