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
* service에서 입력받은 비밀번호가 DB에 저장된 비밀번호와 일치할 경우 회원 탈퇴 진행. 📌 [service](https://github.com/ddukkung/xyab/blob/2edc61d129af72fca78667a45786f220ab6c6d70/src/main/java/community/xyab/service/UserService.java#L40)
  * BCryptPasswordEncoder의 matches() 메소드를 통해 입력받은 비밀번호를 해시코드로 변환시켜 비교
  * `SecurityContextHolder.clearContext();`를 추가하여 로그아웃 처리

<br>

### 페이지네이션
* JPA의 Pageable을 사용하여 페이징 처리   

<br>

### 댓글 조회, 작성 및 삭제
* 상세 조회한 게시글에 댓글을 작성할 수 있으며 본인이 작성한 댓글의 경우에만 삭제 가능   

<br>

### 검색
* 키워드를 입력하여 검색 버튼 클릭 시 제목이나 내용에 해당 키워드가 포함된 게시글 목록을 조회할 수 있다.   

<br>

## 5. 회고 / 느낀점
> 프로젝트 개발 회고록 : https://miree.tistory.com/135

<br>
