package Mapa;

import Componentes.*;
import Entidades.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Zona Segura y Base de Operaciones. Cloud no corre peligro aquí.
 * Ofrece el Simulador de Combate y la Tienda de Chatarra.
 */
public class Sector7 extends Zona {

    private List<Mejora> tiendaLocal;
    private static final Random rand = new Random();

    /**
     * Constructor del Sector 7. No requiere nivel mínimo para acceder.
     * Inicializa la tienda con las tres Mejoras disponibles.
     */
    public Sector7() {
        super("Sector 7", 1);
        tiendaLocal = new ArrayList<>();
        tiendaLocal.add(new Mejora("Mejora de Vitalidad", 100, TipoStat.HP_MAX, 20));
        tiendaLocal.add(new Mejora("Mejora de Éter", 120, TipoStat.MP_MAX, 10));
        tiendaLocal.add(new Mejora("Mejora Física", 150, TipoStat.FUERZA, 10));
    }

    /**
     * Muestra el menú del Sector 7 con las opciones disponibles.
     * @param jugador el jugador que interactúa con la zona
     */
    @Override
    public void accionZona(Jugador jugador) {
        Scanner sc = new Scanner(System.in);
        boolean enSector7 = true;
        while (enSector7) {
            System.out.println("\n=== SECTOR 7 ===");
            System.out.println("1. Iniciar Simulador de Combate");
            System.out.println("2. Abrir Tienda de Chatarra");
            System.out.println("3. Ver estado de Cloud");
            System.out.println("4. Gestionar Materias (Equipar/Desequipar)");
            System.out.println("0. Volver al Mapa");
            System.out.print("Opción: ");
            String opcion = sc.nextLine().trim();
            switch (opcion) {
                case "1": iniciarSimulador(jugador); break;
                case "2": abrirTienda(jugador); break;
                case "3": jugador.mostrarEstado(); break;
                case "4": gestionarMaterias(jugador, sc); break;
                case "0": enSector7 = false; break;
                default: System.out.println("Opción no válida.");
            }
        }
    }

    /**
     * Inicia el Simulador de Combate contra un EnemigoSimulador.
     * Si Cloud pierde aquí, no hay penalización (queda con 1 HP).
     * @param jugador el jugador que entra al simulador
     */
    public void iniciarSimulador(Jugador jugador) {
        System.out.println("\n--- SIMULADOR DE COMBATE ---");
        int cantidad = 1 + rand.nextInt(2); // 1 o 2 enemigos
        List<Enemigo> enemigos = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            enemigos.add(new EnemigoSimulador());
        }
        System.out.println("Aparecen " + cantidad + " Soldado(s) Común!");
        ejecutarCombate(jugador, enemigos, false);
        // Restaurar HP/MP de Cloud tras el simulador (sin penalización)
        jugador.getStats().restaurarHPCompleto();
        jugador.getStats().restaurarMPCompleto();
        System.out.println("Fin del simulador. HP de Cloud restaurado.");
    }

    /**
     * Abre la Tienda de Chatarra donde Cloud puede comprar Mejoras permanentes.
     * @param jugador el jugador que interactúa con la tienda
     */
    public void abrirTienda(Jugador jugador) {
        Scanner sc = new Scanner(System.in);
        boolean enTienda = true;
        while (enTienda) {
            System.out.println("\n--- TIENDA DE CHATARRA ---");
            System.out.println("Chatarra disponible: " + jugador.getChatarra());
            for (int i = 0; i < tiendaLocal.size(); i++) {
                System.out.println((i + 1) + ". " + tiendaLocal.get(i));
            }
            System.out.println("0. Salir de la tienda");
            System.out.print("¿Qué deseas comprar? ");
            String op = sc.nextLine().trim();
            if (op.equals("0")) {
                enTienda = false;
                continue;
            }
            try {
                int idx = Integer.parseInt(op) - 1;
                if (idx < 0 || idx >= tiendaLocal.size()) {
                    System.out.println("Opción no válida.");
                    continue;
                }
                Mejora mejora = tiendaLocal.get(idx);
                if (jugador.getChatarra() < mejora.getCostoChatarra()) {
                    System.out.println("No tienes suficiente chatarra. Necesitas " + mejora.getCostoChatarra() + ".");
                    continue;
                }
                // Aplicar mejora
                jugador.setChatarra(jugador.getChatarra() - mejora.getCostoChatarra());
                aplicarMejora(jugador, mejora);
                System.out.println("Compraste: " + mejora.getNombre() + "!");
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida.");
            }
        }
    }

    /**
     * Aplica el bono de una Mejora a las estadísticas de Cloud.
     * @param jugador el jugador beneficiado
     * @param mejora la mejora a aplicar
     */
    private void aplicarMejora(Jugador jugador, Mejora mejora) {
        Estadisticas s = jugador.getStats();
        switch (mejora.getStatAfectado()) {
            case HP_MAX:
                s.setHpMaximo(s.getHpMaximo() + mejora.getValorBono());
                s.setHpActual(s.getHpActual() + mejora.getValorBono());
                break;
            case MP_MAX:
                s.setMpMaximo(s.getMpMaximo() + mejora.getValorBono());
                s.setMpActual(s.getMpActual() + mejora.getValorBono());
                break;
            case FUERZA:
                s.setFuerza(s.getFuerza() + mejora.getValorBono());
                break;
        }
    }

    /**
     * Permite a Cloud gestionar sus materias: equipar desde la mochila o desequipar del Arma.
     * @param jugador el jugador
     * @param sc el scanner de entrada
     */
    private void gestionarMaterias(Jugador jugador, Scanner sc) {
        boolean gestionando = true;
        while (gestionando) {
            System.out.println("\n--- GESTIÓN DE MATERIAS ---");
            System.out.println("Arma (" + jugador.getBusterSword().getMateriasEquipadas().size() + "/5): " + jugador.getBusterSword().getMateriasEquipadas());
            System.out.println("Mochila (" + jugador.getMochila().size() + "): " + jugador.getMochila());
            System.out.println("1. Equipar materia de la mochila");
            System.out.println("2. Desequipar materia del Arma");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            String op = sc.nextLine().trim();
            switch (op) {
                case "1":
                    if (jugador.getMochila().isEmpty()) {
                        System.out.println("La mochila está vacía.");
                        break;
                    }
                    for (int i = 0; i < jugador.getMochila().size(); i++) {
                        System.out.println((i + 1) + ". " + jugador.getMochila().get(i));
                    }
                    System.out.print("¿Cuál equipar? ");
                    try {
                        int idx = Integer.parseInt(sc.nextLine().trim()) - 1;
                        if (idx >= 0 && idx < jugador.getMochila().size()) {
                            jugador.equiparMateria(jugador.getMochila().get(idx));
                        }
                    } catch (NumberFormatException e) { System.out.println("Entrada no válida."); }
                    break;
                case "2":
                    if (jugador.getBusterSword().getMateriasEquipadas().isEmpty()) {
                        System.out.println("No hay materias equipadas.");
                        break;
                    }
                    for (int i = 0; i < jugador.getBusterSword().getMateriasEquipadas().size(); i++) {
                        System.out.println((i + 1) + ". " + jugador.getBusterSword().getMateriasEquipadas().get(i));
                    }
                    System.out.print("¿Cuál desequipar? ");
                    try {
                        int idx = Integer.parseInt(sc.nextLine().trim()) - 1;
                        if (idx >= 0 && idx < jugador.getBusterSword().getMateriasEquipadas().size()) {
                            jugador.desequiparMateria(jugador.getBusterSword().getMateriasEquipadas().get(idx));
                        }
                    } catch (NumberFormatException e) { System.out.println("Entrada no válida."); }
                    break;
                case "0":
                    gestionando = false;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    /**
     * Ejecuta un combate por turnos entre Cloud y una lista de enemigos.
     * @param jugador el jugador
     * @param enemigos la lista de enemigos a combatir
     * @param esPeligroso si true, la muerte de Cloud aplica penalización
     */
    public void ejecutarCombate(Jugador jugador, List<Enemigo> enemigos, boolean esPeligroso) {
        Scanner sc = new Scanner(System.in);
        jugador.getStats().restaurarMPCompleto();

        System.out.println("\n=== INICIO DEL COMBATE ===");
        boolean combateActivo = true;

        java.util.Set<Enemigo> recompensasDadas = new java.util.HashSet<>();

        while (combateActivo) {
            // Mostrar estado
            System.out.println("\n--- TURNO ---");
            System.out.println(jugador.getNombre() + " | " + jugador.getStats() + " | Límite: " + jugador.getBusterSword().getCargaLimite() + "/100");
            System.out.println("Enemigos:");
            for (int i = 0; i < enemigos.size(); i++) {
                if (!enemigos.get(i).estaMuerto()) {
                    System.out.println("  " + (i + 1) + ". " + enemigos.get(i));
                }
            }

            // Turno del jugador
            System.out.println("\nAcciones:");
            System.out.println("1. Atacar (Físico)");
            System.out.println("2. Magia");
            System.out.println("3. Curarse (CURA)");
            if (jugador.getBusterSword().limiteDisponible()) {
                System.out.println("4. LÍMITE (Disponible!)");
            }
            if (esPeligroso) System.out.println("5. Huir (50% éxito)");
            System.out.print("Opción: ");
            String accion = sc.nextLine().trim();

            Enemigo objetivo = seleccionarEnemigo(enemigos, sc);
            if (objetivo == null && !accion.equals("3") && !accion.equals("5")) {
                System.out.println("No hay enemigos disponibles.");
                break;
            }

            switch (accion) {
                case "1": // Ataque físico
                    int danoFisico = jugador.getBusterSword().calcularDanoFisico();
                    if (objetivo != null) {
                        objetivo.getStats().recibirDMG(danoFisico);
                        jugador.getBusterSword().cargarLimiteAlInfligirDano(danoFisico);
                        System.out.println("Cloud ataca a " + objetivo.getNombre() + " por " + danoFisico + " de daño físico!");
                    }
                    break;
                case "2": // Magia ofensiva
                    usarMagia(jugador, objetivo, sc, false);
                    break;
                case "3": // Curarse
                    usarCura(jugador, sc);
                    break;
                case "4": // Límite
                    if (jugador.getBusterSword().limiteDisponible()) {
                        int danoLimite = jugador.getBusterSword().calcularDanoLimite();
                        if (objetivo != null) {
                            objetivo.getStats().recibirDMG(danoLimite);
                            System.out.println("Cloud lanza OMNISLASH a " + objetivo.getNombre() + " por " + danoLimite + " de daño!");
                            // Si el objetivo es Sephiroth, reiniciar contador
                            if (objetivo instanceof Sephiroth) {
                                ((Sephiroth) objetivo).reiniciarContadorSuperNova();
                            }
                        }
                    } else {
                        System.out.println("El Límite no está disponible todavía.");
                    }
                    break;
                case "5": // Huir
                    if (esPeligroso) {
                        if (Math.random() < 0.5) {
                            System.out.println("Cloud huye exitosamente!");
                            return;
                        } else {
                            System.out.println("Cloud no pudo huir! Pierde el turno.");
                        }
                    } else {
                        System.out.println("Opción no válida.");
                    }
                    break;
                default:
                    System.out.println("Opción no válida, Cloud pierde el turno.");
            }

            // Verificar si todos los enemigos están muertos
            boolean todosVencidos = true;
            for (Enemigo e : enemigos) {
                if (e.estaMuerto()) {
                    // Dar recompensa si aún no se dio
                    if (!recompensasDadas.contains(e)) {
                        recompensasDadas.add(e);
                        e.giveXpRecompensa(jugador);
                    }
                } else {
                    todosVencidos = false;
                }
            }
            if (todosVencidos) {
                System.out.println("*** Todos los enemigos han sido derrotados! ***");
                combateActivo = false;
                break;
            }

            // Verificar si Cloud murió
            if (jugador.getStats().getHpActual() <= 0) {
                System.out.println("Cloud ha sido derrotado...");
                combateActivo = false;
                break;
            }

            // Turno de los enemigos
            System.out.println("\n-- Turno de los enemigos --");
            for (Enemigo e : enemigos) {
                if (!e.estaMuerto()) {
                    // Probabilidad de ataque conjunto (si hay más de uno)
                    long vivos = enemigos.stream().filter(en -> !en.estaMuerto()).count();
                    if (vivos == 1 || debeAtacar(vivos)) {
                        e.atacar(jugador);
                    }
                    if (jugador.getStats().getHpActual() <= 0) {
                        System.out.println("Cloud ha sido derrotado...");
                        combateActivo = false;
                        break;
                    }
                }
            }
        }
    }

    /**
     * Determina si un enemigo ataca según la probabilidad de ataque conjunto.
     * Con 2 enemigos: 50%, con 3 enemigos: 33%.
     * @param cantidadVivos cantidad de enemigos vivos
     * @return true si el enemigo ataca en este turno
     */
    private boolean debeAtacar(long cantidadVivos) {
        if (cantidadVivos == 2) return Math.random() < 0.5;
        if (cantidadVivos >= 3) return Math.random() < 0.33;
        return true;
    }

    /**
     * Permite al jugador usar magia ofensiva contra un enemigo.
     * @param jugador el jugador
     * @param objetivo el enemigo objetivo
     * @param sc el scanner
     * @param esCura si es un ataque de curación
     */
    private void usarMagia(Jugador jugador, Enemigo objetivo, Scanner sc, boolean esCura) {
        List<Elemento> elementosDisponibles = new ArrayList<>();
        for (Elemento e : Elemento.values()) {
            if (e != Elemento.CURA && jugador.getBusterSword().tieneMateria(e)) {
                elementosDisponibles.add(e);
            }
        }
        if (elementosDisponibles.isEmpty()) {
            System.out.println("No tienes materias mágicas equipadas.");
            return;
        }
        System.out.println("Elementos disponibles:");
        for (int i = 0; i < elementosDisponibles.size(); i++) {
            int n = jugador.getBusterSword().contarMateriasDeElemento(elementosDisponibles.get(i));
            int costo = 10 + (5 * n);
            System.out.println((i + 1) + ". " + elementosDisponibles.get(i) + " (n=" + n + ", MP=" + costo + ")");
        }
        System.out.print("¿Qué elemento usar? ");
        try {
            int idx = Integer.parseInt(sc.nextLine().trim()) - 1;
            if (idx < 0 || idx >= elementosDisponibles.size()) {
                System.out.println("Opción no válida.");
                return;
            }
            Elemento elemento = elementosDisponibles.get(idx);
            int dano = jugador.getBusterSword().calcularDanoMagico(elemento, objetivo);
            if (dano > 0 && objetivo != null) {
                objetivo.getStats().recibirDMG(dano);
                jugador.getBusterSword().cargarLimiteAlInfligirDano(dano);
                System.out.println("Cloud lanza " + elemento + " a " + objetivo.getNombre() + " por " + dano + " de daño!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada no válida.");
        }
    }

    /**
     * Permite al jugador curarse usando CURA si tiene la materia equipada.
     * @param jugador el jugador
     * @param sc el scanner
     */
    private void usarCura(Jugador jugador, Scanner sc) {
        if (!jugador.getBusterSword().tieneMateria(Elemento.CURA)) {
            System.out.println("No tienes la materia CURA equipada.");
            return;
        }
        int n = jugador.getBusterSword().contarMateriasDeElemento(Elemento.CURA);
        int costoMP = 10 + (5 * n);
        if (!jugador.getStats().consumirMP(costoMP)) {
            System.out.println("No tienes suficiente MP para CURA.");
            return;
        }
        int curacion = (int)(jugador.getStats().getMagia() * (1.0 + (0.5 * n)));
        jugador.getStats().restaurarHP(curacion);
        System.out.println("Cloud se cura por " + curacion + " HP! HP actual: " + jugador.getStats().getHpActual() + "/" + jugador.getStats().getHpMaximo());
    }

    /**
     * Permite al jugador seleccionar un enemigo vivo de la lista.
     * Si solo hay uno, se selecciona automáticamente.
     * @param enemigos lista de enemigos
     * @param sc el scanner
     * @return el enemigo seleccionado, o null si no hay vivos
     */
    private Enemigo seleccionarEnemigo(List<Enemigo> enemigos, Scanner sc) {
        List<Enemigo> vivos = new ArrayList<>();
        for (Enemigo e : enemigos) {
            if (!e.estaMuerto()) vivos.add(e);
        }
        if (vivos.isEmpty()) return null;
        if (vivos.size() == 1) return vivos.get(0);
        System.out.println("Selecciona objetivo:");
        for (int i = 0; i < vivos.size(); i++) {
            System.out.println((i + 1) + ". " + vivos.get(i).getNombre());
        }
        System.out.print("Objetivo: ");
        try {
            int idx = Integer.parseInt(sc.nextLine().trim()) - 1;
            if (idx >= 0 && idx < vivos.size()) return vivos.get(idx);
        } catch (NumberFormatException e) {}
        return vivos.get(0);
    }

    /**
     * El Sector 7 es siempre accesible para cualquier jugador.
     * @param jugador el jugador
     * @return siempre true
     */
    @Override
    public boolean validarAcceso(Jugador jugador) {
        return true;
    }

    // --- Getters y Setters ---

    public List<Mejora> getTiendaLocal() { return tiendaLocal; }
    public void setTiendaLocal(List<Mejora> tiendaLocal) { this.tiendaLocal = tiendaLocal; }
}
