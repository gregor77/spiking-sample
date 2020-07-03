# spiking-sample
## Integration API Error Handling
***해당 API의 호출 빈도와 에러 발생 가능 빈도를 기준으로 판단한다.***
API의 호출빈도가 빈번한 경우 롤백을 원칙으로 한다.
API의 호출빈도가 낮고, 롤백 비용이 비싼 경우는 재처리를 유도한다.

## 시나리오별 에러 처리 
### 1. 재처리 유도 -  Project 생성 시나리오
Project 생성시 mattermost와 연계되는 데이터 저장 테이블, (Mattermost_Team 테이블)
channel의 경우 하드코딩된 값을 사용하기 때문에 DB에 별도 저장하지 않는다.

- 빈번하게 발생하지 않는 경우에는 사용자가 재처리를 할 수 있는 기능을 제공한다. 
    ex: 프로젝트 생성하는 service의 비즈니스 로직은 다음과 같다.
    1. Project 생성을 위한 openpms 관련 DB insert (project, portal, menu, workflow, rootwork, adminUser...)
    2. mattermost team 생성
    3. mattermost admin user 생성
    4. mattermost notification & random channel 생성
    5. mattermost webhook 설정
    6. mattermost slash command 생성 
    
    1번 처리 도중 실패할 경우 : create project service transaction 내에서 rollback 되므로 추가 처리 불필요.
    2~6번 처리 도중 실패할 경우 : 


#### 해결 방법 - API별 상태 관리
API별 상태관리를 통해서, 화면에서 READY, FAILED의 경우 사용자가 재처리할 수 있도록 유도한다.
```
# 현재 - API 연계 종료 후에
repository.save();

# TOBE - 1) 처음에 READY 상태 save, 2) 실패 또는 성공시 상태 변경
```
- TO-BE 로직 : 연계 전에 STATUS = READY repository.save();
  . API 연계 중간 실패 : FAILED 수정, 어떤 step에서 실패했는지 알아야 한다.
  . Mattermost 연계 API = 하EHS 서비스 API DB 저장
  . 모든 API 연계 성공 : SUCCESS 수정
- Project 생성 API는 이미 EHS에 SVC_ID 지정되어 있다.
  . mattermost Api를 DB에서 관리를 한다.
  . SVC_ID에 어떤 mattermost APi 사용되는지 매핑관리한다.
  . ***만약 SVC_ID를 session에서 알 수 없다면, 화면에서 API 요청시 파라미터로 전달한다..***
  . STATUS : Default는 READY, SUCCESS, FAIL
  . 화면 프로젝트 관리에서 프로젝트 클릭 -> 어떤 연계 api 에러났는지 상태 확인(READY, FAIL)
  . 사용자는 READY, FAIL만 다시 수행한다.   
  
- 화면 "프로젝트 관리" 메뉴에서 "Messenger 연계" 컬럼에서 상태값 확인 가능
  . FAILED 버튼 클릭, 실패한 API 상태 확인  

### 2. 롤백 - 사용자 가입 시나리오 
빈번하게 발생하는 경우에는 모든 이전 단계를 rollback 한다.
* ex: 회원가입 service의 비즈니스 로직은 다음과 같다.
    1. PMS 로그인을 위한 openpms 관련 DB insert (user account, role ...)
    2. mattermost 사용을 위한 user 생성
    3. mattermost의 private channel 생성 & invite
    
   1번 처리 도중 실패할 경우 : user service transaction 내에서 rollback 되므로 추가 처리 불필요.
   2번 이후 : 실패 이전 각 단계별 rollback 하는 api 호출해야 함.

![Integration API Error Handling](https://github.com/gregor77/spiking-sample/blob/master/src/main/resources/images/fallback-api.png)