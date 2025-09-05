게임 퀴즈 프로젝트
==================

# 퀴즈

## 퀴즈 entity

```java
	@Id
	@Column(name="quiz_id")
	private Long quizId; // 퀴즈 ID - DB에서 관리하기 때문에 auto_increment 하지 않았음
	
	@Column
	private String question; /* 문제. 문장으로 나와야 하기 때문에 String(255글자 초과할 일이
	많지 않기 때문에 text가 아니라 그냥 varchar 사용)
	*/
	@Column
	private String answer; // 정답, 마찬가지 이유로 varchar 사용
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	private CategoryEntity category; // 카테고리는 별도의 테이블로 분리해놓음
	
	@Column
	private byte score; // 점수, 기본적으로 1점 혹은 2점이기 때문에 byte사용(안해도 상관 없음)
}
```

## 카테고리 entity

```java
	@Id
	private Integer categoryId; // 카테고리 ID - 마찬가지로 DB에서 관리하기 때문에 auto_increment 하지 않음

	@Column
	@Enumerated(EnumType.STRING)
	private Game gamename; // 게임 이름 - 게임 목록만 enum으로 만들어서 넣음
	
	@Column
	@Enumerated(EnumType.STRING)
	private Category category; // 카테고리 - 게임 별로 카테고리 만 만들어서 넣음
```

## 게임 결과 기록용 entity

```java
	@Id
	private Long userId; // 유저를 찾기 위한 userId 저장
	
	@Column
	private Integer lolWeeklyScore; // 게임 별 주간점수, 최대 점수 저장
	
	@Column
	private Integer lolMaxScore; 
	
	@Column
	private Integer bgWeeklyScore;
	
	@Column
	private Integer bgMaxScore;
	
	@Column
	private Integer scWeeklyScore;
	
	@Column
	private Integer scMaxScore;
	
	@Column
	private Integer msWeeklyScore;

	@Column
	private Integer msMaxScore;
	
	@Column
	private Integer loaWeeklyScore;

	@Column
	private Integer loaMaxScore;
	
	public int sum() {
		return this.getBgWeeklyScore() + this.getMsWeeklyScore() + this.getLolWeeklyScore() + this.getScWeeklyScore() + this.getLoaWeeklyScore();
	} // 주말에 게임 점수 정산 용 메서드
```

## 퀴즈 리포지토리

```java
	@Query(value = "SELECT * from quiz q where q.category_id = ? ORDER BY RAND() LIMIT 1", nativeQuery = true)
	QuizEntity findSomeOneByCategory(Integer categoryId); // 카테고리가 일치하는 퀴즈중 랜덤으로 하나 추출 
```

## 게임 플레이 횟수 제한 필터

```java
@Component
@RequiredArgsConstructor
public class GameFilter extends OncePerRequestFilter { // 요청 마다 한번 씩 필터링

	private final JwtService jwtService; // 유저 인식 용
	// 퀴즈 경로
	private static final String PREFIX = "/quiz"; // 요청 경로
	// 게임 별 횟수 제한
	private final int DAILY_LIMIT = 999; // 게임 별 요청 횟수 

	private final ConcurrentHashMap<String, Map<String, Integer>> attempts = new ConcurrentHashMap<>(); // 게임 별 요청횟수 저장

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {
		if (request.getRequestURI().startsWith(PREFIX)) { // 요청 경로가 PREFIX로 시작하는지 
			String jwt = jwtService.resolveToken(request); // 사용자가 누구인지
			String username = jwtService.getUsername(jwt); // 사용자의 유저이름이 뭔지
			String gameName = request.getRequestURI().substring(PREFIX.length() + 1).toUpperCase(); // 요청 게임이 어떤 게임인지
			if (StringUtils.hasText(username)) { // 유저이름이 빈 문자열이 아닌지
				int currentAttempts = attempts.computeIfAbsent(username, k -> new HashMap<>()) // 유저이름 없으면 유저이름을 key로 갖는 맵 생성
										.compute(gameName, (name, count) -> (count == null) ? 1 : count + 1); // 게임이름인 key가 있으면 카운트 추가, 없으면 카운트 1로 생성 
				if (currentAttempts > DAILY_LIMIT) { // 하루 제한보다 많으면 에러
					response.sendError(HttpServletResponse.SC_FORBIDDEN);
					return;
				}
			}
		}
		filterChain.doFilter(request, response); // 필터체인에 추가
	}
}

```

## 스케쥴러

```java
	@Scheduled(cron = "0 0 0 * * ?") // 매일 실행
	public ConcurrentHashMap<?, ?> dailyEvent() {
        ConcurrentHashMap<String, Map<String, Integer>> attempts = gameFilter.getAttempts();
        attempts.clear(); // 매일 필터 초기화
        return attempts;
	}
	
	@Scheduled(cron = "0 50 23 * * 0")
	public void weeklyEvent() {
		totalService.settlement(); // 매주 일요일 23시 50분에 실행
	}

```

## 서비스

```java
	public void settlement() {
		gameListRepository.findAll() // 모든 개인 게임 목록 가져옴
		.stream()
		.collect(Collectors.groupingBy(GameList::getUserId, 
					Collectors.summingInt(GameList::sum))) // 유저 아이디 별로 모든 주간 게임 결과 합으로 그룹화
		.forEach(
			(userId, sum) -> userRepository
				.findById(userId)
				.stream()
				.map(entity -> {
					entity.setPoint(entity.getPoint() + sum); // 포인트 할당
					entity.setLastWeekScore(sum); // 지난주 점수에 넣음
					entity.setTotalScore(entity.getTotalScore() + sum); // 점수 총합에 넣음
					userRepository.save(entity);
					return null;
					})
		.close()); // 스트림 닫음
	}
```
