# ─────────────────────────────────────────────
#  Stage 1 – Compile all Java sources
# ─────────────────────────────────────────────
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app
COPY src ./src

RUN mkdir -p out && \
    find src -name "*.java" > sources.txt && \
    javac -source 17 -target 17 -d out @sources.txt

# ─────────────────────────────────────────────
#  Stage 2 – Lightweight JRE runtime
# ─────────────────────────────────────────────
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy compiled classes and static frontend
COPY --from=build /app/out ./out
COPY web ./web

EXPOSE 8080

CMD ["java", "-cp", "out", "com.skytrail.server.Server"]
