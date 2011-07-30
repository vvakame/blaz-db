mvn clean eclipse:clean eclipse:eclipse test install
cd blazdb-sqlite
mvn clean eclipse:clean eclipse:eclipse test install
cd ../
cd blazdb-generated
mvn clean eclipse:clean eclipse:eclipse
cd ../

