mvn clean eclipse:clean eclipse:eclipse test install -DdownloadSources=true && \
cd blazdb-generated && \
mvn clean eclipse:clean eclipse:eclipse -DdownloadSources=true && \
cd ../ && \
cd tryout && \
mvn clean eclipse:clean eclipse:eclipse -DdownloadSources=true && \
cd ..
