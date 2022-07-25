package com.yunji.dmaker.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * description
 * <p>
 * author         : yunji
 * date           : 22. 7. 25.
 */
@Getter
@AllArgsConstructor
public enum StatusCode {
    EMPLOYED("고용"),
    RETIRED("퇴직");

    private final String description;
}
