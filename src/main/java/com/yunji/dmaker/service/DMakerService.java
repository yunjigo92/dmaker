package com.yunji.dmaker.service;

import com.yunji.dmaker.code.StatusCode;
import com.yunji.dmaker.dto.CreateDeveloper;
import com.yunji.dmaker.dto.DeveloperDetailDto;
import com.yunji.dmaker.dto.DeveloperDto;
import com.yunji.dmaker.dto.EditDeveloper;
import com.yunji.dmaker.entity.Developer;
import com.yunji.dmaker.entity.RetiredDeveloper;
import com.yunji.dmaker.exception.DMakerErrorCode;
import com.yunji.dmaker.exception.DMakerException;
import com.yunji.dmaker.repository.DeveloperRepository;
import com.yunji.dmaker.repository.RetiredDeveloperRepository;
import com.yunji.dmaker.type.DeveloperLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.stream.Collectors;

import static com.yunji.dmaker.constant.DMakerConstant.MAX_SENIOR_EXPERIENCE_YEARS;
import static com.yunji.dmaker.constant.DMakerConstant.MIN_SENIOR_EXPERIENCE_YEARS;

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
    private final RetiredDeveloperRepository retiredDeveloperRepository;
    private final EntityManager em;

    @Transactional
    public CreateDeveloper.Response createDeveloper(@NonNull CreateDeveloper.Request request) {
        validateCreateDeveloperRequest(request);
        return CreateDeveloper.Response.fromEntity(developerRepository.save(createDeveloperFromRequest(request)));
    }


    private Developer createDeveloperFromRequest(CreateDeveloper.Request request){
        return Developer.builder()
                .developerLevel(request.getDeveloperLevel())
                .developerSkillType(request.getDeveloperSkillType())
                .experienceYears(request.getExperienceYears())
                .name(request.getName())
                .age(request.getAge())
                .statusCode(StatusCode.EMPLOYED)
                .memberId(request.getMemberId())
                .build();
    }


    private void validateCreateDeveloperRequest(@NonNull CreateDeveloper.Request request) {
        //business validation
        validateDeveloper(request.getDeveloperLevel(), request.getExperienceYears());

        developerRepository.findByMemberId(request.getMemberId())
                .ifPresent((developer) -> {throw new DMakerException(DMakerErrorCode.DUPLICATED_MEMBER_ID);});
        
    }

    private void validateDeveloper(DeveloperLevel developerLevel, Integer experienceYears) {
        if(developerLevel == DeveloperLevel.SENIOR &&
                experienceYears < MIN_SENIOR_EXPERIENCE_YEARS){
            throw new DMakerException(DMakerErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

        if(developerLevel == DeveloperLevel.JUNGNIOR && (experienceYears < MIN_SENIOR_EXPERIENCE_YEARS || experienceYears > MAX_SENIOR_EXPERIENCE_YEARS) ){
            throw new DMakerException(DMakerErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

        if(developerLevel == DeveloperLevel.JUNIOR && experienceYears > MIN_SENIOR_EXPERIENCE_YEARS){
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

    @Transactional(readOnly = true)
    public List<DeveloperDto> getAllEmployedDevelopers() {
        return developerRepository.findDevelopersByStatusCodeEquals(StatusCode.EMPLOYED)
                .stream().map(DeveloperDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DeveloperDetailDto getDeveloperDetail(String memberId) {
        return developerRepository.findByMemberId(memberId)
                .map(DeveloperDetailDto::fromEntity).orElseThrow(() -> new DMakerException(DMakerErrorCode.NO_DEVELOPER));
    }

    public DeveloperDetailDto editDeveloper(String memberId, EditDeveloper.Request request) {
        validateDeveloper(request.getDeveloperLevel(), request.getExperienceYears());

        Developer developer = getUpdatedDeveloperFromRequest(request, getDeveloperByMemberId(memberId));

        developerRepository.save(developer);
        return DeveloperDetailDto.fromEntity(developer);
    }

    private static Developer getUpdatedDeveloperFromRequest(EditDeveloper.Request request, Developer developer) {
        developer.setDeveloperLevel(request.getDeveloperLevel());
        developer.setDeveloperSkillType(request.getDeveloperSkillType());
        developer.setExperienceYears(request.getExperienceYears());

        return developer;
    }

    @Transactional
    public DeveloperDetailDto deleteDeveloper(String memberId) {
        //1. Employed -> Retired
        Developer developer = getDeveloperByMemberId(memberId);
        developer.setStatusCode(StatusCode.RETIRED);

        //2. save into RetiredDeveloper
        RetiredDeveloper retiredDeveloper = RetiredDeveloper.builder()
                .memberId(memberId)
                .name(developer.getName())
                .build();

        retiredDeveloperRepository.save(retiredDeveloper);
        return DeveloperDetailDto.fromEntity(developer);
    }

    private Developer getDeveloperByMemberId(String memberId){
        return developerRepository.findByMemberId(memberId)
                .orElseThrow(() -> new DMakerException(DMakerErrorCode.NO_DEVELOPER));
    }

}
