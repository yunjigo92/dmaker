package com.yunji.dmaker.dto;

import com.yunji.dmaker.code.StatusCode;
import com.yunji.dmaker.entity.Developer;
import com.yunji.dmaker.type.DeveloperLevel;
import com.yunji.dmaker.type.DeveloperSkillType;
import lombok.*;

/**
 * description
 * <p>
 * author         : yunji
 * date           : 22. 7. 21.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeveloperDetailDto {
    private DeveloperLevel developerLevel;
    private DeveloperSkillType developerSkillType;
    private Integer experienceYears;
    private String memberId;
    private String name;
    private Integer age;
    private StatusCode statusCode;

    public static DeveloperDetailDto fromEntity(Developer developer){
        return DeveloperDetailDto.builder()
                .developerLevel(developer.getDeveloperLevel())
                .developerSkillType(developer.getDeveloperSkillType())
                .experienceYears(developer.getExperienceYears())
                .memberId(developer.getMemberId())
                .name(developer.getName())
                .age(developer.getAge())
                .statusCode(developer.getStatusCode())
                .build();
    }
}
