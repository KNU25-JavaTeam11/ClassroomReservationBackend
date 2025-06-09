# 1. Java 21 빌드 환경
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# Gradle Wrapper 및 의존성 파일 복사
COPY build.gradle settings.gradle ./
COPY gradle ./gradle

# 의존성 미리 다운로드
RUN ./gradlew dependencies || true

# 전체 소스 복사
COPY . .

# jar 빌드 (테스트 제외)
RUN ./gradlew clean build -x test

# 2. 실제 실행 환경 (더 가벼운 Alpine JRE)
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# 빌드된 jar 복사
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
