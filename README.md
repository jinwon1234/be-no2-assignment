
v1은 필수기능만 구현한 버전
v2는 도전기능 까지 모두 구현한 버전입니다.

## API 명세서

- v1 API 명세서

| 기능             | Method | URL                                 | request       | response         | 상태코드           |
|------------------|--------|-------------------------------------|----------------|------------------|---------------------|
| 일정 등록        | POST   | /api/v1/schedules                           | 요청 body      | 등록 정보         | 201 (Created)       |
| 일정 단건 조회   | GET    | /api/v1/schedules/{scheduleId}                  | 요청 param     | 단건 응답 정보     | 200 (OK)            |
| 전체 일정 조회   | GET    | /api/v1/schedules                           | 요청 param     | 다건 응답 정보     | 200 (OK)            |
| 일정 삭제        | DELETE | /api/v1/schedules/{scheduleId}                  | 요청 body     | -                | 204 (No Content)    |
| 일정 수정        | PATCH  | /api/v1/schedules/{scheduleId}                  | 요청 body      | 수정 정보         | 200 (OK)            |

- v2 API 명세서

| 기능             | Method | URL                                 | request       | response         | 상태코드           |
|------------------|--------|-------------------------------------|----------------|------------------|---------------------|
| 일정 등록        | POST   | /api/v2/schedules                           | 요청 body      | 등록 정보         | 201 (Created)       |
| 일정 단건 조회   | GET    | /api/v2/schedules/{scheduleId}                  | 요청 param     | 단건 응답 정보     | 200 (OK)            |
| 전체 일정 조회   | GET    | /api/v2/schedules                           | 요청 param     | 다건 응답 정보     | 200 (OK)            |
| 일정 삭제        | DELETE | /api/v2/schedules/{scheduleId}                  | 요청 body     | -                | 204 (No Content)    |
| 일정 수정        | PATCH  | /api/v2/schedules/{scheduleId}                  | 요청 body      | 수정 정보         | 200 (OK)            |
| 회원가입         | POST   | /api/v2/users                          | 요청 body      | 가입된 회원 정보   | 201 (Created)       |
| 회원 이름 수정        | PATCH   | /api/v2/users/name                          | 요청 body      | 수정된 회원 정보   | 200 (OK)       |
| 회원 비밀번호 수정        | PATCH   | /api/v2/users/password                          | 요청 body      | 수정된 회원 정보   | 200 (OK)       |
| 회원탈퇴         | DELETE   | /api/v2/users                          | 요청 body      | -   | 204 (No Content)       |
| 회원의 할 일 조회 | GET    | /api/v2/users/{userId}/schedules            | 요청 param     | 회원의 일정 정보   | 200 (OK)            |

## ERD

- v1 ERD
![image](https://github.com/user-attachments/assets/33174468-dcc3-4e0e-9859-12fa434b622e)

- v2 ERD
![image](https://github.com/user-attachments/assets/0f32faa7-c4e7-42ca-b196-1434d30e9212)





