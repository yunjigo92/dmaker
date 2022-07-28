package com.yunji.dmaker.dto;

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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DeveloperDto {
    private DeveloperLevel developerLevel;
    private DeveloperSkillType developerSkillType;
    private String memberId;

    public static DeveloperDto fromEntity(Developer developer){
        return DeveloperDto.builder()
                .developerLevel(developer.getDeveloperLevel())
                .developerSkillType(developer.getDeveloperSkillType())
                .memberId(developer.getMemberId())
                .build();
    }


}
