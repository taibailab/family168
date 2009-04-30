call clean

javac Main.java
java Main createChildPom

call mvn dependency:tree -o > tree.txt

java Main createJarsTxt
