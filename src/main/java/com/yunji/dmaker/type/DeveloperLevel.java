package com.yunji.dmaker.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * description
 * <p>
 * author         : yunji
 * date           : 22. 7. 18.
 */
@AllArgsConstructor
@Getter
public enum DeveloperLevel {
    NEW("신입 개발자",0,0),
    JUNIOR("주니어 개발자",1,10),
    JUNGNIOR("중니어 개발자",10,20),
    SENIOR("시니어 개발자",20,30);

    private final String description;
    private final Integer minExperienceYears;
    private final Integer maxExperienceYears;
}
