# spiking-sample
### Error Handling의 컨셉은 해당 API의 호출 빈도와 에러 발생 가능 빈도를 기준으로 판단한다.

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
    
    
    
    
- 빈번하게 발생하는 경우에는 모든 이전 단계를 rollback 한다.
    ex: 회원가입 service의 비즈니스 로직은 다음과 같다.
    1. PMS 로그인을 위한 openpms 관련 DB insert (user account, role ...)
    2. mattermost 사용을 위한 user 생성
    3. mattermost의 private channel 생성 & invite
    
   1번 처리 도중 실패할 경우 : user service transaction 내에서 rollback 되므로 추가 처리 불필요.
   2번 이후 : 실패 이전 각 단계별 rollback 하는 api 호출해야 함.
