# Build Project
FROM gradle:jdk17-alpine AS BUILD_STAGE
COPY --chown=gradle:gradle . /home/gradle
RUN gradle assemble || return 1

# Extract jar in layers
FROM azul/zulu-openjdk-alpine:17-latest AS FINAL_STAGE

COPY --from=BUILD_STAGE /home/gradle/build/libs/quiz-service.jar quiz-service.jar

ENTRYPOINT ["java","-jar",  "-Xss512k", "-Xms512M", "-Xmx1024M", "-XX:MaxGCPauseMillis=500", "-XX:+UseG1GC", "-XX:+DisableExplicitGC", "-XX:SurvivorRatio=6", "-XX:MaxMetaspaceSize=256m", "-XX:+ParallelRefProcEnabled", "quiz-service.jar"]