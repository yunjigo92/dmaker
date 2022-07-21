package com.yunji.dmaker.service;

import com.yunji.dmaker.dto.CreateDeveloper;
import com.yunji.dmaker.dto.DeveloperDetailDto;
import com.yunji.dmaker.dto.DeveloperDto;
import com.yunji.dmaker.dto.EditDeveloper;
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
import java.util.List;
import java.util.stream.Collectors;

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
        validateDeveloper(request.getDeveloperLevel(), request.getExperienceYears());

        developerRepository.findByMemberId(request.getMemberId())
                .ifPresent((developer) -> {throw new DMakerException(DMakerErrorCode.DUPLICATED_MEMBER_ID);});
        
    }

    private void validateDeveloper(DeveloperLevel developerLevel, Integer experienceYears) {
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

    public List<DeveloperDto> getAllDevelopers() {
        return developerRepository.findAll().stream().map(DeveloperDto::fromEntity)
                .collect(Collectors.toList());
    }

    public DeveloperDetailDto getDeveloperDetail(String memberId) {
        return developerRepository.findByMemberId(memberId)
                .map(DeveloperDetailDto::fromEntity).orElseThrow(() -> new DMakerException(DMakerErrorCode.NO_DEVELOPER));
    }

    public DeveloperDetailDto editDeveloper(String memberId, EditDeveloper.Request request) {
        validateDeveloper(request.getDeveloperLevel(), request.getExperienceYears());

        Developer developer = developerRepository.findByMemberId(memberId).orElseThrow(() -> new DMakerException(DMakerErrorCode.NO_DEVELOPER));

        developer.setDeveloperLevel(request.getDeveloperLevel());
        developer.setDeveloperSkillType(request.getDeveloperSkillType());
        developer.setExperienceYears(request.getExperienceYears());

        developerRepository.save(developer);

        return DeveloperDetailDto.fromEntity(developer);
    }

}
