FROM amazoncorretto:17.0.6
MAINTAINER zihluwang

ENV TZ Asia/Shanghai
ENV APP_KEY ''
ENV PROFILES 'prod'

WORKDIR /tmp
ADD build/libs/vocab-buddy-backend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["/bin/sh", "-c", "java -jar -Dspring.profiles.active=$PROFILES /tmp/app.jar --mpw.key=$APP_KEY"]