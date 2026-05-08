=========================================
  INF-253 Lenguajes de Programación
  Tarea 3 2026-1: La Amenaza de Sephiroth
=========================================

Nombre  : [TU NOMBRE COMPLETO AQUÍ]
Rol     : [TU ROL AQUÍ]

=========================================
DESCRIPCIÓN
=========================================
Prototipo de juego de rol (RPG) por turnos en Java.
El jugador controla a Cloud, un mercenario que debe
derrotar a Sephiroth para salvar el planeta.

=========================================
REQUISITOS
=========================================
- Java JDK 11 o superior
- Sistema operativo: Linux / macOS / Windows
- Make (para usar el Makefile)

=========================================
COMPILACIÓN Y EJECUCIÓN
=========================================

Para compilar el proyecto:
    make

Para compilar y ejecutar el juego:
    make run

Para limpiar los archivos .class generados:
    make clean

Compilación manual (sin make):
    javac -encoding UTF-8 Main.java Componentes/*.java Entidades/*.java Mapa/*.java
    java Main

=========================================
ESTRUCTURA DEL PROYECTO
=========================================

Tarea3/
├── Main.java                    (Punto de entrada del juego)
├── makefile                     (Archivo de compilación)
├── README.txt                   (Este archivo)
├── Componentes/
│   ├── Elemento.java            (Enum: FUEGO, HIELO, RAYO, CURA, FISICO)
│   ├── TipoStat.java            (Enum: HP_MAX, MP_MAX, FUERZA)
│   ├── Vulnerable.java          (Interfaz para debilidades elementales)
│   ├── Estadisticas.java        (HP, MP, Fuerza, Magia del jugador/enemigo)
│   ├── Materia.java             (Esfera mágica con elemento)
│   └── Mejora.java              (Mejora permanente de la tienda)
├── Entidades/
│   ├── Enemigo.java             (Clase abstracta base de enemigos)
│   ├── EnemigoSimulador.java    (Enemigo del simulador del Sector 7)
│   ├── EnemigoSalvaje.java      (Enemigo de Gongaga, implementa Vulnerable)
│   ├── Sephiroth.java           (Jefe Final con contador SuperNova)
│   └── Jugador.java             (Cloud + clase anidada Arma/BusterSword)
└── Mapa/
    ├── Zona.java                (Clase abstracta base de zonas)
    ├── Sector7.java             (Zona segura: simulador y tienda)
    ├── Gongaga.java             (Zona salvaje: exploración y emboscadas)
    ├── NucleoPlaneta.java       (Zona jefe final: combate contra Sephiroth)
    └── Mapa.java                (Menú principal de navegación entre zonas)

=========================================
INSTRUCCIONES DE JUEGO
=========================================

1. El juego inicia en el Sector 7 (zona segura).
2. Desde el mapa, elige la zona a visitar.
3. En el Sector 7:
   - Simulador de Combate: entrena contra soldados sin riesgo.
   - Tienda de Chatarra: compra mejoras permanentes con chatarra.
   - Gestionar Materias: equipa/desequipa materias en tu Arma.
4. En Gongaga (Nivel 5+):
   - 30% de encontrar una Materia aleatoria.
   - 70% de sufrir una emboscada de 1-3 enemigos.
   - Si Cloud muere: pierde chatarra y materias de la mochila.
5. En el Núcleo del Planeta (Nivel 20+, 2 materias equipadas):
   - Combate directo contra Sephiroth.
   - No se puede huir. ¡Derrótalo antes de que llegue al turno 10!

=========================================
COMBATE
=========================================
Durante el combate tienes las siguientes acciones:
  1. Atacar (Físico): Daño = floor(Fuerza x 1.25)
  2. Magia: Daño = floor(Magia x (1.0 + 0.5*n)), Costo MP = 10 + 5*n
  3. Curarse (CURA): Requiere materia CURA equipada
  4. LÍMITE: Disponible cuando la barra llega a 100. Daño = Fuerza x 5
  5. Huir (50% éxito, solo en zonas de peligro)

Barra de Límite:
  - Se carga al recibir daño: += floor(daño recibido / 2)
  - Se carga al infligir daño: += floor(daño infligido / 5)
  - Al llegar a 100, se puede usar el LÍMITE (se vacía al usarlo)
  - El Límite de Cloud reinicia el contador de SuperNova de Sephiroth

=========================================
