# Multi-stage build를 사용하여 최적화된 Docker 이미지 생성
FROM openjdk:21-jdk-alpine as builder

# 작업 디렉토리 설정
WORKDIR /app

# Gradle 래퍼와 의존성 파일을 먼저 복사 (레이어 캐싱 최적화)
COPY gradlew build.gradle settings.gradle ./
COPY gradle gradle

# Gradle 의존성 다운로드 (별도 레이어로 분리하여 캐싱 최적화)
RUN ./gradlew dependencies --no-daemon

# 소스 코드 복사
COPY src src

# 애플리케이션 빌드 (테스트 제외하고 빌드)
RUN ./gradlew bootJar --no-daemon -x test

# 실행용 이미지 생성
FROM openjdk:21-jre-alpine

# 애플리케이션 실행을 위한 사용자 생성
RUN addgroup --system spring && adduser --system spring --ingroup spring

# 작업 디렉토리 설정
WORKDIR /app

# 빌드된 JAR 파일을 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 애플리케이션 사용자로 권한 변경
RUN chown spring:spring /app/app.jar
USER spring:spring

# 서버 포트 노출
EXPOSE 8080

# JVM 옵션 설정 (메모리 최적화 및 컨테이너 환경 고려)
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:+UseG1GC -XX:+UseStringDeduplication"

# 애플리케이션 실행
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"] 