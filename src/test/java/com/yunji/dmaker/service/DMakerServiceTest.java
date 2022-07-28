package com.yunji.dmaker.service;

import com.yunji.dmaker.code.StatusCode;
import com.yunji.dmaker.dto.CreateDeveloper;
import com.yunji.dmaker.dto.DeveloperDetailDto;
import com.yunji.dmaker.dto.DeveloperDto;
import com.yunji.dmaker.entity.Developer;
import com.yunji.dmaker.repository.DeveloperRepository;
import com.yunji.dmaker.repository.RetiredDeveloperRepository;
import com.yunji.dmaker.type.DeveloperLevel;
import com.yunji.dmaker.type.DeveloperSkillType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

/**
 * description
 * <p>
 * author         : yunji
 * date           : 22. 7. 28.
 */
@ExtendWith(MockitoExtension.class)
class DMakerServiceTest {

    @Mock
    private DeveloperRepository developerRepository;

    @Mock
    private RetiredDeveloperRepository retiredDeveloperRepository;

    @InjectMocks
    private DMakerService dMakerService;

    @Test
    void createDeveloper() {

    }

    @Test
    void transactionTest() {
    }

    @Test
    void getAllEmployedDevelopers() {
    }

    @Test
    void getDeveloperDetail() {

        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(
                        Developer.builder()
                                .developerLevel(DeveloperLevel.JUNGNIOR)
                                .developerSkillType(DeveloperSkillType.BACK_END)
                                .experienceYears(5)
                                .statusCode(StatusCode.EMPLOYED)
                                .name("yunji")
                                .age(30)
                                .build()
                ));

        DeveloperDetailDto developerDetailDto = dMakerService.getDeveloperDetail("memberId");

       // assertEquals(DeveloperLevel.JUNIOR, developerDetailDto.getDeveloperLevel());
        assertEquals(DeveloperLevel.JUNGNIOR,developerDetailDto.getDeveloperLevel());

    }

    @Test
    void editDeveloper() {
    }

    @Test
    void deleteDeveloper() {
    }
}