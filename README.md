# REF

## 서비스 소개

AI 기반 냉장고 식재료 관리 서비스, 레프

### 타겟층

냉장고를 사용하는 모든 사람들, 특히 많이 사용하는 자취생, 주부 등을 타겟으로 함
- 식단 관리하는 사람들 -> 등록된 식재료의 영양 성분을 제공하여 도움을 줌

### 핵심 기능

| 기능 명 | 기능 설명 |
| --- | --- |
| 식재료 인식 | Object Detection을 이용한 식재료 인식 |
| 식재료별 영양 성분 정보 생성 | 등록한 재료의 중량 정보를 기반으로 재료의 영양 성분 정보를 생성 |
| 식재료 기반 음식 레시피 추천 | 냉장고 속 재료를 기반으로 하여 아침/점심/저녁 음식 레시피 추천 |
| 음식 예시 이미지 생성 | 등록한 재료/추천된 레시피에 맞는 예시 이미지 생성 |
| 식재료 소비기한 알림 | 유통기한 마감 임박(D-3) 재료에 대해 알림 |

### 화면
| 탭 | 탭 설명 |
| --- | --- |
| 식재료 등록 | 카메라로 식재료 사진 촬영, Image Classification으로 식재료 이름 자동 등록, 식재료별 무게 기반 영양소 자동 생성 |
| 식재료 표시 | 식재료별 냉장/냉동 분류, 냉장고 속 재료의 세부 정보 표시, 식재료 수정 및 삭제 가능 |
| 음식 레시피 추천 | 내 냉장고 안 식재료들을 기반으로 한 아침/점심/저녁 음식 추천, 음식 예시 이미지 생성, 음식 레시피 및 조리방법 등 세부정보 표시 |
| 마이페이지 | 알림 설정, 회원 정보 수정, 기타 설정 |

### 와이어프레임

<img width="932" alt="화면 설계" src="https://github.com/zxcvb2002/Project/assets/122873008/0d7d32bc-206e-4c7b-b4e6-9ec38c78637d">

### 프로토타입
![Android Large - 3 (아마_ 최종본)](https://github.com/zxcvb2002/Project/assets/122873008/8ed5bd91-832e-4e58-910a-c231c54f8a2f)
![Android Large - 8 (아마_ 최종본)](https://github.com/zxcvb2002/Project/assets/122873008/52048d6d-9b33-4416-bfa5-45e72f1dc5d0)

![Android Large - 1 (선택클릭시)](https://github.com/zxcvb2002/Project/assets/122873008/7848f2d2-a341-420c-bfb9-b86cf53fcacb)
![Android Large - 5 (아마_ 최종본)](https://github.com/zxcvb2002/Project/assets/122873008/5da0b3f6-72b8-46df-a2e9-80abcf81e600)

![Android Large - 7 (아마_ 최종](https://github.com/zxcvb2002/Project/assets/122873008/96727d71-f4ec-470a-8e01-8f93cc2c4f82)
![Android Large - 7 (아마_ 최종본)](https://github.com/zxcvb2002/Project/assets/122873008/fe8a56e2-a833-4cde-8c9c-08f08fbec3d6)

### MVP

[시현 영상]()

## 설계

### ERD

### 기능 명세서

[서버 API](https://tremendous-waiter-41f.notion.site/REF-API-c72f2e5fb89648bda2352558ceab1584?pvs=4)

### 서비스 구성도
![서비스 구성도](https://github.com/zxcvb2002/Project/assets/122873008/506acb8b-c0f2-4c87-8dc3-8184e9efd6d0)

## Git

### Branch Strategy

Git Flow 참고
- main (release)
- develop (default)
  - feature
    - /#1
    - /#2
    - /#3

![cfa3fab2-c1bd-49ff-9cc6-4f1b8ddb3be0_1322x567](https://github.com/zxcvb2002/Project/assets/122873008/80584432-c1ef-4230-89da-a71ce8b877f3)

![9fed8a5a-f8b3-44aa-bbca-83d98f78e052_1322x402](https://github.com/zxcvb2002/Project/assets/122873008/9432ff3f-8f87-4247-a9f6-dfe74d7f7fc3)


## Collaboration

### Kanban

프로젝트 관리를 위해 Kanban Board 활용 (To-do / In Progress / Complete)

<img width="850" alt="스크린샷 2024-06-02 오후 6 43 39" src="https://github.com/zxcvb2002/Project/assets/122873008/ead076ba-a27f-427d-b9e6-63a357cdb478">

### 기술 스택

- Client
  
  ![Android](https://img.shields.io/badge/Android-34A853?style=flat-square&logo=android&logoColor=white)
  ![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=flat-square&logo=kotlin&logoColor=white)
  ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=flat-square&logo=Gradle&logoColor=white)

- Server
  
  ![OpenAI-API](https://img.shields.io/badge/OpenAI-555555?style=flat-square&logo=openai&logoColor=white)
  ![FastAPI](https://img.shields.io/badge/FastAPI-009688?style=flat-square&logo=fastapi&logoColor=white)
  ![Postman](https://img.shields.io/badge/Postman-FF6C37?style=flat-square&logo=postman&logoColor=white)

- AI
  
  ![TFLite](https://img.shields.io/badge/TFLite-FF6F00.svg?style=flat-square&logo=tensorflow&logoColor=white)

- Co-working
  
  ![Notion](https://img.shields.io/badge/Notion-000000.svg?style=flat-square&logo=notion&logoColor=white)
  ![Git](https://img.shields.io/badge/Git-F05032.svg?style=flat-square&logo=git&logoColor=white)
  ![Github](https://img.shields.io/badge/Github-181717.svg?style=flat-square&logo=github&logoColor=white)

- Design
  
  ![Figma](https://img.shields.io/badge/Figma-F24E1E.svg?style=flat-square&logo=figma&logoColor=white)

## 팀 구성

| 이름 | 분야 | 담당 |
| --- | --- | --- |
| 황병주 | 클라이언트 | Android, XML, Kotlin |
| 정새움 | 기획 | 서비스 기획, 서비스 명, 서비스 로고 |
| 변우석 | 디자인 | 서비스 디자인, UI 디자인, UX 디자인 |
| 정보영 | 서버 | API, DB, AI |
