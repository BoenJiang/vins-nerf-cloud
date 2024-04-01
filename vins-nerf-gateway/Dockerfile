FROM openjdk:8-jre-slim
LABEL org.opencontainers.image.authors="Booooooen@xxx.com"

ENV encryptor_password=""

ENV PARAMS=""

ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ADD target/vins-nerf-gateway-*.jar /app.jar

ENTRYPOINT ["sh","-c","java -jar -Djasypt.encryptor.password=$encryptor_password $JAVA_OPTS /app.jar $PARAMS"]