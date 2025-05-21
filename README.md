## API 명세서

| 기능             | Method | URL                                 | request       | response         | 상태코드           |
|------------------|--------|-------------------------------------|----------------|------------------|---------------------|
| 일정 등록        | POST   | /api/schedules                           | 요청 body      | 등록 정보         | 201 (Created)       |
| 일정 단건 조회   | GET    | /api/schedules/{scheduleId}                  | 요청 param     | 단건 응답 정보     | 200 (OK)            |
| 전체 일정 조회   | GET    | /api/schedules                           | 요청 param     | 다건 응답 정보     | 200 (OK)            |
| 일정 삭제        | DELETE | /api/schedules/{scheduleId}                  | 요청 param     | -                | 204 (No Content)    |
| 일정 수정        | PATCH  | /api/schedules/{scheduleId}                  | 요청 body      | 수정 정보         | 200 (OK)            |
| 회원가입         | POST   | /api/users                          | 요청 body      | 가입된 회원 정보   | 201 (Created)       |
| 회원의 할 일 조회 | GET    | /api/users/{userId}/schedules            | 요청 param     | 회원의 일정 정보   | 200 (OK)            |

## ERD

- Lv3 구현 전 ERD
![image](https://github.com/user-attachments/assets/4fd5fbd1-ba0a-41fc-8674-bcbabaa7e123)


