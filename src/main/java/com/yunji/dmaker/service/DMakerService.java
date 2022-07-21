package com.yunji.dmaker.service;

import com.yunji.dmaker.dto.CreateDeveloper;
import com.yunji.dmaker.entity.Developer;
import com.yunji.dmaker.exception.DMakerErrorCode;
import com.yunji.dmaker.exception.DMakerException;
import com.yunji.dmaker.repository.DeveloperRepository;
import com.yunji.dmaker.type.DeveloperLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * description
 * <p>
 * author         : yunji
 * date           : 22. 7. 18.
 */
@Service
@RequiredArgsConstructor
public class DMakerService {
    private final DeveloperRepository developerRepository;
    private final EntityManager em;

    @Transactional
    public CreateDeveloper.Response createDeveloper(CreateDeveloper.Request request) {

        validateCreateDeveloperRequest(request);

        Developer developer = Developer.builder()
                .developerLevel(request.getDeveloperLevel())
                .developerSkillType(request.getDeveloperSkillType())
                .experienceYears(request.getExperienceYears())
                .name(request.getName())
                .age(request.getAge())
                .memberId(request.getMemberId())
                .build();

        developerRepository.save(developer);

        return CreateDeveloper.Response.fromEntity(developer);

    }

    private void validateCreateDeveloperRequest(CreateDeveloper.Request request) {
        //business validation
        DeveloperLevel developerLevel = request.getDeveloperLevel();
        Integer experienceYears = request.getExperienceYears();
        if(developerLevel == DeveloperLevel.SENIOR &&
                experienceYears < 10){
            throw new DMakerException(DMakerErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }
        
        if(developerLevel == DeveloperLevel.JUNGNIOR && (experienceYears < 4 || experienceYears > 10) ){
            throw new DMakerException(DMakerErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

        if(developerLevel == DeveloperLevel.JUNIOR && experienceYears > 4){
            throw new DMakerException(DMakerErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

        developerRepository.findByMemberId(request.getMemberId())
                .ifPresent((developer) -> {throw new DMakerException(DMakerErrorCode.DUPLICATED_MEMBER_ID);});
        
    }

    // transaction 관련 동작``
    public void transactionTest() {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            //비지니스 로직 시작
            // A -> B 1만원 송금
            // A 계좌에서 1 만원 줄임
            // B 계좌에서 1 만원 늘림
            //비지니스 로직 종료
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

}
