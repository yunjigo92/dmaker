package com.yunji.dmaker.controller;

import com.yunji.dmaker.dto.DeveloperDto;
import com.yunji.dmaker.service.DMakerService;
import com.yunji.dmaker.type.DeveloperLevel;
import com.yunji.dmaker.type.DeveloperSkillType;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * description
 * <p>
 * author         : yunji
 * date           : 22. 7. 29.
 */
@WebMvcTest(DMakerController.class)
class DMakerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DMakerService dMakerService;

    protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType()
    , MediaType.APPLICATION_JSON.getSubtype()
    , StandardCharsets.UTF_8);

    @Test
    void getAllDeveloper() throws Exception{
        DeveloperDto newDeveloperDto = DeveloperDto.builder()
                .developerLevel(DeveloperLevel.NEW)
                .developerSkillType(DeveloperSkillType.BACK_END)
                .memberId("memberId").build();
        DeveloperDto juniorDeveloperDto = DeveloperDto.builder()
                .developerLevel(DeveloperLevel.JUNIOR)
                .developerSkillType(DeveloperSkillType.FRONT_END)
                .memberId("memberId").build();

        given(dMakerService.getAllEmployedDevelopers())
                .willReturn(Arrays.asList(juniorDeveloperDto, newDeveloperDto));

        mockMvc.perform(get("/developers").contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.[0].developerLevel", CoreMatchers.is(DeveloperLevel.JUNIOR.name()))
                ).andExpect(
                        jsonPath("$.[1].developerLevel", CoreMatchers.is(DeveloperLevel.NEW.name()))
                ).andExpect(
                        jsonPath("$.[0].developerSkillType", CoreMatchers.is(DeveloperSkillType.FRONT_END.name()))
                );

    }

}