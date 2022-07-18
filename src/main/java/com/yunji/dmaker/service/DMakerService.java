package com.yunji.dmaker.service;

import com.yunji.dmaker.dto.CreateDeveloper;
import com.yunji.dmaker.entity.Developer;
import com.yunji.dmaker.repository.DeveloperRepository;
import com.yunji.dmaker.type.DeveloperLevel;
import com.yunji.dmaker.type.DeveloperSkillType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.validation.Valid;

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
    public void createDeveloper(CreateDeveloper.Request request) {
        Developer developer = Developer.builder()
                .developerLevel(DeveloperLevel.JUNIOR)
                .developerSkillType(DeveloperSkillType.FRONT_END)
                .experienceYears(2)
                .name("yunji")
                .age(10)
                .build();

        developerRepository.save(developer);
    }

    // transaction 관련 동작
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
