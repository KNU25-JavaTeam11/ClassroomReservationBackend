# ClassroomReservationBackend

## 소개
경북대학교 컴퓨터학부 강의실/시설물 예약 시스템의 백엔드 서버입니다.  
Spring Boot, JPA, MySQL, Spring Security 기반으로 개발되었습니다.

## 주요 기능
- 회원가입/로그인 (아이디, 비밀번호 기반)
- 강의실/시설물 목록 및 상세 조회
- 예약 생성/조회/취소

## 실행 방법

1. MySQL에 `classroom_reservation` 데이터베이스를 생성하세요.
2. `src/main/resources/application.yml`에서 DB 계정 정보를 입력하세요.
3. 아래 명령어로 서버를 실행하세요.
   ```bash
   ./gradlew build
   ./gradlew bootRun
   ```
4. API는 기본적으로 `http://localhost:8080`에서 동작합니다.

## API 예시

- 회원가입: `POST /api/auth/register`
- 로그인: `POST /api/auth/login`
- 강의실 목록: `GET /api/rooms`
- 예약 생성: `POST /api/reservations`

## 개발 환경
- Java 17 이상
- Spring Boot 3.x
- MySQL 8.x

## 기여 방법
- dev 브랜치에서 기능별 브랜치를 생성해 작업 후 Pull Request를 올려주세요.