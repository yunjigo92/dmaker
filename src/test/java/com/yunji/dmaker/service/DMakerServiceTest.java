package com.yunji.dmaker.service;

import com.yunji.dmaker.code.StatusCode;
import com.yunji.dmaker.dto.CreateDeveloper;
import com.yunji.dmaker.dto.DeveloperDetailDto;
import com.yunji.dmaker.dto.DeveloperDto;
import com.yunji.dmaker.entity.Developer;
import com.yunji.dmaker.exception.DMakerErrorCode;
import com.yunji.dmaker.exception.DMakerException;
import com.yunji.dmaker.repository.DeveloperRepository;
import com.yunji.dmaker.repository.RetiredDeveloperRepository;
import com.yunji.dmaker.type.DeveloperLevel;
import com.yunji.dmaker.type.DeveloperSkillType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

    private final CreateDeveloper.Request developerRequest = CreateDeveloper.Request.builder()
            .developerLevel(DeveloperLevel.SENIOR)
            .developerSkillType(DeveloperSkillType.FRONT_END)
            .experienceYears(10)
            .name("yunji")
            .memberId("memberID")
            .age(31)
            .build();

    private final Developer developer = Developer.builder()
            .developerLevel(DeveloperLevel.SENIOR)
            .developerSkillType(DeveloperSkillType.FRONT_END)
            .experienceYears(10)
            .name("yunji")
            .memberId("memberID")
            .age(31)
            .build();

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
    void createDeveloperTest_success(){


        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.empty());

        ArgumentCaptor<Developer> captor = ArgumentCaptor.forClass(Developer.class);


        //when
        CreateDeveloper.Response developer =  dMakerService.createDeveloper(developerRequest);

        //then
        verify(developerRepository,times(1 )).save(captor.capture());
        Developer savedDeveloper = captor.getValue();
        assertEquals(DeveloperLevel.SENIOR,savedDeveloper.getDeveloperLevel());
        assertEquals(DeveloperSkillType.FRONT_END,savedDeveloper.getDeveloperSkillType());

    };


    @Test
    void createDeveloperTest_failed_with_duplicated(){
        //given
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(developer));

        //when
        //then
        DMakerException dMakerException = assertThrows(DMakerException.class, () -> dMakerService.createDeveloper(developerRequest));

        assertEquals(DMakerErrorCode.DUPLICATED_MEMBER_ID, dMakerException.getDMakerErrorCode());
    };

}