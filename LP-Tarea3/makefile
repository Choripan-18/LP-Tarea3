JAVAC = javac
JAVA  = java
SRC   = Main.java \
        Componentes/Elemento.java \
        Componentes/TipoStat.java \
        Componentes/Vulnerable.java \
        Componentes/Estadisticas.java \
        Componentes/Materia.java \
        Componentes/Mejora.java \
        Entidades/Enemigo.java \
        Entidades/EnemigoSimulador.java \
        Entidades/EnemigoSalvaje.java \
        Entidades/Sephiroth.java \
        Entidades/Jugador.java \
        Mapa/Zona.java \
        Mapa/Sector7.java \
        Mapa/Gongaga.java \
        Mapa/NucleoPlaneta.java \
        Mapa/Mapa.java

FLAGS = -encoding UTF-8

.PHONY: all run clean

all:
	$(JAVAC) $(FLAGS) $(SRC)

run: all
	$(JAVA) Main

clean:
	rm -f *.class Componentes/*.class Entidades/*.class Mapa/*.class Entidades/Jugador\$$Arma.class
