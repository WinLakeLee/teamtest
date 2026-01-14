# 게임 퀴즈 프로젝트

# 구현 기능

## 👤 유저

- 로그인
- 유저 정보
- 로그아웃
- 회원 가입
- 회원 탈퇴
- 회원 정보 수정

## 🏬 상점

- 상품 구매
- 상품 환불
- 상품 적용
- 적용 아이템 변경
- 아이템 조회

## 🏆 랭크

- 게임 플레이 기록 저장
- 게임 기록 확인
- 순위 확인

## 🎮 게임

- 게임 플레이
- 게임 횟수 제한 (3회)
- 게임 결과 초기화
- 게임 결과 저장

# 🖥️ 백엔드

## ⚙️ 프레임워크

- Spring Boot

## 📦 의존성

- Gson
- jjwt
- spring-boot-starter-data-jpa
- spring-boot-starter-web
- spring-boot-starter-security
- jjwt-api
- Lombok

## 🔧 개발 툴

- spring-boot-devtools

## 🗄️ 데이터베이스

- MySQL

 # 🖥️ 프론트엔드

 ## 🔧 개발 툴

 - React

 ## 📦 라이브러리
 
 - Axios



	List<GameList> findAllByOrderByLolWeeklyScoreDesc();
	List<GameList> findAllByOrderByBgWeeklyScoreDesc();
	List<GameList> findAllByOrderByScWeeklyScoreDesc();
	List<GameList> findAllByOrderByMsWeeklyScoreDesc();
	List<GameList> findAllByOrderByLoaWeeklyScoreDesc();
