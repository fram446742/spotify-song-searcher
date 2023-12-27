@echo off

echo Running mvn clean compile, mvn dependency:copy-dependencies -DoutputDirectory=target/modules, mvn package, mvn install...
mvn clean compile && mvn dependency:copy-dependencies -DoutputDirectory=target/modules && mvn package && mvn install && echo Completed process. && pause && echo Now running spotify-song-searcher-1.0-Beta.jar && java -jar target/bin/spotify-song-searcher-1.0-Beta.jar
