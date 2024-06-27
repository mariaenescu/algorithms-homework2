build: Numarare.java Trenuri.java Drumuri.java
	javac Numarare.java Trenuri.java Drumuri.java

run-p1: Numarare.class
	java Numarare
run-p2: Trenuri.java
	java Trenuri
run-p3: Drumuri.java
	java Drumuri
clean: *.class
	rm *.class