package com.yunji.dmaker.dto;

import com.yunji.dmaker.exception.DMakerErrorCode;
import lombok.*;

/**
 * description
 * <p>
 * author         : yunji
 * date           : 22. 7. 26.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DMakerErrorResponse {
    private DMakerErrorCode errorCode;
    private String errorMessage;
}
