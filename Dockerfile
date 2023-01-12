#FROM maven:3.8.7-openjdk-18-slim AS build
#COPY pom.xml /NintendoSwitchDiscountsBot/pom.xml
#RUN mvn -f /NintendoSwitchDiscountsBot/pom.xml dependency:go-offline
##
#COPY src /NintendoSwitchDiscountsBot/src
#RUN mvn -f /NintendoSwitchDiscountsBot/pom.xml -DskipTests clean install
##
#FROM openjdk:17-alpine
#COPY --from=build /NintendoSwitchDiscountsBot/target/NintendoSwitchDiscountsBot-0.0.1-SNAPSHOT.jar NintendoSwitchDiscountsBot-0.0.1-SNAPSHOT.jar
#ENTRYPOINT ["java","-jar","NintendoSwitchDiscountsBot-0.0.1-SNAPSHOT.jar"]

FROM openjdk:17-alpine
COPY target/NintendoSwitchDiscountsBot-0.0.1-SNAPSHOT.jar NintendoSwitchDiscountsBot-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","NintendoSwitchDiscountsBot-0.0.1-SNAPSHOT.jar"]
