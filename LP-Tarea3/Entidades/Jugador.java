package Entidades;

import Componentes.Elemento;
import Componentes.Estadisticas;
import Componentes.Materia;
import Componentes.Vulnerable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Clase principal que representa al jugador Cloud.
 * Gestiona el nivel, experiencia, chatarra e inventario (mochila).
 * Contiene la clase anidada Arma que gestiona las materias equipadas.
 */
public class Jugador {

    private String nombre = "Cloud";
    private int nivel;
    private int expActual;
    private int chatarra;
    private List<Materia> mochila;
    private Estadisticas stats;
    public Arma busterSword;

    private static final Random rand = new Random();

    /**
     * Constructor de Cloud. Inicializa en Nivel 1 con estadísticas base.
     * HP Máx: 200, MP Máx: 50, Fuerza: 15, Magia: 15, chatarra: 0, expActual: 0.
     */
    public Jugador() {
        this.nivel = 1;
        this.expActual = 0;
        this.chatarra = 0;
        this.mochila = new ArrayList<>();
        this.stats = new Estadisticas();
        this.busterSword = new Arma();
    }

    /**
     * Recibe XP y verifica si sube de nivel.
     * XP necesaria para subir: 10 x Nivel actual.
     * @param xp cantidad de XP obtenida
     */
    public void recibirXP(int xp) {
        this.expActual += xp;
        int xpNecesaria = 10 * nivel;
        while (expActual >= xpNecesaria) {
            expActual -= xpNecesaria;
            subirNivel();
            xpNecesaria = 10 * nivel;
        }
    }

    /**
     * Sube un nivel e incrementa las estadísticas base automáticamente.
     * +10 HP Máximo, +5 MP Máximo, +4 Fuerza, +6 Magia.
     */
    private void subirNivel() {
        nivel++;
        stats.setHpMaximo(stats.getHpMaximo() + 10);
        stats.setHpActual(stats.getHpActual() + 10); // También recupera algo de HP
        stats.setMpMaximo(stats.getMpMaximo() + 5);
        stats.setMpActual(stats.getMpActual() + 5);
        stats.setFuerza(stats.getFuerza() + 4);
        stats.setMagia(stats.getMagia() + 6);
        System.out.println("*** SUBISTE DE NIVEL! Ahora eres Nivel " + nivel + " ***");
        System.out.println("  Stats: " + stats);
    }

    /**
     * Recibe la recompensa de chatarra de un enemigo derrotado.
     * @param enemigo el enemigo que suelta la chatarra
     */
    public void giveChatarraRecompensa(Enemigo enemigo) {
        this.chatarra += enemigo.getChatarraRecompensa();
    }

    /**
     * Mueve una Materia de la mochila al Arma (equipar).
     * @param materia la materia a equipar
     * @return true si se pudo equipar, false si el Arma está llena o no está en mochila
     */
    public boolean equiparMateria(Materia materia) {
        if (!mochila.contains(materia)) {
            System.out.println("Esa materia no está en la mochila.");
            return false;
        }
        if (busterSword.equiparMateria(materia)) {
            mochila.remove(materia);
            return true;
        }
        return false;
    }

    /**
     * Mueve una Materia del Arma a la mochila (desequipar).
     * @param materia la materia a desequipar
     * @return true si se pudo desequipar
     */
    public boolean desequiparMateria(Materia materia) {
        if (busterSword.desequiparMateria(materia)) {
            mochila.add(materia);
            return true;
        }
        return false;
    }

    /**
     * Aplica la penalización de derrota: pierde toda la chatarra y vacía la mochila.
     * Las materias del Arma quedan intactas.
     */
    public void aplicarPenalidadDerrota() {
        this.chatarra = 0;
        this.mochila.clear();
        this.stats.restaurarHPCompleto();
        this.stats.restaurarMPCompleto();
        System.out.println("Cloud ha sido derrotado y rescatado al Sector 7.");
        System.out.println("Perdiste toda tu chatarra y las materias de la mochila.");
        System.out.println("Las materias equipadas en el Arma están a salvo.");
    }

    /**
     * Restaura HP y MP al máximo (usado al regresar al Sector 7).
     */
    public void restaurarEstado() {
        stats.restaurarHPCompleto();
        stats.restaurarMPCompleto();
    }

    // --- Getters y Setters ---

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getNivel() { return nivel; }
    public void setNivel(int nivel) { this.nivel = nivel; }

    public int getExpActual() { return expActual; }
    public void setExpActual(int expActual) { this.expActual = expActual; }

    public int getChatarra() { return chatarra; }
    public void setChatarra(int chatarra) { this.chatarra = chatarra; }

    public List<Materia> getMochila() { return mochila; }
    public void setMochila(List<Materia> mochila) { this.mochila = mochila; }

    public Estadisticas getStats() { return stats; }
    public void setStats(Estadisticas stats) { this.stats = stats; }

    public Arma getBusterSword() { return busterSword; }
    public void setBusterSword(Arma busterSword) { this.busterSword = busterSword; }

    /**
     * Muestra el estado completo del jugador en consola.
     */
    public void mostrarEstado() {
        int xpNecesaria = 10 * nivel;
        System.out.println("=== ESTADO DE CLOUD ===");
        System.out.println("Nivel: " + nivel + " | XP: " + expActual + "/" + xpNecesaria);
        System.out.println(stats.toString());
        System.out.println("Chatarra: " + chatarra);
        System.out.println("Barra Límite: " + busterSword.getCargaLimite() + "/100");
        System.out.println("Materias en Arma (" + busterSword.getMateriasEquipadas().size() + "/5): " + busterSword.getMateriasEquipadas());
        System.out.println("Mochila (" + mochila.size() + " materias): " + mochila);
    }

    // =========================================================
    //   CLASE ANIDADA: Arma (Buster Sword)
    // =========================================================

    /**
     * Clase anidada que representa el Arma de Cloud (Buster Sword).
     * Gestiona hasta 5 ranuras de materias y la Barra de Límite (0-100).
     * Accede directamente a las estadísticas de Cloud para calcular daño.
     */
    public class Arma {

        private String nombre = "Buster Sword";
        private List<Materia> materiasEquipadas;
        private int cargaLimite; // 0 a 100

        /**
         * Constructor del Arma. Inicializa sin materias y con Límite en 0.
         */
        public Arma() {
            this.materiasEquipadas = new ArrayList<>();
            this.cargaLimite = 0;
        }

        /**
         * Equipa una Materia en una ranura libre del Arma.
         * @param materia la materia a equipar
         * @return true si se equipó, false si no hay espacio (máx 5 ranuras)
         */
        public boolean equiparMateria(Materia materia) {
            if (materiasEquipadas.size() >= 5) {
                System.out.println("El Arma ya tiene 5 materias equipadas (máximo).");
                return false;
            }
            materiasEquipadas.add(materia);
            System.out.println(materia.getNombre() + " equipada en " + nombre + ".");
            return true;
        }

        /**
         * Desequipa una Materia del Arma.
         * @param materia la materia a desequipar
         * @return true si se encontró y desequipó, false si no estaba equipada
         */
        public boolean desequiparMateria(Materia materia) {
            if (materiasEquipadas.remove(materia)) {
                System.out.println(materia.getNombre() + " desequipada.");
                return true;
            }
            System.out.println("Esa materia no está equipada.");
            return false;
        }

        /**
         * Calcula el daño físico de Cloud: floor(Fuerza x 1.25).
         * @return daño físico calculado
         */
        public int calcularDanoFisico() {
            return (int)(stats.getFuerza() * 1.25);
        }

        /**
         * Calcula el daño mágico de Cloud según el elemento y la cantidad de materias del mismo tipo.
         * Daño = floor(Magia x (1.0 + (0.5 x n))) donde n = número de materias del elemento.
         * Costo MP = 10 + (5 x n).
         * Si el elemento es CURA, el daño se aplica como curación a Cloud.
         * @param elemento el elemento mágico a utilizar
         * @param enemigo el enemigo objetivo (puede ser null si es CURA)
         * @return daño/curación calculado, o -1 si no hay MP suficiente o no hay materias del elemento
         */
        public int calcularDanoMagico(Elemento elemento, Enemigo enemigo) {
            int n = contarMateriasDeElemento(elemento);
            if (n == 0) {
                System.out.println("No tienes materias de ese elemento equipadas.");
                return -1;
            }
            int costoMP = 10 + (5 * n);
            if (!stats.consumirMP(costoMP)) {
                System.out.println("No tienes suficiente MP. Necesitas " + costoMP + " MP.");
                return -1;
            }
            int danoBase = (int)(stats.getMagia() * (1.0 + (0.5 * n)));

            // Aplicar multiplicador elemental si el enemigo implementa Vulnerable
            if (enemigo instanceof Vulnerable && elemento != Elemento.CURA) {
                Vulnerable v = (Vulnerable) enemigo;
                double multiplicador = v.evaluarDebilidad(elemento);
                danoBase = (int)(danoBase * multiplicador);
                if (multiplicador == 2.0) System.out.println("Es muy efectivo! (x2.0)");
                else if (multiplicador == 0.5) System.out.println("No es muy efectivo... (x0.5)");
                else if (multiplicador == 0.0) System.out.println(enemigo.getNombre() + " es inmune a ese elemento!");
            }
            return danoBase;
        }

        /**
         * Calcula el daño del Ataque Límite: Fuerza x 5.
         * No consume MP y es un ataque físico devastador.
         * Reinicia la Barra de Límite a 0 tras ejecutarse.
         * @return daño calculado del Límite
         */
        public int calcularDanoLimite() {
            int dano = stats.getFuerza() * 5;
            cargaLimite = 0;
            System.out.println("*** OMNISLASH! Ataque Límite de Cloud! ***");
            return dano;
        }

        /**
         * Carga la Barra de Límite al recibir daño: += floor(daño / 2).
         * @param danoRecibido el daño recibido
         */
        public void cargarLimiteAlRecibirDano(int danoRecibido) {
            cargaLimite += (danoRecibido / 2);
            if (cargaLimite > 100) cargaLimite = 100;
        }

        /**
         * Carga la Barra de Límite al infligir daño: += floor(daño / 5).
         * @param danoInfligido el daño infligido
         */
        public void cargarLimiteAlInfligirDano(int danoInfligido) {
            cargaLimite += (danoInfligido / 5);
            if (cargaLimite > 100) cargaLimite = 100;
        }

        /**
         * Verifica si la Barra de Límite está llena (>= 100).
         * @return true si el Límite está disponible
         */
        public boolean limiteDisponible() {
            return cargaLimite >= 100;
        }

        /**
         * Cuenta cuántas materias del elemento indicado están equipadas.
         * @param elemento el elemento a contar
         * @return cantidad de materias de ese elemento
         */
        public int contarMateriasDeElemento(Elemento elemento) {
            int count = 0;
            for (Materia m : materiasEquipadas) {
                if (m.getElemento() == elemento) count++;
            }
            return count;
        }

        /**
         * Verifica si hay alguna materia de un elemento específico equipada.
         * @param elemento el elemento a verificar
         * @return true si hay al menos una materia de ese elemento
         */
        public boolean tieneMateria(Elemento elemento) {
            return contarMateriasDeElemento(elemento) > 0;
        }

        // --- Getters y Setters ---

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public List<Materia> getMateriasEquipadas() { return materiasEquipadas; }
        public void setMateriasEquipadas(List<Materia> materiasEquipadas) { this.materiasEquipadas = materiasEquipadas; }

        public int getCargaLimite() { return cargaLimite; }
        public void setCargaLimite(int cargaLimite) { this.cargaLimite = cargaLimite; }
    }
}
