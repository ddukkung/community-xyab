# XYAB
> 닌텐도 스위치 유저 커뮤니티 웹 서비스

<br>

## 1. 개발 기간 & 참여 인원
* 2022년 3월 21일 ~ 4월 2일
* 개인 프로젝트

<br>

## 2. 사용 기술
* Java 11
* Spring Boot 2.6.4
* Spring Data JPA
* Spring Security
* MySQL 5.7
* Thymeleaf
* Ajax, BootStrap   

<br>

## 3. ERD 설계
![xyab_db](https://user-images.githubusercontent.com/88926356/163223202-938e57fb-de0b-411c-98b2-3233eba83ca0.png)  

<br>

## 4. 핵심 기능
XYAB는 닌텐도 스위치 유저를 위한 커뮤니티로, 회원 가입을 하여 자신의 글을 작성하거나 다른 사람의 글에 댓글을 남길 수 있습니다.  
  
### 로직 (예시)
```java
컨트롤러단
@PutMapping("/api/v1/user")
public Long update(@RequestBody User user, @AuthenticationPrincipal PrincipalDetail principalDetail) {
    return userService.update(user, principalDetail);
}

서비스단
@Transactional
public Long update(User user, @AuthenticationPrincipal PrincipalDetail principalDetail) {
    User userEntity = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다. id=" + user.getId()));
    userEntity.update(encoder.encode(user.getPassword()), user.getNickname());
    principalDetail.setUser(userEntity);
    return userEntity.getId();
}
```
* Spring MVC 패턴으로 구현했습니다.
* 데이터 요청 및 응답에는 DTO 클래스를 만들어 사용하고, Service에서는 자동 커밋 및 데이터 정합성을 지키기 위해 @Transactional을 사용합니다.

<br>

### 회원 정보 수정
```java
@PutMapping("/api/v1/user")
public Long update(@RequestBody User user, @AuthenticationPrincipal PrincipalDetail principalDetail) {
    return userService.update(user, principalDetail);
}
```
* `@AuthenticationPrincipal`을 통해 로그인한 사용자의 정보를 받아와 UserService의 update() 메소드에 입력받은 비밀번호, 닉네임이 담긴 User 객체와 PrincipalDetail 객체를 넘겨줌

<br>

```java
@Transactional
public Long update(User user, @AuthenticationPrincipal PrincipalDetail principalDetail) {
    User userEntity = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다. id=" + user.getId()));
    userEntity.update(encoder.encode(user.getPassword()), user.getNickname());
    principalDetail.setUser(userEntity);
    return userEntity.getId();
}
```
* Service에서는 id로 User 테이블에서 조회하여 나온 유저 객체를 영속화 시킨다. 📌 [service](https://github.com/ddukkung/xyab/blob/33647187ad4904ec54aad3f4e25f1685f886dab1/src/main/java/community/xyab/service/UserService.java#L32)
    * JPA를 사용하기 때문에 update 쿼리를 보내지 않아도 캐시에 있는 데이터의 변경이 감지되면 자동으로 update 된다. 그러므로 User 객체의 update() 메소드를 사용해 입력받은 데이터로 변경시킨다. 
    * PrincipalDetail에도 setUser()를 하여 변경된 정보가 반영되도록 한다.

<br>

### 회원 탈퇴
```java
// 회원 탈퇴 시 게시글 모두 삭제
@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Board> boardList = new ArrayList<>();

// 회원 탈퇴 시 댓글 모두 삭제
@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Reply> replyList = new ArrayList<>();
```
* User 도메인의 boardList와 replyList는 OneToMany를, Board와 Reply의 user에는 ManyToOne를 주어 양방향 매핑 
* boardList와 replyList에는 CascadeType.ALL, orphanRemoval = true 를 주어 유저가 회원 탈퇴할 시 해당 유저가 작성한 게시글과 댓글이 모두 삭제됨

<br>

```java
@Transactional
public void delete(UserDeleteRequestDto userDeleteRequestDto) {
    User user = userRepository.findById(userDeleteRequestDto.getId()).orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

    if (encoder.matches(userDeleteRequestDto.getPassword(), user.getPassword())) {
        userRepository.deleteById(userDeleteRequestDto.getId());
        SecurityContextHolder.clearContext();
    } else {
        throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
    }
}
```
* service에서 입력받은 비밀번호가 DB에 저장된 비밀번호와 일치할 경우 회원 탈퇴 진행. 📌 [service](https://github.com/ddukkung/xyab/blob/2edc61d129af72fca78667a45786f220ab6c6d70/src/main/java/community/xyab/service/UserService.java#L40)
  * `BCryptPasswordEncoder`의 `matches()` 메소드를 통해 입력받은 비밀번호를 인코딩해 비교
  * `SecurityContextHolder.clearContext();`를 추가하여 로그아웃 처리

<br>

### 페이지네이션, 검색
```java
@GetMapping("/")
public String index(Model model,
                    @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                    @RequestParam(required = false, defaultValue = "")String search) {
    Page<Board> boards = boardService.findByTitleContainingOrContentContaining(search, search, pageable);

    int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
    int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);

    model.addAttribute("startPage", startPage);
    model.addAttribute("endPage", endPage);
    model.addAttribute("boards", boards);

    return "index";
}
```
* JPA의 `Pageable` 이용
* `@PageableDefault`를 통해 페이지의 size와 정렬방식, 정렬 기준을 정한다. 한 페이지에 5개씩, 최신 글이 먼저 보이도록 내림차순으로 정렬한다.
* 검색은 url 뒤에 쿼리 파라미터로 검색어가 붙어서 오기 때문에 `@RequestParam`으로 받는다. `required = false` 로 검색어가 입력되지 않아도 괜찮도록 처리. `defaultValue`를 ""로 하여 검색어가 입력되지 않으면 search의 값은 ""이 된다.
* `findByTitleContainingOrContentContaining`은 JPA에서 containing은 무엇을 포함한다는 LIKE문과 같으므로, 검색어가 title이나 content에 존재하는지 조회한다.

<br>

```java
<!-- Pagination -->
<nav aria-label="Page navigation example">
    <ul class="pagination">
        <li class="page-item" th:classappend="${1 == boards.pageable.pageNumber + 1} ? 'disable' : '' ">
            <a class="page-link" th:href="@{/(page=${boards.pageable.pageNumber - 1}, search=${param.search})}">Prev</a>
        </li>
        <li class="page-item" th:classappend="${i == boards.pageable.pageNumber + 1} ? 'active' : '' " th:each="i : ${#numbers.sequence(startPage, endPage)}">
            <a class="page-link" th:href="@{/(page=${i - 1}, search=${param.search})}" th:text="${i}">1</a>
        </li>
        <li class="page-item" th:classappend="${boards.totalPages == boards.pageable.pageNumber + 1} ? 'disable' : '' ">
            <a class="page-link" th:href="@{/(page=${boards.pageable.pageNumber + 1}, search=${param.search})}">Next</a>
        </li>
    </ul>
</nav>
```
* `th:classappend`를 사용해 조건에 맞을 경우 동적으로 클래스에 disable or active가 추가되게 함.
* `th:each`로 반복하며 `${#numbers.sequence(startPage, endPage)}`를 통해 컨트롤러에서 모델에 추가한 startPage, endPage까지 숫자 범위를 설정한다.
* 검색 시에 페이지를 누르면 검색이 초기화되는 것을 막기 위해 `th:href` url에 search 쿼리 파라미터 추가

<br>

```java
<!-- Search Bar -->
<form class="d-flex" style="position: relative; top: 40px;" method="get" th:action="@{/}">
    <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search"              
    id="search" name="search" th:value="${param.search}">
    <button class="btn btn-outline-success" type="submit">Search</button>
</form>
```
* 검색어를 입력할 경우 검색어가 url에 쿼리 파라미터로 붙어서 GET 방식으로 url이 요청된다. 
* `th:value="${param.search}"`로 검색어가 검색창에 유지되도록 함.

<br>

## 5. 회고 / 느낀점
> 프로젝트 개발 회고록 : https://miree.tistory.com/135

<br>
