JC = javac
JRUN = java
JFLAGS = -g
JAR = -cp ".:json.jar"

MAINCLASS = TocHW4

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $(JAR) $*.java

CLASSES = \
		  DataRequest.java \
		  JsonWebReader.java \
		  TocHW4.java

default: classes

classes: $(CLASSES:.java=.class)

test: default
	$(JRUN) $(JAR) $(MAINCLASS) http://www.datagarage.io/api/538447a07122e8a77dfe2d86

clean:
	$(RM) *.class
