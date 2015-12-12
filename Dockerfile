FROM java:8-jre
MAINTAINER Jonathan Como <jonathan.como@gmail.com>

VOLUME /cfg
COPY foodie.yml /cfg/foodie.yml

COPY target/foodie-standalone.jar /app/foodie-standalone.jar

WORKDIR /app

CMD ["java", "-jar", "foodie-standalone.jar", "server", "/cfg/foodie.yml"]
