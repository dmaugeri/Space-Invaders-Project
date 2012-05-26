include ../config.mk

SRC = AlienCluster.java \
      Alien.java \
      AlienProjectile.java \
      AlienRectangleCluster.java \
      BoundingBox.java \
      EndScreen.java \
      Entity.java \
      GameBoard.java \
      Input.java \
      LevelGenerator.java \
      Level.java \
      Projectile.java \
      Screen.java \
      Shape2D.java \
      Ship.java \
      ShipProjectile.java \
      SoundEffect.java \
      SpaceInvaderViewer.java \
      TitleScreen.java \
      Util.java
BIN = ${SRC:.java=.class}

default: build

build: ../bin/SpaceInvaderViewer.class

../bin/SpaceInvaderViewer.class: ${SRC} makefile
	mkdir -p ../bin
ifeq ($(JAVA_IMP), OpenJDK)
	${MAKE} clean
endif
	${JAVAC} -d ../bin SpaceInvaderViewer.java

clean:
	-rm ../bin/*.class 2> /dev/null

run: build
	${MAKE} -C .. $@

.PHONY: build clean default run
