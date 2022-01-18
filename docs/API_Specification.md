# API 명세서

맘시터 회원관리 API

## 목차
* [시터 회원가입 - POST : /api/accounts/sitter](#시터회원가입)
* [부모 회원가입 - POST : /api/accounts/parent](#부모회원가입)
* [회원정보 수정 - PUT : /api/accounts/me](#회원정보수정)
* [시터정보 수정 - PUT : /api/accounts/me/sitter](#시터정보수정)
* [부모정보 수정 - PUT : /api/accounts/me/parent](#부모정보수정)
* [내 정보 보기 - GET : /api/accounts/me](#내정보보기)
* [시터회원이 부모로도 활동하기 - POST : /api/accounts/me/parent](#시터회원이부모로도활동하기)
* [부모회원이 시터로도 활동하기 - POST : /api/accounts/me/sitter](#부모회원이시터로도활동하기)
* [로그인 - POST : /api/login](#로그인)

## 시터 회원가입 <a id="시터회원가입"></a>

### Request
Request method:	POST   
Request URI:	/api/accounts/sitter
```json
{
  "account": {
    "accountId": "string",
    "dateOfBirth": "string",
    "email": "string",
    "gender": "string",
    "name": "string",
    "password": "string"
  },
  "sitterInfo": {
    "aboutMe": "string",
    "maxAge": 0,
    "minAge": 0
  }
}
```

* account : 회원 계정 정보(공통 정보)
  * name : 회원 이름
  * dateOfBirth : 회원 생년월일
  * gender : 회원 성별
  * accountId : 회원 계정 ID
  * password : 회원 비밀번호
  * email : 회원 이메일
* sitterInfo : 회원의 시터 정보
  * minAge : 케어 가능한 최소연령
  * maxAge : 케어 가능한 최대연령
  * aboutMe : 자기 소개
  
### Response
```json
HTTP/1.1 201 
Location: /accounts/1
Content-Length: 0
```

## 부모 회원가입 <a id="부모회원가입"></a>

### Request
Request method:	POST   
Request URI:	/api/accounts/parent
```json
{
    "account": {
        "name": "박민영",
        "dateOfBirth": [
            1992,
            5,
            30
        ],
        "gender": "남",
        "accountId": "parkSitter92",
        "password": "park1234!@",
        "email": "test@test.com"
    },
    "parentInfo": {
        "children": [
            {
                "dateOfBirth": [
                    2020,
                    5,
                    15
                ],
                "gender": "남"
            },
            {
                "dateOfBirth": [
                    2018,
                    3,
                    30
                ],
                "gender": "여"
            }
        ],
        "careRequestInfo": "잘 돌봐주실분!"
    }
}
```

* account : 회원 계정 정보(공통 정보)
  * name : 회원 이름
  * dateOfBirth : 회원 생년월일
  * gender : 회원 성별
  * accountId : 회원 계정 ID
  * password : 회원 비밀번호
  * email : 회원 이메일
* parentInfo : 회원 부모 정보
  * children : 돌봄을 신청하는 아이 정보
    * dateOfBirth : 아이의 생년월일
    * gender : 아이의 성별
  * careRequestInfo : 돌봄 신청 내용

### Response
```json
HTTP/1.1 201 
Location: /accounts/1
Content-Length: 0
```

## 회원정보 수정 <a id="회원정보수정"></a>

### Request
Request method:	PUT   
Request URI:	/api/accounts/me   
Headers:		Accept=application/json   
Content-Type=application/json; charset=UTF-8
```json
{
    "gender": "여",
    "password": "newpw12!!@@",
    "email": "newtest@email.com"
}
```
* gender : 회원의 수정할 성별
* password : 회원의 수정할 비밀번호
* email : 회원의 수정할 이메일

### Response
```json
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Tue, 18 Jan 2022 21:51:08 GMT
Keep-Alive: timeout=60
Connection: keep-alive

{
    "id": 1,
    "name": "박민영",
    "dateOfBirth": "1992-05-30",
    "gender": "여",
    "accountId": "parkSitter92",
    "email": "newtest@email.com"
}
```

* id : 회원의 고유번호
* name : 회원의 이름
* dateOfBirth : 회원의 생년월일
* gender : 회원의 성별
* accountId : 회원의 아이디
* email : 회원의 이메일

## 시터정보 수정 <a id="시터정보수정"></a>

### Request
Request method:	PUT   
Request URI:	/api/accounts/me/sitter   
Headers:		Accept=application/json   
Content-Type=application/json; charset=UTF-8
```json
{
    "minCareAge": 4,
    "maxCareAge": 6,
    "aboutMe": "아이들의 가장 가까운 친구, 돌보는걸 너무 잘해요update"
}
```
* minCareAge : 수정할 케어가능한 최소 연령
* maxCareAge : 수정할 케어가능한 최대 연령
* aboutMe : 수정할 자기 소개

### Response
```json
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Tue, 18 Jan 2022 21:51:08 GMT
Keep-Alive: timeout=60
Connection: keep-alive

{
    "id": 1,
    "minCareAge": 4,
    "maxCareAge": 6,
    "aboutMe": "아이들의 가장 가까운 친구, 돌보는걸 너무 잘해요update"
}
```

* id : 시터 정보의 고유 번호
* minCareAge : 케어가능한 최소 연령
* maxCareAge : 케어가능한 최대 연령
* aboutMe : 자기 소개

## 부모정보 수정 <a id="부모정보수정"></a>

### Request
Request method:	PUT   
Request URI:	/api/accounts/me/parent   
Headers:		Accept=application/json   
Content-Type=application/json; charset=UTF-8   

```json
{
  "children": [
    {
      "id": 1,
      "dateOfBirth": [
        2020,
        5,
        16
      ],
      "gender": "여"
    },
    {
      "id": 2,
      "dateOfBirth": [
        2018,
        3,
        31
      ],
      "gender": "여"
    }
  ],
  "careRequestInfo": "시터님 잘부탁드려요!"
}
```
* children : 수정할 아이들 정보
  * id : 아이정보 고유 번호
  * dateOfBirth : 아이의 생년월일
  * gender : 아이의 성별
* careRequestInfo : 돌봄 요청 내용

### Response
```json
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Tue, 18 Jan 2022 21:51:09 GMT
Keep-Alive: timeout=60
Connection: keep-alive

{
    "id": 1,
    "children": [
        {
            "id": 1,
            "dateOfBirth": "2020-05-10",
            "gender": "여"
        },
        {
            "id": 2,
            "dateOfBirth": "2018-03-15",
            "gender": "남"
        }
    ],
    "careRequestInfo": "시터님 잘부탁드려요!"
}
```

## 내 정보 보기 <a id="내정보보기"></a>
### Request
Request method:	GET   
Request URI:	/api/accounts/me   
Headers:		Accept=application/json

### Response
```json
HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Tue, 18 Jan 2022 21:51:09 GMT
Keep-Alive: timeout=60
Connection: keep-alive

{
    "account": {
        "id": 1,
        "name": "박민영",
        "dateOfBirth": "1992-05-30",
        "gender": "여",
        "accountId": "parkSitter92",
        "email": "newtest@email.com"
    },
    "sitter": null,
    "parent": {
        "id": 1,
        "children": [
            {
                "id": 1,
                "dateOfBirth": "2020-05-15",
                "gender": "남"
            },
            {
                "id": 2,
                "dateOfBirth": "2018-03-30",
                "gender": "여"
            }
        ],
        "careRequestInfo": "잘 돌봐주실분!"
    },
    "sitterAccount": false,
    "parentAccount": true
}
```

* account : 회원 정보
  * id : 회원 고유번호
  * name : 회원 이름
  * dateOfBirth : 회원 생년월일
  * gender : 회원 성별
  * accountId : 회원 계정 아이디
  * email : 회원 이메일
* sitter(Optional) : 시터 정보
  * minCareAge : 케어 가능한 최소연령
  * maxCareAge : 케어 가능한 최대연령
  * aboutMe : 자기 소개
* parent(Optional) : 부모 정보
  * id : 부모 고유 번호
  * children : 돌봄 아이 정보
    * id : 아이 고유 번호
    * dateOfBirth : 아이 생년월일
    * gender : 아이 성별
  * careRequestInfo : 돌봄 신청 내용
* sitterAccount : 회원이 시터회원인지 여부 (true, false)
* parentAccount : 회원이 부모회원인지 여부 (true, false)

## 시터회원이 부모로도 활동하기 <a id="시터회원이부모로도활동하기"></a>

### Request
Request method:	POST   
Request URI:	/api/accounts/me/parent
Headers:		Accept=application/json   
Content-Type=application/json; charset=UTF-8   
```json
{
    "children": [
        {
            "dateOfBirth": [
                2020,
                5,
                15
            ],
            "gender": "남"
        },
        {
            "dateOfBirth": [
                2018,
                3,
                30
            ],
            "gender": "여"
        }
    ],
    "careRequestInfo": "잘 돌봐주실분!"
}
```
* children : 돌봄 신청 아이 정보
  * dateOfBirth : 아이의 생년월일
  * gender : 아이의 성별
* careRequestInfo : 돌봄 신청 내용

### Response
```json
HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Tue, 18 Jan 2022 21:51:08 GMT
Keep-Alive: timeout=60
Connection: keep-alive
{
    "id": 1,
    "children": [
        {
            "id": 1,
            "dateOfBirth": "2020-05-15",
            "gender": "남"
        },
        {
            "id": 2,
            "dateOfBirth": "2018-03-30",
            "gender": "여"
        }
    ],
    "careRequestInfo": "잘 돌봐주실분!"
}
```
* id : 부모 정보의 고유 번호
* children : 돌봄 신청 아이 정보
  * id : 아이 고유 번호
  * dateOfBirth : 아이의 생년월일
  * gender : 아이의 성별
* careRequestInfo : 돌봄 신청 내용

## 부모회원이 시터로도 활동하기 <a id="부모회원이시터로도활동하기"></a>

### Request
Request method:	POST   
Request URI:	api/accounts/me/sitter   
Headers:		Accept=application/json   
Content-Type=application/json; charset=UTF-8   
```json
{
    "minAge": 2,
    "maxAge": 4,
    "aboutMe": "언제나 내 아이처럼!, 내 가족처럼!"
}
```
* minAge : 케어 가능한 최소 연령
* maxAge : 케어 가능한 최대 연령
* aboutMe : 돌봄 신청 내용

### Response
```json
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Tue, 18 Jan 2022 21:51:09 GMT
Keep-Alive: timeout=60
Connection: keep-alive

{
"id": 1,
"minCareAge": 2,
"maxCareAge": 4,
"aboutMe": "언제나 내 아이처럼!, 내 가족처럼!"
}
```
* id : 부모 고유 번호
* minCareAge : 케어 가능한 최소 연령
* maxCareAge : 케어 가능한 최대 연령
* aboutMe : 돌봄 신청 내용

## 로그인 <a id="로그인"></a>

### Request
Request method:	POST
Request URI:	/api/login
Headers:		Accept=*/*   
Content-Type=application/json; charset=UTF-8   
```json
{
    "accountId": "parkSitter92",
    "password": "park1234!@"
}
```
* accountId : 로그인에 사용되는 계정 아이디
* password : 로그인에 사용되는 계정 비밀번호

### Response
```json
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Tue, 18 Jan 2022 21:51:08 GMT
Keep-Alive: timeout=60
Connection: keep-alive

{
    "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYXJrU2l0dGVyOTIiLCJpYXQiOjE2NDI1NDI2NjksImV4cCI6MTY0MjU0NDQ2OX0.i0rTBg71xOJ9WzLsGSpzy5EeB45Lz3DyvJO8toXbJ6Y"
}
```

* accessToken : 회원 인증 토큰