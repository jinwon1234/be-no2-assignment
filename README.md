## API 명세서

| 기능             | Method | URL                                 | request       | response         | 상태코드           |
|------------------|--------|-------------------------------------|----------------|------------------|---------------------|
| 일정 등록        | POST   | /api/todo                           | 요청 body      | 등록 정보         | 201 (Created)       |
| 일정 단건 조회   | GET    | /api/todo/{todoId}                  | 요청 param     | 단건 응답 정보     | 200 (OK)            |
| 전체 일정 조회   | GET    | /api/todo                           | 요청 param     | 다건 응답 정보     | 200 (OK)            |
| 일정 삭제        | DELETE | /api/todo/{todoId}                  | 요청 param     | -                | 204 (No Content)    |
| 일정 수정        | PATCH  | /api/todo/{todoId}                  | 요청 body      | 수정 정보         | 200 (OK)            |
| 회원가입         | POST   | /api/users                          | 요청 body      | 가입된 회원 정보   | 201 (Created)       |
| 회원의 할 일 조회 | GET    | /api/users/{userId}/todo            | 요청 param     | 회원의 일정 정보   | 200 (OK)            |

## ERD

- Lv3 구현 전 ERD
![image](https://github.com/user-attachments/assets/7d039c40-6e01-435c-ad8a-aa4129ea7036)
