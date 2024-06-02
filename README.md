# 레프
냉장고 식재료 관리 서비스

## 핵심 기능 정의

| 기능 명 | 기능 설명 |
| --- | --- |
| 식재료 인식 | Object Detection을 이용한 식재료 인식 |
| 식재료별 영양 성분 정보 생성 | 등록한 재료의 중량 정보를 기반으로 재료의 영양 성분 정보를 생성 |
| 음식 레시피 추천 | 냉장고 속 재료를 기반으로 하여 아침/점심/저녁 음식 레시피 추천 |
| 이미지 생성 | 유통기한 마감 임박(D-3) 재료에 대해 알림 |
| 유통기한 알림 | 등록한 재료/추천된 레시피에 맞는 예시 이미지 생성 |

### ERD

### 서비스 구성도
![서비스 구성도](https://github.com/zxcvb2002/Project/assets/122873008/506acb8b-c0f2-4c87-8dc3-8184e9efd6d0)

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
