FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD build/libs/apache-ignite-nutshell-zk-ignite-node-1.0-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENV zkConnection='209.177.91.59:12181'
CMD [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]