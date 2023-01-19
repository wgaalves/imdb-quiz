# Build Project
FROM gradle:jdk11-alpine AS BUILD_STAGE
COPY --chown=gradle:gradle . /home/gradle
RUN gradle assemble || return 1

# Extract jar in layers
FROM azul/zulu-openjdk-alpine:11.0.15 as BUILD_EXTRACT

COPY --from=BUILD_STAGE /home/gradle/build/libs/gateway.jar gateway.jar
RUN java -Djarmode=layertools -jar gateway.jar extract

# Build Image
FROM azul/zulu-openjdk-alpine:11.0.15
COPY --from=BUILD_EXTRACT dependencies/ ./
COPY --from=BUILD_EXTRACT spring-boot-loader/ ./
RUN true
COPY --from=BUILD_EXTRACT snapshot-dependencies/ ./
RUN true
COPY --from=BUILD_EXTRACT application/ ./


ENTRYPOINT ["java",  "-Xss512k", "-Xms512M", "-Xmx1024M", "-XX:MaxGCPauseMillis=500", "-XX:+UseG1GC", "-XX:+DisableExplicitGC", "-XX:SurvivorRatio=6", "-XX:MaxMetaspaceSize=256m", "-XX:+ParallelRefProcEnabled","org.springframework.boot.loader.JarLauncher"]
