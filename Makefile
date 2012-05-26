# top level makefile - recurses to lower level makefile(s) when necessary

include config.mk

default: build

build clean:
	${MAKE} -C src $@

run: build
	${JAVA} -cp bin SpaceInvaderViewer

.PHONY: build clean default run
