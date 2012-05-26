# config for SpaceInvaders makefile

# uncomment for verbose build output
#NOISY = true

# set java implementation
# note:
# 	- OpenJDK fails to rebuild all related class files after modifications
# 	- a 'make clean' is done before building to accommodate for this
JAVA_IMP = OracleJDK
#JAVA_IMP = OpenJDK

ifeq ($(JAVA_IMP), OracleJDK)
    JAVA = /opt/oracle/jdk/bin/java
    JAVAC = /opt/oracle/jdk/bin/javac
else
    JAVA = java
    JAVAC = javac
endif

ifndef NOISY
.SILENT:
endif
