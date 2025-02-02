# 엔티티 정보

![img.png](img.png)

### 유저

회원정보 (Account)
* id : 회원 가입시 자동으로 부여되는 고유 번호
* name : 회원 이름
* dateOfBirth : YYYYMMDD 포맷으로 저장
* gender : 남, 여
* accountId : 회원이 직접 입력한 고유한 아이디.
* password : 계정 비밀번호.
* email : 회원 이메일

시터정보 (SitterInfo)
* id : 시터 정보의 고유 번호
* minAge : 케어 가능한 최소 연령
* maxAge : 케어 가능한 최대 연령
* aboutMe : 자기 소개

부모정보 (ParentInfo)
* id : 부모 정보의 고유 번호
* children : 돌봄을 원하는 아이들 정보
* careRequestInfo : 돌봄 신청 내용

Account -> SitterInfo : OneToOne 단방향 관계 (Account 쪽이 외래키 관리자)
Account -> ParentInfo : OneToOne 단방향 관계 (Account 쪽이 외래키 관리자)
* SitterInfo, ParentInfo 쪽에서는 Account가 필요할 일이 적을거라 판단 (만약 필요하다면 추후 양방향으로 리팩터링 가능)
* 양방향 관계를 피함으로, 변경을 최소화

ParentInfo <-> Child : ManyToOne 양방향 관계 (Child 쪽이 외래키 관리자)

