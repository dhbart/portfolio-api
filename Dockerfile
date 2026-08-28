# =============================================================================
# Build Stage
# =============================================================================
FROM eclipse-temurin:25-jdk AS builder

WORKDIR /app

# -----------------------------------------------------------------------------
# Copy only Gradle files first to maximize Docker layer caching
# -----------------------------------------------------------------------------
COPY gradlew .
COPY gradle gradle
COPY settings.gradle .
COPY build.gradle .

RUN chmod +x gradlew

# Download Gradle distribution and project dependencies
RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew classes --no-daemon

# -----------------------------------------------------------------------------
# Copy application source
# -----------------------------------------------------------------------------
COPY src src

# If your project contains additional folders required during build
# (resources outside src, config files, etc.), copy them here.

# Build application
RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew bootJar --no-daemon

# =============================================================================
# Runtime Stage
# =============================================================================
FROM eclipse-temurin:25-jre

WORKDIR /app

# -----------------------------------------------------------------------------
# Create non-root user
# -----------------------------------------------------------------------------
RUN groupadd --system spring && \
    useradd --system --gid spring --create-home spring

# -----------------------------------------------------------------------------
# Copy application
# -----------------------------------------------------------------------------
COPY --from=builder /app/build/libs/*.jar app.jar

# Change ownership
RUN chown spring:spring app.jar

USER spring

EXPOSE 8080

# -----------------------------------------------------------------------------
# Healthcheck
# -----------------------------------------------------------------------------
HEALTHCHECK --interval=30s \
             --timeout=5s \
             --start-period=30s \
             --retries=3 \
CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# -----------------------------------------------------------------------------
# JVM options optimized for containers
# -----------------------------------------------------------------------------
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]