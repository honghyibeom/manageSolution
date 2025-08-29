# SpringBoot-Project
- springBoot + mybatis + thymeleaf <br>
 웹 프로젝트 입니다. <br>

## 🖥️ 프로젝트 소개
헬스장 및 운동 시설의 고객관리 프로젝트입니다. <br>
오래된 헬스장에 가보면 수기 관리하고 있어서 <br>
기본적인 회원 관리 및 매출현황을 편하게 관리할 수 있도록 하면 어떨까<br>
그리고 SSR과 mybatis를 경험해보고자 프로젝트를 만들었습니다. <br>

## 🕰️ 개발 기간
* 25.06.26일 - 진행중

### 🧑‍🤝‍🧑 맴버구성
홍희범 - JavaSpringBoot 

- ### ⚙️ 개발 환경
![Java](https://img.shields.io/badge/Java-17-007396?logo=java)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.3.1-6DB33F?logo=springboot)
![MyBatis](https://img.shields.io/badge/MyBatis-Mapper-red)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-Template-brightgreen)
![MariaDB](https://img.shields.io/badge/MariaDB-11.0-blue?logo=mariadb)

### 📌 사용 기술
- Spring Security
- myBatis
- Thymeleaf

## 📌 주요 기능

| 기능           | 설명                                   |
| -------------- | -------------------------------------- |
| 로그인         | Spring Security 기반 form 로그인       |
| 회원 관리      | 회원 CRUD, 상품(PT/회원권) CRUD        |
| 결제 관리      | 미결제/마감 회원 조회, 결제 내역 조회  |
| 강사 관리      | 강사 CRUD, 담당 회원 관리              |
| 수업 관리      | 트레이너별 수업 CRUD, 시간표 조회      |
| 매출 통계      | 총 매출, 일/월별 매출 그래프, 상품별 매출 |
| CARE 뷰       | PT 첫 수업 조회, 마감 임박 회원 조회    |
| 관리자 대시보드 | 회원 통계, 상품 순위, 트레이너 일정 조회 |


## 🗒 ERD
<img width="1660" height="1007" alt="Untitled (1)" src="https://github.com/user-attachments/assets/a6d5e6d4-85f2-42e4-9839-eb32150f3bae" />

## 🖼️ View
#### 관리 메뉴
- 회원 관리
<img width="1357" height="626" alt="image" src="https://github.com/user-attachments/assets/aaf8a6b8-9ce6-483d-87cc-416e9ac8060a" />

- 결제 관리
<img width="1610" height="817" alt="image" src="https://github.com/user-attachments/assets/34268d8d-b268-4a5d-89b0-f00a4102bdac" />

- 강사 관리
<img width="1260" height="715" alt="image" src="https://github.com/user-attachments/assets/f4ca1656-1887-476c-84bd-88b5846d2103" />

- 상품 관리
<img width="1365" height="698" alt="image" src="https://github.com/user-attachments/assets/520631ea-253a-4fe5-b106-ee44d5ac53b0" />

- 수업 관리
<img width="1358" height="680" alt="image" src="https://github.com/user-attachments/assets/94713497-a229-4d2c-ace4-337016042dce" />

#### 리포트
<img width="1613" height="776" alt="image" src="https://github.com/user-attachments/assets/5815862f-b192-45c5-aa97-b9eeafb5e7ef" />
<img width="901" height="884" alt="image" src="https://github.com/user-attachments/assets/08998422-a0ed-4dbc-858a-8bd1cb0fceee" />
<img width="1259" height="829" alt="image" src="https://github.com/user-attachments/assets/a1894464-6929-4477-be01-36dabb4890c4" />


## 🔮 향후 개선 계획
- 얼굴인식 출석 기능
- 환불 기능
- 모바일 앱 버전 출시
- Docker 기반 배포 자동화


## 🤔 고민과 개선 과정 
사용자 입장에서 최대한 편하게 프로그램을 사용할 수 있도록 고민하였다.

### 1. Subscription 테이블 설계
- 기존: `membership`, `pt_package` 두 개의 테이블을 분리 운영
- 문제점: 매출 통계 조회 시 `UNION ALL`을 사용하여 별도로 합쳐야 했음
- 개선: 두 테이블을 **`subscription`** 으로 통합
  - 단일 테이블 기반으로 통계 조회 단순화
  - 확장성과 유지보수 용이성 확보

---

### 2. 수업 등록/조회 UI 개선
#### 초기 버전
![image.png](attachment:721cf1b7-36a6-4c36-8f57-9eecfcfd9716:image.png)
![image.png](attachment:5ddc6cd8-e155-40c9-8d84-ee1cb64b5890:image.png)

- 달력에서 날짜 클릭 → 해당 날짜의 PT 수업 등록 및 조회
- 문제점
  - 날짜별로 클릭해야 해서 전체 조회 불가능
  - 모달창 등록 시 반복 등록 불편
    - 관리자가 직접 날짜를 계산해야 함
    - 시간대를 일일이 입력해야 함

#### 개선 버전
![image.png](attachment:4609f14d-66f1-4e03-81da-1a7d97052daa:image.png)
![image.png](attachment:f647786f-6364-454e-b48f-a4e0b8ce2268:34f240db-12ef-4e6b-a845-1d94d51895bd.png)
- **전체 조회 기능 추가** : 날짜 단위가 아니라 기간/트레이너 단위 조회 가능
- **등록 UI 개선** : 
  - 반복 등록 시 요일·주차 선택 가능
  - 시간대 버튼 선택형으로 전환 → 관리자의 입력 부담 감소
- 결과: 수업 관리 효율성 및 사용자 경험(UX) 개선

