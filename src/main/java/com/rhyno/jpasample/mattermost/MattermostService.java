package com.rhyno.jpasample.mattermost;

import com.rhyno.jpasample.mattermost.api.MattermostChannelApi;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MattermostService {
    private MattermostChannelApi mattermostChannelApi;

    public MattermostService(MattermostChannelApi mattermostChannelApi) {
        this.mattermostChannelApi = mattermostChannelApi;
    }

    // EE Architecture : layered architecture
    // Controller(Application layer) : request, response, request validation, 권한
    // - Service(Business layer) : business validation, repository call, other service
    // - Repository (Infrastructure layer) : DB - DB crud, Cache - Cache crud

//    ProjectController.createProject() {
//        // A.method() -> @Transaction
//        // B.method()
//    }
//
//    @Transactional
//    ProjectService.createProject()  {
//        // project 생성
//        // workflow
//        // systemAdminUSer
//
//        // mattermost api -> RuntimeExceptiopn
//        // api 안에서만 fallback 처리해준다.
//    }

    @Transactional
    // Exception : CheckException vs UncheckedException
    // CheckedException : 직접 try...catch, Exception 상속 --> rollback X
    // UncheckedException : RuntimeException 상속 받은 애들 --> Rollback O
    public void createMattermostTeam() {
        // 1. db save
        repository.save();
        repository.save();
        repository.save();
        repository.save();

        // TODO. wiki 정리

        // 1) 빈번하게 발생안한다 ->  프로젝트 생성 : 운영자, 프로젝트 관리자 (?) / 재처리 기능 제공
        // 프로젝트 관리자 -> 프로젝트 추가, 컬럼 (Messenger 상태 : 정상, 실패 - 재처리 버튼)
        // Integration 상태 팝업 -
        // Team 생성 여부,
        // Admin User 생성 여부,
        // channel 생성여부,
        // webhook, -> Mattermost Get api 비정상표시, 재처리 createHook
        // slack commnd -> Mattermost Get api 값 있네, 없어 재처리
        // Team api
        // 2. MM api - Admin user
        // 3. MM api - create channel , invite channel


        // 2) 빈번하게 생기는  거 : 회원가입 -> roll back

        // webhook
        // slack command


        mattermostChannelApi.createAndInviteChannel();


        throw new RuntimeException("error");
    }
}
