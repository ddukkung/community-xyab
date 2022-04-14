# XYAB
> 전반적인 웹의 기본 소양이 되는 게시판 프로젝트입니다.  
  
  
![xyab](https://user-images.githubusercontent.com/88926356/162909413-b56e8a43-b595-4b07-89fb-5d1f66883ed5.jpg)  

<br>

# 목차
 * [들어가며](#들어가며)
   * [프로젝트 소개](#프로젝트-소개)
   * [프로젝트 기능](#프로젝트-기능)
   * [개발 환경](#개발환경)
   * [실행 화면](#실행화면)
 * [구조 및 설계](#구조-및-설계)
   * [DB 설계](#db-설계)
   * [API 설계](#api-설계)
 * [마치며](#마치며)
   * [보완사항](#보완사항)
   * [후기](#후기)
  
<br>
  
# 들어가며

## 프로젝트 소개
학원 수료 후 스프링에 대한 공부가 부족하다고 느껴 스프링 부트 강의를 들었습니다. 완강 후 배운 것을 적용하고자 웹의 기본인 게시판 프로젝트를 만들었습니다. 
XYAB는 닌텐도 스위치 유저를 위한 커뮤니티로, 회원가입을 하여 자신의 글을 작성하거나 다른 사람의 글에 댓글을 남길 수 있습니다.  
  
<br>

## 프로젝트 기능
프로젝트의 주요 기능은 다음과 같습니다.   

 * **게시판** - CRUD 기능, 조회수, 페이징, 검색
 * **사용자** - Spring Security 회원가입 및 로그인, 회원 정보 수정, 회원 탈퇴
 * **댓글** - 댓글 조회, 작성, 삭제   

<br>

## 개발환경

  * Java 11
  * Spring Boot 2.6.4
  * IntelliJ
  * MySQL 5.7
  * JPA
  * Spring Security
  * Thymeleaf
  * Ajax, BootStrap   

<br>

***

# 실행화면  

<br>

## User   

### 회원가입
![join](https://user-images.githubusercontent.com/88926356/162678606-0b5d8198-9fad-4f03-bf39-950f48a41e48.gif)
* 부트스트랩의 validation을 사용해 사용자가 유효하지 않은 값으로 회원가입 진행 시 오류 메시지 출력
* 완료 시 회원 정보를 저장하고 메인 화면으로 이동   

<br>

### 회원정보 수정
![user_update](https://user-images.githubusercontent.com/88926356/162683862-a1d37f57-5c50-43f2-a959-77800d927adb.gif)
* 자신의 비밀번호와 닉네임 변경 가능   

<br>

### 회원 탈퇴
![user_delete](https://user-images.githubusercontent.com/88926356/162684150-ba03a070-17b1-42fc-9f9d-39abf4d5f183.gif)
* 회원 정보 수정 페이지에서 회원 탈퇴 버튼 클릭 시 탈퇴 페이지로 이동
* 입력된 비밀번호가 사용자의 비밀번호와 일치할 시 회원 탈퇴 진행   

<br>


### 로그인 및 로그아웃
![login_n_logout](https://user-images.githubusercontent.com/88926356/162684370-d59a02b8-84c6-4044-ac05-4bf402b97ded.gif)
* 로그인 실패 시 실패 메시지를 출력하고 성공 시 메인 페이지로 이동
* Remember-me 버튼을 클릭한 후 로그인할 경우 Spring Security 기능을 사용해 7일 간 자동 로그인 가능   

<br>

***

## Board   

### 게시글 CRUD
![board_crud](https://user-images.githubusercontent.com/88926356/162692801-fe209682-e993-4eee-bda1-3383ec606e24.gif)
* **게시글 상세보기** 
  * 로그인한 사용자만 게시글 조회가 가능하며, 로그인하지 않았을 경우 로그인 페이지로 이동한다.
  * Spring Security를 사용하여 본인이 작성한 글에만 수정, 삭제 버튼이 나타나도록 함
* **게시글 작성**
  * 로그인한 사용자만 작성 가능하며, 작성 후 게시글 목록으로 redirect한다.
* **게시글 수정**
  * 작성자 본인일 경우에만 수정 가능하다. 제목, 글 내용을 수정할 수 있으며 수정 후 게시글 목록으로 redirect한다.
* **게시글 삭제**
  * 작성자 본인일 경우에만 삭제 가능하며 삭제 후 게시글 목록으로 redirect한다.   

<br>

### 페이지네이션
![pagination](https://user-images.githubusercontent.com/88926356/162693125-b57ba955-e2fd-4654-920e-c799ad002105.gif)
* JPA의 Pageable을 사용하여 페이징 처리   

<br>

### 댓글 조회, 작성 및 삭제
![comment](https://user-images.githubusercontent.com/88926356/162692952-19a94296-a804-4e52-828c-05f6da25178b.gif)
* 상세 조회한 게시글에 댓글을 작성할 수 있으며 본인이 작성한 댓글의 경우에만 삭제 가능   

<br>

### 검색
![search](https://user-images.githubusercontent.com/88926356/162693181-febcb68f-8171-46c5-b333-f17f49e302c4.gif)
* 키워드를 입력하여 검색 버튼 클릭 시 제목이나 내용에 해당 키워드가 포함된 게시글 목록을 조회할 수 있다.   

<br>

***

# 구조 및 설계

<br>

## DB 설계

![xyab_db](https://user-images.githubusercontent.com/88926356/163223202-938e57fb-de0b-411c-98b2-3233eba83ca0.png)  

![image](https://user-images.githubusercontent.com/88926356/163228227-3f648b79-0b1f-49d4-86a5-9db05c21db78.png)  

![image](https://user-images.githubusercontent.com/88926356/163228282-08a7ce9c-cec7-421a-a7b8-2164d34d1a2c.png)  

![image](https://user-images.githubusercontent.com/88926356/163228324-b898b4d7-ee00-4997-8759-eccfb947ff0b.png)  

<br>

## API 설계

![image](https://user-images.githubusercontent.com/88926356/163233217-8b1049fb-c81c-4660-a778-cc8bc9997eea.png)  

![image](https://user-images.githubusercontent.com/88926356/163233291-627308da-1f4e-4e35-9627-8676c2ef5423.png)  

![image](https://user-images.githubusercontent.com/88926356/163233332-8ffe31b2-793e-4cd0-bb93-2a7d12224d11.png)  

***

# 마치며  

## 보완사항  
이 프로젝트는 이전에 제 능력을 넘어서는 거대한 기획으로 인해 프로젝트를 실패한 뒤 작은 것부터 시작하자는 마음에서 시작했습니다. 그래서 기본 중의 기본 게시판 커뮤니티를 만들었지만, 이것 또한 쉽지 않다는 것을 프로젝트를 진행하며 느낄 수 있었습니다. 그 기본적인 기능을 구현하는 것도 지금의 저로써는 힘에 부치는 것들이 있어 지금 당장은 구현하지 못했지만, 앞으로 꾸준히 보완해나가며 언젠가는 사람들에게 직접 선보일 수 있는 커뮤니티로 성장시키고 싶습니다. 다음은 프로젝트를 진행하며 구현하지 못했던 기능과 추가하고 싶은 기능입니다.

<br>

**보완사항**
* 회원가입 아이디 중복 체크 및 유효성 검사 구현
* REST API 제대로 구현하기
* 댓글 수정 기능 구현 성공하기
* 페이징 
  * 페이지 수가 점점 늘어나는 것 고치기
  * 제일 마지막 페이지에서 next 버튼 클릭 시 막히는 것이 아닌 빈 화면이 나오는 것 고치기
  * 페이지 처음, 끝으로 이동하는 버튼 추가하기

<br>

**추가사항**
* 파일 업로드 기능
* OAuth2 로그인 기능
* 메인 페이지 따로 제작하기(바로 글 목록 페이지로 가지지 않도록)
* 관리자 페이지
* 마이 페이지(자신이 작성한 글, 댓글 관리 기능)
* 북마크 기능
* 게시판 수 늘리고 분리하기  

<br>
  
## 후기
처음으로 혼자 프로젝트를 진행하며 가장 많이 느낀 감정은 배움에의 갈증이었습니다.  

프로젝트를 진행하며 어떠한 기능을 구현하기 위해 인터넷에 검색해 글을 보며 참고할 때마다 처음 보는 개념이 등장하곤 했습니다. 처음에는 그런 것들을 하나하나 모두 찾아보며 나름대로 공부하고 기록하기도 했지만, 그 과정을 반복하다보니 어느 순간 프로젝트 진행을 위한 공부가 아닌 공부를 위한 공부를 하고 있다는 것을 깨달았습니다. 물론 모두 중요한 것이고 공부가 필요한 것이지만 이대로 가다가는 프로젝트를 완성할 수 없겠다는 생각에 우선 당장 필요한 개념만 공부하고 기록한 뒤 부가적인 것들은 따로 메모만 한 뒤 넘어갔습니다.  

프로젝트를 진행하며 이러한 일이 굉장히 잦았고, 저는 제가 공부할 것이 한참 많이 남았다는 것을 느낄 수 있었습니다. 컴퓨터 전반에 대한 지식이 부족하다고 느꼈기에 앞으로 CS 공부를 중심으로 자바 기초 등 기반을 다지기 위한 공부를 해나갈 것입니다.  

또한 기록하는 습관을 가지게 되었습니다.  

이전에는 모르는 개념을 보게되면 그것에 대해 찾아보더라도 기록은 따로 하지 않고 한 번 읽고 이해했다 생각하며 넘기곤 했습니다. 하지만 나중에 또 그 개념과 마주쳤을 때 분명히 이름은 들었지만 그것에 대한 지식은 휘발된 경우가 잦았습니다. 이번에는 앞서 말했듯이 프로젝트를 진행하며 모르는 개념을 마주칠 때마다 그것에 대해 검색해 공부하고, 블로그에 직접 기록하는 연습을 했습니다. 그 결과 나중에 그 개념이 헷갈릴 때 다시 찾아서 볼 수도 있고, 무엇보다 기록하며 한 번 더 머릿속에 정리가 돼 이해할 수 있는 점이 좋았습니다. 특히 오류 로그를 남기는 것이 큰 도움이 되었습니다.  

이번 프로젝트를 통해 간단한 게시판 만드는 것도 쉽지 않다는 것을 깨닫게 됐습니다. 자신의 부족함을 인식하고, 프로젝트를 진행하며 얻은 지식들을 양분으로 삼아 내알은 더 나은 자신이 되기 위해 꾸준히 노력해나가겠습니다.
