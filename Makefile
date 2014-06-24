JC = javac
JRUN = java
JFLAGS = -g
JAR = -cp ".:json.jar"

MAINCLASS = TocHw4

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $(JAR) $*.java

CLASSES = \
		  DataRequest.java \
		  JsonWebReader.java \
		  TocHw4.java

default: classes

classes: $(CLASSES:.java=.class)

test: default
	$(JRUN) $(JAR) $(MAINCLASS) http://www.datagarage.io/api/538447a07122e8a77dfe2d86

test2: default
	$(JRUN) $(JAR) $(MAINCLASS) http://www.datagarage.io/api/5384489ae7259bb37d9238d8

test3: default
	$(JRUN) $(JAR) $(MAINCLASS) http://www.datagarage.io/api/5385b69de7259bb37d925971

test4: default
	$(JRUN) $(JAR) $(MAINCLASS) http://www.datagarage.io/api/5385b858e7259bb37d926912

clean:
	$(RM) *.class
