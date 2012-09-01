
all:
	javac -d ./bin/ -cp ./src/:./lib/commons-exec-1.1.jar:./lib/log4j-1.2.16.jar -sourcepath ./src/ ./src/*.java
	fsc -unchecked -d ./bin/ -cp ./lib/commons-exec-1.1.jar:./lib/akka-actor-2.0.1.jar:./src/:./src/edWood/config/:./lib/log4j-1.2.16.jar -sourcepath ./src/:. ./src/*.scala ./src/*.java

clean: 
	rm -rf ./bin/* edWood.log output.txt specialCharTest.txt

cleanCustom: 
	rm -rf ./custom/bin/*

todos:
	grep -nHGo '//T[oO]D[oO].*' ./src/*

prints:
	grep -nHGo 'println' ./src/*

ed:
	java -cp ./bin/:./custom/bin/:./lib/commons-exec-1.1.jar:./lib/scala-library.jar:./lib/akka-actor-2.0.1.jar:./lib/log4j-1.2.16.jar edWood.EdWood config.xml

java:
	javac -d ./bin/ -cp ./src/:./lib/commons-exec-1.1.jar:./lib/log4j-1.2.16.jar -sourcepath ./src/ ./src/*.java

customAdapters:
	javac -d ./custom/bin/ -cp ./src/:./lib/scala-library.jar:./bin/:./lib/log4j-1.2.16.jar -sourcepath ./custom/src/ ./custom/src/*.java

