mvn clean eclipse:clean eclipse:eclipse test install -DdownloadSources=true && \
cd blazdb-test-generated && \
mvn clean eclipse:clean eclipse:eclipse test -DdownloadSources=true && \
cd ../
